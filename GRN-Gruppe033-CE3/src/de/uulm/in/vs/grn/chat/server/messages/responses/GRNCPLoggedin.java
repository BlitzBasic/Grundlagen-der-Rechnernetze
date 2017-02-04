package de.uulm.in.vs.grn.chat.server.messages.responses;

import java.io.IOException;
import java.io.Writer;

/**
 * represents a loggedIn response
 * @author Marius
 *
 */
public class GRNCPLoggedin extends Response {
	public GRNCPLoggedin(String date){
		super(date);
	}
	
	/**
	 * displays the login date and that the login was successful
	 */
	@Override
	public void display() {
		System.out.println(date + " | You are now logged in.");
		
	}
	
	@Override
	public void send(Writer writer) throws IOException{
			writer.write("GRNCP/0.1 LOGGEDIN\r\nDate: " + date + "\r\n\r\n");
			writer.flush();
	}

}
