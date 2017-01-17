package de.uulm.in.vs.grn.chat.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import de.uulm.in.vs.grn.chat.client.messages.events.GRNCPEvent;
import de.uulm.in.vs.grn.chat.client.messages.events.GRNCPMessageEvent;
import de.uulm.in.vs.grn.chat.client.messages.requests.GRNCPLogin;
import de.uulm.in.vs.grn.chat.client.messages.requests.Request;
import de.uulm.in.vs.grn.chat.client.messages.responses.GRNCPError;
import de.uulm.in.vs.grn.chat.client.messages.responses.GRNCPLoggedin;

public class CommandCommunicator extends Thread {

	private Queue<Request> requests;
	private InetAddress address;
	private int port;
	private boolean active = false;
	private boolean loggedIn = false;
	
	public CommandCommunicator(InetAddress address, int port) {
		super();
		this.address = address;
		this.port = port;
		requests = new LinkedList<Request>();
		active = true;
	}

	public void run() {
		try (Socket commandSocket = new Socket(address, port);
				BufferedReader commandSocketReader = new BufferedReader(
						new InputStreamReader(commandSocket.getInputStream()));
				BufferedWriter commandSocketWriter = new BufferedWriter(
						new OutputStreamWriter(commandSocket.getOutputStream()))) {

			while (active) {
				workOfQueue(commandSocketWriter, commandSocketReader);
			}

		} catch (Exception e) {
			System.out.println("An exception occured, please reconnect.");
			GRNCP.initiateConnection();
		}

	}

	public synchronized void addRequest(Request request) {
		if(loggedIn || (request instanceof GRNCPLogin))
		requests.add(request);
		notifyAll();

	}

	public synchronized void workOfQueue(BufferedWriter writer, BufferedReader reader) {
		try {
			wait();
		} catch (InterruptedException e) {
			// nothing
		}
		while (!requests.isEmpty()) {
			try {
				Request request = requests.poll();
				request.send(writer);
				
				
								
			} catch (Exception e) {
				System.err.println("Request could not be sent");
				e.printStackTrace(System.err);
			}
			
			
			
			try {

				String response = "";

				String command = "";
				String date = "";
				String reason = "";
				String[] field;
				ArrayList<String> usernames = new ArrayList<String>();
				do {
					response = reader.readLine();
					// System.out.println(response);
					if (response.split(" ")[0].equals("GRNCP/0.1")) {
						command = response.split(" ")[1];
					} else if ((field = response.split(": ")).length > 1) {
						if (field[0].equals("Date"))
							date = field[1];
						else if (field[0].equals("Reason"))
							reason = field[1].trim();
						else if (field[0].equals("Usernames")){
							String[] usernameArray = field[1].split(",");
							for(String s: usernameArray){
								usernames.add(s);
							}
						}
							
					} else if (response.equals("")) {
						switch (command) {
						case "LOGGEDIN":
							GRNCPLoggedin loggedinResponse = new GRNCPLoggedin(date);
							loggedinResponse.handle();
							command = "";
							break;
						case "ERROR":
							GRNCPError errorResponse = new GRNCPError(date, reason);
							errorResponse.handle();
							command = "";
							break;
						case "EXPIRED":
							//TODO
							command = "";
							break;
						case "SENT":
							//TODO
							command = "";
							break;
						case "BYEBYE":
							//TODO
							command = "";
							break;
						case "PONG":
							//TODO
							command = "";
							break;
						}

					}
				} while (response != null);

			} catch (ArrayIndexOutOfBoundsException e) {
				// do nothing
			} catch (IndexOutOfBoundsException e) {
				// do nothing
			} catch (NullPointerException e) {
				// do nothing
			} catch (SocketException e){
				active = false;
				System.out.println("Connection lost, please reconnect");
				while(GRNCP.initiateConnection())
					;
			} catch (IOException e) {
				active = false;
				System.out.println("Connection lost, please reconnect");
				while(GRNCP.initiateConnection());
			}
			
		}
	}

	public void disable() {
		active = false;
		
	}

	public boolean isLoggedIn(){
		return loggedIn;
	}
	
	public void login(String username) {
		if(!loggedIn){
			addRequest(new GRNCPLogin(username));
			
			
			
		}
		
	}
}
