package de.uulm.in.vs.grn.chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

public class PubListener extends Thread {

	private static final String PROTOCOL_VERSION = "GRNCP/0.1";
	private InetAddress address;
	private int port;
	
	public PubListener(InetAddress adress, int port) {
		super();
		this.address = adress;
		this.port = port;
	}
	
	
	
	
	@Override
	public void run(){
		
//		System.out.println(address +":"+ port);
		
		try(Socket pubSocket=new Socket(address, port); BufferedReader inReader = new BufferedReader(new InputStreamReader(pubSocket.getInputStream()))) {
			
			
			while(true){
				
				
				try{
					
				
				String line = inReader.readLine();
				String[] parameters = line.split(" ", 2);
				
				//TODO: Catch Exceptions
				String protocol = parameters[0];
				CommandExpression commandExp = CommandExpression.valueOf(parameters[1]);
				
				if(protocol.equals(PROTOCOL_VERSION)){
					
					
					
					
					switch (commandExp) {
					
					case MESSAGE:
						//read date
						{line = inReader.readLine();
						String date = line.substring(line.indexOf(' ')+1);
						
						//read name
						line = inReader.readLine();
						String username = line.substring(line.indexOf(' ')+1);
						
						//read text
						line = inReader.readLine();
						String text = line.substring(line.indexOf(' ')+1);
						
						System.out.println(date+" | "+username + ": " + text);
						break;}

					case EVENT:
					{
						//read date
						line = inReader.readLine();
						String date = line.substring(line.indexOf(' ')+1);
						
						//read description
						line = inReader.readLine();
						String description = line.substring(line.indexOf(' ')+1);
						
						System.out.println(date+" | "+ description);
						break;

					}
						
						
					default:
						//Ignore
						break;
					}
					
				}
				
				}catch (IllegalArgumentException e) {
					//DO Nothing
				}catch (ArrayIndexOutOfBoundsException e) {
					//DO Nothing
				}catch (NullPointerException e) {
					//DO Nothing
				}
				
				
			}
			
			
		}catch (IOException e) {
			// TODO: handle exception
		}
		
		
		
		
		
		
		
		
	}
	
}
