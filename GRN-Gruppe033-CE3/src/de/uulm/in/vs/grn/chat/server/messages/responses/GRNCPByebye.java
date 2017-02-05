package de.uulm.in.vs.grn.chat.server.messages.responses;

import java.io.IOException;
import java.io.Writer;

/**
 * represents a byebye response
 * 
 * @author Marius
 *
 */
public class GRNCPByebye extends Response {
	public GRNCPByebye(String date) {
		super(date);
	}

	/**
	 * displays any Byebye response the client gets from the server
	 */
	@Override
	public void display() {
		System.out.println(date + " | You are logged out.");

	}

	/**
	 * writes a ByeBye Response
	 * @param writer
	 * @throws IOException
	 */
	@Override
	public void send(Writer writer) throws IOException {

		writer.write("GRNCP/0.1 BYEBYE\r\nDate: " + date + "\r\n\r\n");
		writer.flush();
	}
}
