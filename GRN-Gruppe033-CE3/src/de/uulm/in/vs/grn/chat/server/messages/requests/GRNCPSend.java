package de.uulm.in.vs.grn.chat.server.messages.requests;

import java.io.IOException;
import java.io.Writer;

import de.uulm.in.vs.grn.chat.server.GRNCPServer;

/**
 * represents a send request
 * @author Marius
 *
 */
public class GRNCPSend extends Request {
	
	private final String text;
	
	/**
	 * creates a send request to be sent to the server
	 * @param text
	 */
	public GRNCPSend(String text){
		super();
		this.text = text;
	}
	
	

	@Override
	public void send(Writer writer) throws IOException {
			writer.write("SEND " + GRNCPServer.PROTOCOL_VERSION + "\r\nText: " + text + "\r\n\r\n");
			writer.flush();
	}
}
