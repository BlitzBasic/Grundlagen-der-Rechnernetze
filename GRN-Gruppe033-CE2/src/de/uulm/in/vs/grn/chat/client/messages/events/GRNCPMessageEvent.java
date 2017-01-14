package de.uulm.in.vs.grn.chat.client.messages.events;

public class GRNCPMessageEvent extends Event {

	private final String username;
	private final String text;

	public GRNCPMessageEvent(String date, String username, String text) {
		super(date);
		this.username = username;
		this.text = text;
	}



	@Override
	public void display() {
		System.out.println(date + " | " + username + ": " + text);
		
	}



	public String getUsername() {
		return username;
	}



	public String getText() {
		return text;
	}
	
	
	
}
