package de.uulm.in.vs.grn.chat.server.messages.responses;

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

}
