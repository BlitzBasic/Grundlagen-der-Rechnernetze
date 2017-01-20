package de.uulm.in.vs.grn.chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.concurrent.CountDownLatch;

import de.uulm.in.vs.grn.chat.client.messages.SystemMessage;
import de.uulm.in.vs.grn.chat.client.messages.requests.GRNCPBye;
import de.uulm.in.vs.grn.chat.client.messages.requests.GRNCPLogin;
import de.uulm.in.vs.grn.chat.client.messages.requests.GRNCPPing;
import de.uulm.in.vs.grn.chat.client.messages.requests.GRNCPSend;

public class CommandController extends Thread {

	private boolean active = false;
	private CommandCommunicator communicator;
	private DisplayWorker displayWorker;
	private BufferedReader inputReader;

	public CommandController(InetAddress address, int port, DisplayWorker displayWorker, BufferedReader inputReader,
			long leaseTime) {
		super();
		communicator = new CommandCommunicator(address, port, displayWorker, leaseTime);
		communicator.start();
		this.displayWorker = displayWorker;
		this.inputReader = inputReader;

		active = true;
	}

	public void run() {
		try {

			while (active) {
				while (!communicator.isLoggedIn()) {
					displayWorker.addDisplayable(new SystemMessage("Please enter a username and press enter!"));
					try {
						String username = inputReader.readLine();
						communicator.login(username);

					} catch (IOException e) {
						System.out.println("Your username couldn't be read");
					}

				}

				String input = inputReader.readLine();

				if (input.startsWith("~")) {
					switch (input) {
					case "~logout":
						communicator.addRequest(new GRNCPBye());
						active = false;
						break;
					case "~help":
						displayWorker.addDisplayable(new SystemMessage("~help      | Displays this help text."));
						displayWorker.addDisplayable(new SystemMessage("~logout    | Logs you out."));
						displayWorker.addDisplayable(new SystemMessage(
								"~whoishere | Displays a full list of the users currently connected to the server."));
						break;
					case "~whoishere":
						communicator.addRequest(new GRNCPPing());
						break;
					default:
						displayWorker.addDisplayable(
								new SystemMessage("Enter \"~help\" to display the full list of commands."));
					}
				} else {
					if (input != "") {
						while (input.length() > 512) {
							communicator.addRequest(new GRNCPSend(input.substring(0, 512)));
							input = input.substring(512, input.length());
						}

						communicator.addRequest(new GRNCPSend(input));
					}
				}
			}

		} catch (Exception e) {
			System.out.println("An exception occured, please reconnect.");
			GRNCP.initiateConnection();
		}
	}

	public void disable() {
		if(communicator!=null)
		communicator.disable();

		active = false;
	}

}
