package de.uulm.in.vs.grn.chat.client.messages.responses;

/**
 * represents an expired response
 * @author Marius
 *
 */
public class GRNCPExpired extends Response {
	public GRNCPExpired(String date) {
		super(date);
	}


	@Override
	public void display() {
		System.out.println(date + " | Your connection has expired, please re-login.");
	}

}
