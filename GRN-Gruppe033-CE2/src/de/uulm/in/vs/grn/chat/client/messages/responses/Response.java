package de.uulm.in.vs.grn.chat.client.messages.responses;

import de.uulm.in.vs.grn.chat.client.messages.Displayable;
import de.uulm.in.vs.grn.chat.client.messages.GRNCPMessage;

/**
 * represents any kind of response
 * @author Marius
 *
 */
public abstract class Response implements GRNCPMessage, Displayable {

	protected final String date;
	
	public Response(String date){
		this.date = date;
	}

	/**
	 * extracts the date out of any response from the server
	 * @return
	 */
	public String getDate() {
		return date;
	}
	
	
}
