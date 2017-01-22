package de.uulm.in.vs.grn.chat.client.messages.responses;

/**
 * represents a loggedin response
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

}
