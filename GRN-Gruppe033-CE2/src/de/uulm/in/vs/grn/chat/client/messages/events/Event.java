package de.uulm.in.vs.grn.chat.client.messages.events;

import de.uulm.in.vs.grn.chat.client.messages.GRNCPDisplayableMessage;

public abstract class Event implements GRNCPDisplayableMessage {

	protected final String date;

	public Event(String date) {
		super();
		this.date = date;
	}

	@Override
	public void handle() {
		display();
	}
	
	public String getDate() {
		return date;
	}

	

}
