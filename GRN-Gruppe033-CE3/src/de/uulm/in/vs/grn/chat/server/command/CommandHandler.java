package de.uulm.in.vs.grn.chat.server.command;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import de.uulm.in.vs.grn.chat.server.GRNCPServer;
import de.uulm.in.vs.grn.chat.server.PubSubHandlerGroup;
import de.uulm.in.vs.grn.chat.server.messages.events.Event;
import de.uulm.in.vs.grn.chat.server.messages.events.GRNCPMessageEvent;
import de.uulm.in.vs.grn.chat.server.messages.requests.GRNCPSend;
import de.uulm.in.vs.grn.chat.server.messages.responses.GRNCPLoggedin;
import de.uulm.in.vs.grn.chat.server.messages.responses.GRNCPSent;
import de.uulm.in.vs.grn.chat.server.messages.responses.Response;

public class CommandHandler implements Runnable {

	private Socket socket;
	private PubSubHandlerGroup handlerGroup;
	boolean active = false;
	boolean loggedin = false;
	String username;
	
	

	CommandHandler(Socket socket, PubSubHandlerGroup handlerGroup) {
		this.socket = socket;
		this.handlerGroup = handlerGroup;
		active = true;
	}

	@Override
	public void run() {
					// 1. check login state
					// 2. wait for request
					// 3. forward to handlerGroup
					// 4. send response
				
		
		try (BufferedReader commandReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				BufferedWriter commandWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

			while (active) {
				try {

					String request = "";
					String[] field;
					String command = "";
					String username = "";
					String text = "";
					String date = "";
					Response response = null;
					do {
						request = commandReader.readLine();
						if (request.split(" ")[1].equals("GRNCP/0.1")) {
							command = request.split(" ")[0];
						} else if ((field = request.split(": ")).length > 1) {
							if (field[0].equals("Username")){
								username = field[1].trim();
							}else if (field[0].equals("Text")){
								text = field[1].trim();
							}
						} else if (request.equals("")) {
							date = (LocalDateTime.now()).format(GRNCPServer.DATIMINATOR);
							switch (command) {
							case "LOGIN":
								//TODO: Peter regex schreiben
								response = new GRNCPLoggedin(date);
								command = "";
								break;
							case "PING":
								//nothing
								command = "";
								break;
							case "BYE":
								//nothing
								// TODO
								command = "";
								break;
							case "SEND":
								// TODO Peter conventions
								Event event = new GRNCPMessageEvent(date, username, text);
								response = new GRNCPSent(date);
								
								command = "";
								break;
							default:
								//nothing
								// TODO
								command = "";
								break;
							}

						}
					} while (request != null);
					
					if(response!=null){
						response.send(commandWriter);
						//send response
					}

				} catch (ArrayIndexOutOfBoundsException e) {
					// do nothing
				} catch (IndexOutOfBoundsException e) {
					// do nothing
				} catch (NullPointerException e) {
					// do nothing
				} catch (SocketException e) {
					active = false;
					//TODO
				}

			}

		} catch (Exception e) {
			// TODO
		}
		
		
		while (active) {
			while(!loggedin){
				
			}
			// 1. check login state
			// 2. wait for request
			// 3. forward to handlerGroup
			// 4. send response
		}

	}

}
