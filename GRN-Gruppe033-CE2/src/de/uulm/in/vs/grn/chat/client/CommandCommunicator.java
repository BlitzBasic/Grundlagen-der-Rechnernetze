package de.uulm.in.vs.grn.chat.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FilterInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class CommandCommunicator extends Thread {

	
	private InetAddress address;
	private int port;

	public CommandCommunicator(InetAddress address, int port) {
		super();
		this.address = address;
		this.port = port;
	}
	
	public void run(){
		try (Socket commandSocket = new Socket(address, port);
				BufferedReader commandSocketReader = new BufferedReader(new InputStreamReader(commandSocket.getInputStream()));
				BufferedWriter commandSocketWriter = new BufferedWriter(new OutputStreamWriter(commandSocket.getOutputStream()));
				BufferedReader inputReader = new BufferedReader(new InputStreamReader(commandSocket.getInputStream()))) {

			while (true) {
				try {
					
					while(true){
						String inputLine = inputReader.readLine();
					}
					
					
					
					
					//first write, than receive
					
//					// read first line of message
//					String line = comReader.readLine();
//					String[] parameters = line.split(" ", 2);
//
//					String protocol = parameters[0];
//					CommandExpression commandExp = CommandExpression.valueOf(parameters[1]);
//
//					// Check for the implemented protocol version
//					if (protocol.equals(GRNCP.PROTOCOL_VERSION)) {
//
//						// choose the correct message type
//						switch (commandExp) {
//
//						case MESSAGE: {
//							// read date
//							line = comReader.readLine();
//							String date = line.substring(line.indexOf(' ') + 1);
//
//							// read name
//							line = comReader.readLine();
//							String username = line.substring(line.indexOf(' ') + 1);
//
//							// read text
//							line = comReader.readLine();
//							String text = line.substring(line.indexOf(' ') + 1);
//
//							// read empty line
//							comReader.readLine();
//
//							System.out.println(date + " | " + username + ": " + text);
//							break;
//						}
//
//						case EVENT: {
//							// read date
//							line = comReader.readLine();
//							String date = line.substring(line.indexOf(' ') + 1);
//
//							// read description
//							line = comReader.readLine();
//							String description = line.substring(line.indexOf(' ') + 1);
//
//							// read empty line
//							comReader.readLine();
//
//							System.out.println(date + " | " + description);
//							break;
//						}
//
//						default:
//							// ignore
//							break;
//						}
//
//					}

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
