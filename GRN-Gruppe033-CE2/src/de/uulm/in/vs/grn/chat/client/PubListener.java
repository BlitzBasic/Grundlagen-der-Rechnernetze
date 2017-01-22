package de.uulm.in.vs.grn.chat.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import de.uulm.in.vs.grn.chat.client.messages.events.GRNCPMessageEvent;
import de.uulm.in.vs.grn.chat.client.messages.SystemMessage;
import de.uulm.in.vs.grn.chat.client.messages.events.GRNCPEvent;

/**
 * This class initiates a pub/sub connection and receives all incoming messages
 * 
 * @author Marius
 *
 */
public class PubListener extends Thread {

	private InetAddress address;
	private int port;
	private boolean active = false;
	private DisplayWorker displayWorker;

	public PubListener(InetAddress address, int port, DisplayWorker displayWorker) {
		super();
		this.address = address;
		this.port = port;
		this.displayWorker = displayWorker;
		active = true;

	}

	/**
	 * receives all the Server messages and handles every kind differently
	 */
	@Override
	public void run() {

		System.out.println("Trying to connect to pub/sub at" + address.getHostAddress() + ":" + port);

		// tries to establish a connection
		try (Socket pubSocket = new Socket(address, port);
				BufferedReader pubReader = new BufferedReader(new InputStreamReader(pubSocket.getInputStream()))) {
			displayWorker.addDisplayable(new SystemMessage("You are connected pub/sub!"));

			while (active) {
				try {

					String response = "";

					String command = "";
					String date = "";
					String username = "";
					String text = "";
					String description = "";
					String[] field;
					do {
						response = pubReader.readLine();
						if (response.split(" ")[0].equals("GRNCP/0.1")) {
							command = response.split(" ")[1];
						} else if ((field = response.split(": ")).length > 1) {
							if (field[0].equals("Date"))
								date = field[1];
							else if (field[0].equals("Username"))
								username = field[1].trim();
							else if (field[0].equals("Text"))
								text = field[1].trim();
							else if (field[0].equals("Description"))
								description = field[1].trim();
						} else if (response.equals("")) {
							switch (command) {
							case "MESSAGE":
								displayWorker.addDisplayable(new GRNCPMessageEvent(date, username, text));
								command = "";
								break;
							case "EVENT":
								displayWorker.addDisplayable(new GRNCPEvent(date, description));
								command = "";
								break;
							}

						}
					} while (response != null);

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
				}

			}

		} catch (Exception e) {
			System.out.println("An exception occured, please reconnect.");
			while (GRNCP.initiateConnection())
				;
		}

	}

	/**
	 * Disables the PubListener
	 */
	public void disable() {
		active = false;
		this.interrupt();
	}

}
