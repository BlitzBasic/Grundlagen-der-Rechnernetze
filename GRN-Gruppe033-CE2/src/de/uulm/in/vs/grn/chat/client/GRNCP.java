package de.uulm.in.vs.grn.chat.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class GRNCP {

	public static final String PROTOCOL_VERSION = "GRNCP/0.1";
	public static final long LEASE_TIME = 570000;
	private static PubListener pubListener;
	private static DisplayWorker displayWorker;
	private static CommandController controller;
	private static BufferedReader inputReader;

	public static void main(String[] args) {

		// This client is command line based

		System.out.println("Welcome to GRNCP!");

		// allow to start with given parameters
		boolean alreadyStarted = false;
		if (args.length == 3) {
			try {
				inputReader = new BufferedReader(new InputStreamReader(System.in));
				InetAddress ipAddress = InetAddress.getByName(args[0]);
				int commandPort = Integer.parseInt(args[1]);
				int pubSubPort = Integer.parseInt(args[2]);

				alreadyStarted = initiateConnection(ipAddress, commandPort, pubSubPort);

			} catch (Exception e) {
				// nothing
			}
		} else if (!alreadyStarted) {

			// ask user to insert connection data if not already connected
			while (!initiateConnection())
				;
		}

	}

	/**
	 * asks for parameters and initiates connection
	 * 
	 * @return true if successful, false if not
	 */
	public static boolean initiateConnection() {
		try {

			disableAllActive();

			// used to connect to a server
			System.out.println("Please enter the IP-Address of the Chat-Server");
			InetAddress ipAddress = InetAddress.getByName(inputReader.readLine());

			// used to implement the command connection
			System.out.println("Please enter the Command-Port of the Chat-Server");
			int commandPort = Integer.parseInt(inputReader.readLine());

			// used to connect to the message distributor
			System.out.println("Please enter the Pub/Sub-Port of the Chat-Server");
			int pubSubPort = Integer.parseInt(inputReader.readLine());

			return initiateConnection(ipAddress, commandPort, pubSubPort);
		} catch (Exception e) {
			// nothing
			e.printStackTrace();
		}

		System.out.println("Connection couldn't be established, please retry!");
		return false;
	}

	/**
	 * initiates connection with given parameters
	 * 
	 * @return true if successful, false if not
	 */
	public static boolean initiateConnection(InetAddress ipAddress, int commandPort, int pubSubPort) {
		try {
			// starts a thread that connects to the server and displays all
			// messages
			displayWorker = new DisplayWorker();
			displayWorker.start();
			pubListener = new PubListener(ipAddress, pubSubPort, displayWorker);
					pubListener.start();
			// starts a thread that initiates the command connection
			controller = new CommandController(ipAddress, commandPort, displayWorker, inputReader, LEASE_TIME);
					controller.start();
			return true;

		} catch (Exception e) {
			// nothing
		}

		// if something went wrong
		System.out.println("Connection couldn't be established");
		return false;
	}
	
	public static void disableAllActive(){
		if (displayWorker!=null){
			displayWorker.disable();
		}
		if (pubListener!=null){
			pubListener.disable();
		}
		if (controller!=null){
			controller.disable();
		}
	}

}
