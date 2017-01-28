package de.uulm.in.vs.grn.chat.server.messages.events;

import de.uulm.in.vs.grn.chat.server.messages.Displayable;
import de.uulm.in.vs.grn.chat.server.messages.GRNCPMessage;

/**
 * model for events
 * @author Marius
 *
 */
public abstract class Event implements GRNCPMessage, Displayable {

	protected final String date;

	/**
	 * sets the date on which the message was sent
	 * @param date
	 */
	public Event(String date) {
		super();
		this.date = date;
	}

	/**
	 * used to determine when an Event was created
	 * @return
	 */
	public String getDate() {
		return date;
	}
	


}
