package de.uulm.in.vs.grn.chat.server.messages.requests;

import java.io.IOException;
import java.io.Writer;

import de.uulm.in.vs.grn.chat.server.GRNCPServer;

/**
 * represents a login request
 * 
 * @author Marius
 *
 */
public class GRNCPPing extends Request {

	private final String pingMessage = "PING " + GRNCPServer.PROTOCOL_VERSION + "\r\n\r\n";
	private boolean userListRequested;

	/**
	 * creates a ping request
	 * 
	 * @param userListRequested
	 *            signalizes the communicator to print list of active users if
	 *            true
	 */
	public GRNCPPing(boolean userListRequested) {
		super();
		this.userListRequested = userListRequested;
	}

	@Override
	public void send(Writer writer) throws IOException {
		writer.write(pingMessage);
		writer.flush();
	}

	public boolean isUserListRequested() {
		return userListRequested;
	}

}
