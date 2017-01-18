package de.uulm.in.vs.grn.chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.concurrent.CountDownLatch;

import de.uulm.in.vs.grn.chat.client.messages.SystemMessage;
import de.uulm.in.vs.grn.chat.client.messages.requests.GRNCPLogin;

public class CommandController extends Thread {

	private boolean active = false;
	private CommandCommunicator communicator;
	private DisplayWorker displayWorker;
	private BufferedReader inputReader;
	private ConnectionKeeper connectionKeeper;

	
	public CommandController(InetAddress address, int port, DisplayWorker displayWorker, BufferedReader inputReader, long leaseTime) {
		super();
		communicator = new CommandCommunicator(address, port, displayWorker);
		communicator.start();
		this.displayWorker = displayWorker;
		this.inputReader = inputReader;
		this.connectionKeeper = new ConnectionKeeper(leaseTime, communicator);
		active = true;
	}

	public void run() {
		try {

			
			
			while (active) {
				while(!communicator.isLoggedIn()){
					displayWorker.addDisplayable(new SystemMessage("Please enter a username and press enter!"));
					try {
						String username = inputReader.readLine();
						communicator.login(username);
						
					} catch (IOException e) {
						System.out.println("Your username couldn't be read");
					}
					
				}
				
				
				//Implement user actions

			}

		} catch (Exception e) {
			System.out.println("An exception occured, please reconnect.");
			GRNCP.initiateConnection();
		}
	}
	
	public void disable(){
		communicator.disable();
		connectionKeeper.disable();
		active = false;
	}

	
	
}
