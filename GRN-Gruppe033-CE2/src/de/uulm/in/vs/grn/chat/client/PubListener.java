package de.uulm.in.vs.grn.chat.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

import de.uulm.in.vs.grn.chat.client.events.MessageEvent;
import de.uulm.in.vs.grn.chat.client.events.PubEvent;

public class PubListener extends Thread {

	private InetAddress address;
	private int port;
	private boolean active = false;
	private EventWorker worker;

	public PubListener(InetAddress address, int port) {
		super();
		this.address = address;
		this.port = port;
		worker = new EventWorker();
		worker.start();
		active = true;
		
	}

	@Override
	public void run() {

		System.out.println("Trying to connect to " + address.getHostAddress() + ":" + port);

		// tries to establish a connection
		try (Socket pubSocket = new Socket(address, port);
				BufferedReader pubReader = new BufferedReader(new InputStreamReader(pubSocket.getInputStream()))) {
			System.out.println("You are connected!");

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
						// System.out.println(response);
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
								worker.addEvent(new MessageEvent(date, username, text));
								command = "";
								break;
							case "EVENT":
								worker.addEvent(new PubEvent(date, description));
								command = "";
								break;
							}
							
						}
					} while (response != null);

					// //old
					// // read first line of message
					// String line = pubReader.readLine();
					// String[] parameters = line.split(" ", 2);
					//
					// String protocol = parameters[0];
					// CommandExpression commandExp =
					// CommandExpression.valueOf(parameters[1]);
					//
					// // Check for the implemented protocol version
					// if (protocol.equals(GRNCP.PROTOCOL_VERSION)) {
					//
					// // choose the correct message type
					// switch (commandExp) {
					//
					// case MESSAGE: {
					// // read date
					// line = pubReader.readLine();
					// String date = line.substring(line.indexOf(' ') + 1);
					//
					// // read name
					// line = pubReader.readLine();
					// String username = line.substring(line.indexOf(' ') + 1);
					//
					// // read text
					// line = pubReader.readLine();
					// String text = line.substring(line.indexOf(' ') + 1);
					//
					// // read empty line
					// pubReader.readLine();
					//
					// System.out.println(date + " | " + username + ": " +
					// text);
					// break;
					// }
					//
					// case EVENT: {
					// // read date
					// line = pubReader.readLine();
					// String date = line.substring(line.indexOf(' ') + 1);
					//
					// // read description
					// line = pubReader.readLine();
					// String description = line.substring(line.indexOf(' ') +
					// 1);
					//
					// // read empty line
					// pubReader.readLine();
					//
					// System.out.println(date + " | " + description);
					// break;
					// }
					//
					// default:
					// // ignore
					// break;
					// }
					//
					// }

				} catch (IllegalArgumentException e) {
					// do nothing
				} catch (ArrayIndexOutOfBoundsException e) {
					// do nothing
				} catch (IndexOutOfBoundsException e) {
					// do nothing
				} catch (NullPointerException e) {
					// do nothing
				}

			}

		} catch (Exception e) {
			System.out.println("An exception occured, please reconnect.");
			GRNCP.initiateConnection();
		}

	}

}
