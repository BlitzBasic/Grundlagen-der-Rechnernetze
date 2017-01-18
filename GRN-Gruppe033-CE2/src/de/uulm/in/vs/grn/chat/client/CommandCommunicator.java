package de.uulm.in.vs.grn.chat.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

import de.uulm.in.vs.grn.chat.client.messages.requests.GRNCPLogin;
import de.uulm.in.vs.grn.chat.client.messages.requests.Request;
import de.uulm.in.vs.grn.chat.client.messages.responses.GRNCPByebye;
import de.uulm.in.vs.grn.chat.client.messages.responses.GRNCPError;
import de.uulm.in.vs.grn.chat.client.messages.responses.GRNCPExpired;
import de.uulm.in.vs.grn.chat.client.messages.responses.GRNCPLoggedin;
import de.uulm.in.vs.grn.chat.client.messages.responses.GRNCPSent;
import de.uulm.in.vs.grn.chat.client.messages.responses.Response;

public class CommandCommunicator extends Thread {

	private final LinkedBlockingQueue<Request> requests;
	private final InetAddress address;
	private final int port;
	private boolean active = false;
	private boolean loggedIn = false;
	private CountDownLatch loginSignal;
	private boolean waitingForLoggedIn = false;
	private final DisplayWorker displayWorker;

	public CommandCommunicator(InetAddress address, int port, DisplayWorker displayWorker) {
		super();
		this.address = address;
		this.port = port;
		requests = new LinkedBlockingQueue<>();
		active = true;
		this.displayWorker = displayWorker;
	}

	public void run() {
		try (Socket commandSocket = new Socket(address, port);
				BufferedReader commandSocketReader = new BufferedReader(
						new InputStreamReader(commandSocket.getInputStream()));
				BufferedWriter commandSocketWriter = new BufferedWriter(
						new OutputStreamWriter(commandSocket.getOutputStream()))) {

			while (active) {
				try {
					Request request = requests.take();
					request.send(commandSocketWriter);

				} catch (InterruptedException e) {
					// nothing
				} catch (Exception e) {
					System.err.println("Request could not be sent");
					e.printStackTrace(System.err);
				}

				// TODO: rethink
				while (waitingForLoggedIn) {
					try {

						String responseString = "";

						String command = "";
						String date = "";
						String reason = "";
						String[] field;
						ArrayList<String> usernames = new ArrayList<String>();
						do {
							responseString = commandSocketReader.readLine();
							// System.out.println(response);
							if (responseString.split(" ")[0].equals("GRNCP/0.1")) {
								command = responseString.split(" ")[1];
							} else if ((field = responseString.split(": ")).length > 1) {
								if (field[0].equals("Date"))
									date = field[1];
								else if (field[0].equals("Reason"))
									reason = field[1].trim();
								else if (field[0].equals("Usernames")) {
									String[] usernameArray = field[1].split(",");
									for (String s : usernameArray) {
										usernames.add(s);
									}
								}

							} else if (responseString.equals("")) {
								switch (command) {
								case "LOGGEDIN":
									GRNCPLoggedin loggedinResponse = new GRNCPLoggedin(date);
									loggedinResponse.handle();
									displayWorker.addDisplayable(loggedinResponse);
									loggedIn = true;
									waitingForLoggedIn = false;
									command = "";
									loginSignal.countDown();
									break;
								case "ERROR":
									GRNCPError errorResponse = new GRNCPError(date, reason);
									errorResponse.handle();
									displayWorker.addDisplayable(errorResponse);
									waitingForLoggedIn = false;
									command = "";
									loginSignal.countDown();
									break;
								case "EXPIRED":
									GRNCPExpired expiredResponse = new GRNCPExpired(date);
									displayWorker.addDisplayable(expiredResponse);
									// TODO
									command = "";
									loginSignal.countDown();
									break;
								case "SENT":
									GRNCPSent sentResponse = new GRNCPSent(date);
									// TODO
									command = "";
									break;
								case "BYEBYE":
									GRNCPByebye byebyeResponse = new GRNCPByebye(date);
									displayWorker.addDisplayable(byebyeResponse);
									// TODO
									command = "";
									break;
								case "PONG":
									// TODO
									command = "";
									break;

								}
							}
						} while (responseString != null);

					} catch (ArrayIndexOutOfBoundsException e) {
						// do nothing
					} catch (IndexOutOfBoundsException e) {
						// do nothing
					} catch (NullPointerException e) {
						// do nothing
					} catch (SocketException e) {
						active = false;
						System.out.println("Connection lost, please reconnect");
						while (GRNCP.initiateConnection())
							;
					} catch (IOException e) {
						active = false;
						System.out.println("Connection lost, please reconnect");
						while (GRNCP.initiateConnection())
							;
					}
				}
			}

		} catch (Exception e) {
			System.out.println("An exception occured, please reconnect.");
			GRNCP.initiateConnection();
		}

	}

	public void addRequest(Request request) {
		// if (loggedIn || (request instanceof GRNCPLogin))
		requests.add(request);
	}

	public void disable() {
		active = false;

	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void login(String username) {
		if (!loggedIn) {

			loginSignal = new CountDownLatch(1);
			addRequest(new GRNCPLogin(username));
			waitingForLoggedIn = true;
			try {
				loginSignal.await();

			} catch (InterruptedException e) {

			}
		}

	}
}
