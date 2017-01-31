package de.uulm.in.vs.grn.chat.server.messages.responses;

import java.io.IOException;
import java.io.Writer;

/**
 * represents an expired response
 * @author Marius
 *
 */
public class GRNCPExpired extends Response {
	public GRNCPExpired(String date) {
		super(date);
	}

	/**
	 * displays the expiration date and that the connection has expired
	 */
	@Override
	public void display() {
		System.out.println(date + " | Your connection has expired, please re-login.");
	}
	
	@Override
	public void send(Writer writer){
		try {
			writer.write("GRNCP /0.1 EXPIRED\r\nDate : " + date + "\r\n\r\n");
			writer.flush();
		} catch (IOException e) {
			System.out.println("IOException occured while trying to send package.");
		}
	}

}
