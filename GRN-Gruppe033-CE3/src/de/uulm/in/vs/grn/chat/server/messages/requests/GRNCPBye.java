package de.uulm.in.vs.grn.chat.server.messages.requests;

import java.io.IOException;
import java.io.Writer;

import de.uulm.in.vs.grn.chat.server.GRNCPServer;

/**
 * represents a bye request
 * @author Marius
 *
 */
public class GRNCPBye extends Request {
	
	private final String byeMessage = "BYE " + GRNCPServer.PROTOCOL_VERSION + "\r\n\r\n";
	
	/**
	 * sends a disconnect request to the server
	 */
	public GRNCPBye() {
		super();
	}

	/**
	 * writes a Bye Request
	 */
	@Override
	public void send(Writer writer) throws IOException {
		writer.write(byeMessage);
		writer.flush();
	}
}
