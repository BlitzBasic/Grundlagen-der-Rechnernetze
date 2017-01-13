package de.uulm.in.vs.grn.chat.client.events;

public class MessageEvent extends GRNCPEvent {

	private final String username;
	private final String text;

	public MessageEvent(String date, String username, String text) {
		super(date);
		this.username = username;
		this.text = text;
	}

	@Override
	public void handle() {
		System.out.println(date + " | " + username + ": " + text);
	}

}
