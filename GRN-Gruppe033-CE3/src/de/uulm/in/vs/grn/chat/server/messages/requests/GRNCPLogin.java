package de.uulm.in.vs.grn.chat.server.messages.requests;

import java.io.IOException;
import java.io.Writer;

import de.uulm.in.vs.grn.chat.server.GRNCPServer;

/**
 * represents a login request
 * @author Marius
 *
 */
public class GRNCPLogin extends Request {

	private final String username;

	/**
	 * creates a login request including the username
	 * @param username
	 */
	public GRNCPLogin(String username) {
		super();
		this.username = username;
	}

	@Override
	public void send(Writer writer) throws IOException {
		writer.write("LOGIN " + GRNCPServer.PROTOCOL_VERSION + "\r\nUsername: " + username + "\r\n\r\n");
		writer.flush();
	}
}
