package de.uulm.in.vs.grn.chat.server.messages.responses;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

/**
 * represents a pong response
 * 
 * @author Marius
 *
 */
public class GRNCPPong extends Response {

	private final ArrayList<String> usernames;

	public GRNCPPong(String date, ArrayList<String> usernames) {
		super(date);
		this.usernames = usernames;
	}

	/**
	 * takes all usernames from the given response and puts them into an
	 * ArrayList
	 * 
	 * @return
	 */
	public ArrayList<String> getUsernames() {
		return usernames;
	}

	/**
	 * displays the date and all currently logged in users in chat
	 */
	@Override
	public void display() {
		System.out.println(date + " | Currently logged in users:");
		for (String name : usernames) {
			System.out.println("                      " + name);
		}

	}

	@Override
	public void send(Writer writer) throws IOException {
		// TODO finish this (not critical)
		writer.write("");
		writer.flush();
	}

}
