package de.uulm.in.vs.grn.chat.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

import de.uulm.in.vs.grn.chat.client.messages.events.GRNCPMessageEvent;
import de.uulm.in.vs.grn.chat.client.messages.events.GRNCPEvent;

public class PubListener extends Thread {

	private InetAddress address;
	private int port;
	private boolean active = false;
	private DisplayWorker worker;

	public PubListener(InetAddress address, int port) {
		super();
		this.address = address;
		this.port = port;
		worker = new DisplayWorker();
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
								worker.addEvent(new GRNCPMessageEvent(date, username, text));
								command = "";
								break;
							case "EVENT":
								worker.addEvent(new GRNCPEvent(date, description));
								command = "";
								break;
							}
							
						}
					} while (response != null);

				
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
