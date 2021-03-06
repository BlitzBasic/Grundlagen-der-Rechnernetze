package de.uulm.in.vs.grn.chat.client.messages.responses;

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

}
