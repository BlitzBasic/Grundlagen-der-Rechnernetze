package de.uulm.in.vs.grn.chat.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

public class PubListener extends Thread {

	private static final String PROTOCOL_VERSION = "GRNCP/0.1";
	private InetAddress address;
	private int port;

	public PubListener(InetAddress address, int port) {
		super();
		this.address = address;
		this.port = port;
	}

	@Override
	public void run() {

		System.out.println("Trying to connect to " + address.getHostAddress() + ":" + port);

		try (Socket pubSocket = new Socket(address, port);
				BufferedReader inReader = new BufferedReader(new InputStreamReader(pubSocket.getInputStream()))) {
			System.out.println("You are connected!");

			while (true) {
				try {
					String line = inReader.readLine();
					String[] parameters = line.split(" ", 2);

					// TODO: Catch Exceptions
					String protocol = parameters[0];
					CommandExpression commandExp = CommandExpression.valueOf(parameters[1]);

					if (protocol.equals(PROTOCOL_VERSION)) {

						switch (commandExp) {

						case MESSAGE:
						// read date
						{
							line = inReader.readLine();
							String date = line.substring(line.indexOf(' ') + 1);

							// read name
							line = inReader.readLine();
							String username = line.substring(line.indexOf(' ') + 1);

							// read text
							line = inReader.readLine();
							String text = line.substring(line.indexOf(' ') + 1);

							//read empty line
							inReader.readLine();
							
							System.out.println(date + " | " + username + ": " + text);
							break;
						}

						case EVENT: {
							// read date
							line = inReader.readLine();
							String date = line.substring(line.indexOf(' ') + 1);

							// read description
							line = inReader.readLine();
							String description = line.substring(line.indexOf(' ') + 1);

							//read empty line
							inReader.readLine();
							
							System.out.println(date + " | " + description);
							break;
						}

						default:
							// Ignore
							break;
						}

					}

				} catch (IllegalArgumentException e) {
					// Do Nothing
				} catch (ArrayIndexOutOfBoundsException e) {
					// Do Nothing
				} catch (NullPointerException e) {
					// Do Nothing
				}

			}

		} catch (Exception e) {
			System.out.println("An exception occured, please reconnect.");
			GRNCP.initiateConnection();
		}

	}

}
