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
import de.uulm.in.vs.grn.chat.client.messages.responses.GRNCPPong;
import de.uulm.in.vs.grn.chat.client.messages.responses.GRNCPSent;

/**
 * this class initiates a command connection and is capable of sending and
 * receiving messages in their original order
 * 
 * @author Marius
 *
 */
public class CommandCommunicator extends Thread {

	private final LinkedBlockingQueue<Request> requests;
	private final InetAddress address;
	private final int port;
	private boolean active = false;
	private boolean loggedIn = false;
	private CountDownLatch loginSignal;
	private final DisplayWorker displayWorker;
	private ConnectionKeeper connectionKeeper;
	private final long leaseTime;

	/**
	 * Constructor to set the address and port at which to send commands, while at the same time initializing a 
	 * request queue to queue all the commands, a active boolean which indicates if it can currently be used, a 
	 * displayWorker that display everything it's told to display in the command line and a leaseTime which 
	 * indicates how long the Server keeps the connection alive.
	 * 
	 */
	public CommandCommunicator(InetAddress address, int port, DisplayWorker displayWorker, long leaseTime) {
		super();
		this.address = address;
		this.port = port;
		requests = new LinkedBlockingQueue<>();
		active = true;
		this.displayWorker = displayWorker;
		this.leaseTime = leaseTime;
	}

	/**
	 * method to deal with all useable commands if a wrong command is used it just ignores that request to send a
	 * command to the Server.
	 */
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

				try {
					String responseString = "";

					String command = "";
					String date = "";
					String reason = "";
					String[] field;
					ArrayList<String> usernames = new ArrayList<String>();

					do {
						responseString = commandSocketReader.readLine();
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
								displayWorker.addDisplayable(loggedinResponse);
								loggedIn = true;
								connectionKeeper = new ConnectionKeeper(leaseTime, this);
								connectionKeeper.start();
								command = "";
								loginSignal.countDown();
								break;
							case "ERROR":
								GRNCPError errorResponse = new GRNCPError(date, reason);
								displayWorker.addDisplayable(errorResponse);
								command = "";
								loginSignal.countDown();
								break;
							case "EXPIRED":
								GRNCPExpired expiredResponse = new GRNCPExpired(date);
								displayWorker.addDisplayable(expiredResponse);
								command = "";
								loginSignal.countDown();
								loggedIn = false;
								break;
							case "SENT":
								if (!loggedIn)
									break;
								// not used yet, may be used to inform user
								@SuppressWarnings("unused")
								GRNCPSent sentResponse = new GRNCPSent(date);
								command = "";
								break;
							case "BYEBYE":
								GRNCPByebye byebyeResponse = new GRNCPByebye(date);
								displayWorker.addDisplayable(byebyeResponse);
								loggedIn = false;
								active = false;
								GRNCP.initiateConnection();
								break;
							case "PONG":
								if (!loggedIn)
									break;
								GRNCPPong pongResponse = new GRNCPPong(date, usernames);
								displayWorker.addDisplayable(pongResponse);
								command = "";
								break;
							default:
							}
							responseString = null;
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

		} catch (Exception e) {
			System.out.println("An exception occured, please reconnect.");
			GRNCP.initiateConnection();
		}

	}

	/**
	 * add any kind of command request to the request queue or a login request if the client is not yet logged in
	 * @param request
	 */
	public void addRequest(Request request) {
		if (loggedIn || (request instanceof GRNCPLogin))
			requests.add(request);
	}

	/**
	 * disables the connectionKeeper which will result in a disconnect after the leaseTime is over
	 */
	public void disable() {
		active = false;
		if (connectionKeeper != null)
			connectionKeeper.disable();
	}

	/**
	 * returns a boolean which indicates wheter the Client is logged in or not
	 * @return
	 */
	public boolean isLoggedIn() {
		return loggedIn;
	}

	/**
	 * creates a login request to login the user and wait til he is logged in
	 * @param username
	 */
	public void login(String username) {
		if (!loggedIn) {

			loginSignal = new CountDownLatch(1);
			addRequest(new GRNCPLogin(username));
			try {
				//wait for loggedin response
				loginSignal.await();

			} catch (InterruptedException e) {

			}
		}

	}
}
