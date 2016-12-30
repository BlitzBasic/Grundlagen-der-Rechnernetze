package de.uulm.in.vs.grn.chat.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class GRNCP {

	public static void main(String[] args) {
		
		//This client is command line based
		
		System.out.println("Welcome to GRNCP!");
		
		try{
			
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.println("Please enter the IP-Address of the Chat-Server");
			InetAddress ipAddress = InetAddress.getByName(inputReader.readLine());
			
			System.out.println("Please enter the Command-Port of the Chat-Server");
			int commandPort = Integer.parseInt(inputReader.readLine());
			
			System.out.println("Please enter the Pub/Sub-Port of the Chat-Server");
			int pubSubPort = Integer.parseInt(inputReader.readLine());
			
			(new PubListener(ipAddress, pubSubPort)).run();
			
		}catch (Exception e) {
			// TODO: handle exception
		}

	}

}
