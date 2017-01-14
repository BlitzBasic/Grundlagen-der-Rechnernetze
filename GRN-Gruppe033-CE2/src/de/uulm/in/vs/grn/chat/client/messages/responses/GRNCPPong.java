package de.uulm.in.vs.grn.chat.client.messages.responses;

import java.util.ArrayList;

public class GRNCPPong extends Response {

	private final ArrayList<String> usernames;
	
	public GRNCPPong(String date, ArrayList<String> usernames) {
		super(date);
		this.usernames = usernames;
	}

	@Override
	public void handle() {
		// TODO Auto-generated method stub

	}

	public ArrayList<String> getUsernames() {
		return usernames;
	}

	
	
}
