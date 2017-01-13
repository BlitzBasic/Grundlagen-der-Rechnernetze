package de.uulm.in.vs.grn.chat.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class CommandCommunicator extends Thread {

	private InetAddress address;
	private int port;
	private boolean active = false;

	public CommandCommunicator(InetAddress address, int port) {
		super();
		this.address = address;
		this.port = port;
		active = true;
	}

	public void run() {
		try (Socket commandSocket = new Socket(address, port);
				BufferedReader commandSocketReader = new BufferedReader(
						new InputStreamReader(commandSocket.getInputStream()));
				BufferedWriter commandSocketWriter = new BufferedWriter(
						new OutputStreamWriter(commandSocket.getOutputStream()));
				BufferedReader inputReader = new BufferedReader(
						new InputStreamReader(commandSocket.getInputStream()))) {

			while (active) {
				// try {
				//
				//
				//
				// } catch (IOException e) {
				// // do nothing
				// }

			}

		} catch (Exception e) {
			System.out.println("An exception occured, please reconnect.");
			GRNCP.initiateConnection();
		}

	}
}
