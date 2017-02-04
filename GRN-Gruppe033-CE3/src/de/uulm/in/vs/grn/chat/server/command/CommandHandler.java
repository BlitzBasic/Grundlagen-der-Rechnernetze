package de.uulm.in.vs.grn.chat.server.command;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.time.LocalDateTime;

import de.uulm.in.vs.grn.chat.server.GRNCPServer;
import de.uulm.in.vs.grn.chat.server.messages.events.Event;
import de.uulm.in.vs.grn.chat.server.messages.events.GRNCPEvent;
import de.uulm.in.vs.grn.chat.server.messages.events.GRNCPMessageEvent;
import de.uulm.in.vs.grn.chat.server.messages.responses.GRNCPByebye;
import de.uulm.in.vs.grn.chat.server.messages.responses.GRNCPError;
import de.uulm.in.vs.grn.chat.server.messages.responses.GRNCPLoggedin;
import de.uulm.in.vs.grn.chat.server.messages.responses.GRNCPSent;
import de.uulm.in.vs.grn.chat.server.messages.responses.Response;
import de.uulm.in.vs.grn.chat.server.pubsub.PubSubHandlerGroup;

public class CommandHandler implements Runnable {

	private Socket socket;
	private PubSubHandlerGroup handlerGroup;
	private boolean active = false;
	private boolean loggedin = false;
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

					do {

						// 1. wait for request
						// 2. check login state
						// 3. forward to handlerGroup
						// 4. send response

						// System.out.println("warte auf request");
						Response response = null;
						request = commandReader.readLine();
						// System.out.println(request);
						if (request.equals("")) {
							// System.out.println("test");
							date = (LocalDateTime.now()).format(GRNCPServer.DATIMINATOR);
							switch (command) {
							case "LOGIN":
								// System.out.println("login");
								if (loggedin) {
									response = new GRNCPError(date, "You are already logged in as " + username);
									// TODO: check limitations (not critical)
								} else {
									response = new GRNCPLoggedin(date);
									// System.out.println("loggedin und so");
									this.username = username;
									Event loginEvent = new GRNCPEvent(date, username + " entered the server.");
									handlerGroup.addEvent(loginEvent);
									loggedin = true;
								}

								command = "";
								break;
							case "PING":
								if (loggedin) {
									response = new GRNCPError(date, "This request is not implemented yet.");
								} else {
									response = new GRNCPError(date, "You are not logged in.");
								}
								command = "";
								break;
							case "BYE":
								if (loggedin) {
									response = new GRNCPByebye(date);
									logout(date);

								} else {
									response = new GRNCPError(date, "You are not logged in.");
								}

								command = "";
								break;
							case "SEND":
								if (loggedin) {
									// TODO: check limitations (not critical)
									Event sendEvent = new GRNCPMessageEvent(date, this.username, text);
									handlerGroup.addEvent(sendEvent);
									response = new GRNCPSent(date);

								} else {
									response = new GRNCPError(date, "You are not logged in.");
								}
								command = "";
								break;
							default:
								response = new GRNCPError(date, "You entered an unknown request.");
								command = "";
								break;
							}

						} else if (request.split(" ")[1].equals("GRNCP/0.1")) {
							command = request.split(" ")[0].trim();
							// System.out.println(command);
						} else if ((field = request.split(": ")).length > 1) {
							if (field[0].equals("Username")) {
								username = field[1].trim();
								// System.out.println(username);
							} else if (field[0].equals("Text")) {
								text = field[1].trim();
							}
						}
						
						if (response != null) {
							response.send(commandWriter);
							// System.out.println("response sent");
							// send response
						}
					} while (request != null && active);

				} catch (ArrayIndexOutOfBoundsException e) {
					// do nothing
				} catch (IndexOutOfBoundsException e) {
					// do nothing
				} catch (NullPointerException e) {
					// do nothing
				} catch (IOException e) {
					logout((LocalDateTime.now()).format(GRNCPServer.DATIMINATOR));
				}

			}

		} catch (Exception e) {
			System.err.println("DOOM");
			logout((LocalDateTime.now()).format(GRNCPServer.DATIMINATOR));
			e.printStackTrace();
		}

	}

	/**
	 * @param date
	 */
	private void logout(String date) {
		Event byeEvent = new GRNCPEvent(date, this.username + " left the server.");
		handlerGroup.addEvent(byeEvent);
		loggedin = false;
		active = false;
	}

	
	public void disable() {
		active = false;
	}

}
