package de.uulm.in.vs.grn.chat.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class CommandController extends Thread {

	private boolean active = false;
	private CommandCommunicator communicator;
	private DisplayWorker displayWorker;
	private BufferedReader inputReader;
	
	public CommandController(InetAddress address, int port, DisplayWorker displayWorker, BufferedReader inputReader) {
		super();
		communicator = new CommandCommunicator(address, port);
		this.displayWorker = displayWorker;
		this.inputReader = inputReader;
		active = true;
	}

	public void run() {
		try {

			
			
			while (active) {

				// implement logic

			}

		} catch (Exception e) {
			System.out.println("An exception occured, please reconnect.");
			GRNCP.initiateConnection();
		}
	}
	
	public void disable(){
		communicator.disable();
		active = false;
	}

}
