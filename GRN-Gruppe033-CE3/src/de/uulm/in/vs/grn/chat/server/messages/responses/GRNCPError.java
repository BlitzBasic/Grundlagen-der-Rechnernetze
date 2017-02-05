package de.uulm.in.vs.grn.chat.server.messages.responses;

import java.io.IOException;
import java.io.Writer;

/**
 * represents an error response
 * 
 * @author Marius
 *
 */
public class GRNCPError extends Response {
	private final String reason;

	public GRNCPError(String date, String reason) {
		super(date);
		this.reason = reason;
	}

	public String getReason() {
		return reason;
	}

	/**
	 * displays the date of the Error response and why it occurred
	 */
	@Override
	public void display() {
		System.out.println(date + " | " + reason);

	}

	/**
	 * writes a Error Response
	 * @param writer
	 * @throws IOException
	 */
	@Override
	public void send(Writer writer) throws IOException {
		writer.write("GRNCP/0.1 ERROR\r\nDate: " + date + "\r\nReason: " + reason + "\r\n\r\n");
		writer.flush();
	}
}
