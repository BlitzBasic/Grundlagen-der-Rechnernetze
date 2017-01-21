package de.uulm.in.vs.grn.chat.client.messages.responses;

import java.util.ArrayList;

public class GRNCPPong extends Response {

	private final ArrayList<String> usernames;
	
	public GRNCPPong(String date, ArrayList<String> usernames) {
		super(date);
		this.usernames = usernames;
	}


	public ArrayList<String> getUsernames() {
		return usernames;
	}

	@Override
	public void display() {
		System.out.println(date + " | Currently logged in users:");
		for(String name : usernames){
			System.out.println("                      "+name);
		}
		
	}

	
	
}
