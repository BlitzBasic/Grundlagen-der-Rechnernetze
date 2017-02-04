package de.uulm.in.vs.grn.chat.server.messages.responses;

import java.io.IOException;
import java.io.Writer;

/**
 * represents a sent response
 * @author Marius
 *
 */
public class GRNCPSent extends Response {
	public GRNCPSent(String date){
		super(date);
	}
	
	/**
	 * displays the response sent after sending a message
	 */
	@Override
	public void display() {
		System.out.println(date + " | Your message was succesfully sent.");
		
	}
	
	@Override
	public void send(Writer writer) throws IOException{
			writer.write("GRNCP/0.1 SENT\r\nDate : " + date + "\r\n\r\n");
			writer.flush();
	}

}
