package de.uulm.in.vs.grn.chat.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class GRNCP {

	public static void main(String[] args) {
		
		//This client is command line based
		
		System.out.println("Welcome to GRNCP!");
		
		while(!initiateConnection());

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
			
			//TODO: create a new class for command connection, initialize it
			(new PubListener(ipAddress, pubSubPort)).run();
			return true;
		}catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("Connection couldn't be established");
		return false;
	}

}
