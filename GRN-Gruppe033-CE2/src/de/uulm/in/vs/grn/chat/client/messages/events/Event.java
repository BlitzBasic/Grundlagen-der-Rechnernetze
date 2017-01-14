package de.uulm.in.vs.grn.chat.client.messages.events;

import de.uulm.in.vs.grn.chat.client.messages.Displayable;
import de.uulm.in.vs.grn.chat.client.messages.GRNCPMessage;

public abstract class Event implements GRNCPMessage, Displayable {

	protected final String date;

	public Event(String date) {
		super();
		this.date = date;
	}

	public String getDate() {
		return date;
	}

}
