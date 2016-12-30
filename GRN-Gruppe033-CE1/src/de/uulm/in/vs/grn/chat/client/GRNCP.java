package de.uulm.in.vs.grn.chat.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class GRNCP {

	public static void main(String[] args) {
		
		//This client is command line based
		
		
		System.out.println("Welcome to GRNCP!");
		
		//allow to start with given parameters
		boolean alreadyStarted=false;
		if(args.length == 3){
			try {
				InetAddress ipAddress = InetAddress.getByName(args[0]);
				int commandPort = Integer.parseInt(args[1]);
				int pubSubPort = Integer.parseInt(args[2]);
				
				alreadyStarted = initiateConnection(ipAddress, commandPort, pubSubPort);
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}else if(!alreadyStarted) {
			//try to connect
			while(!initiateConnection());
		}
		
		
		
		
		
		

	}

	/**
	 * initiates connection
	 * @return true if successful
	 */
	public static boolean initiateConnection() {
		try{
			
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.println("Please enter the IP-Address of the Chat-Server");
			InetAddress ipAddress = InetAddress.getByName(inputReader.readLine());
			
			System.out.println("Please enter the Command-Port of the Chat-Server");
			int commandPort = Integer.parseInt(inputReader.readLine());
			
			System.out.println("Please enter the Pub/Sub-Port of the Chat-Server");
			int pubSubPort = Integer.parseInt(inputReader.readLine());
			
			(new PubListener(ipAddress, pubSubPort)).run();
			return true;
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		System.out.println("Connection couldn't be established");
		return false;
	}
	
	public static boolean initiateConnection(InetAddress ipAddress, int commandPort, int pubSubPort) {
		try{
			
			(new PubListener(ipAddress, pubSubPort)).run();
			return true;
			
		}catch (Exception e) {
			// nothing
		}
		
		System.out.println("Connection couldn't be established");
		return false;
	}

}
