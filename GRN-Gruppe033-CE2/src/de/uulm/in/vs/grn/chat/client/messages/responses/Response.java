package de.uulm.in.vs.grn.chat.client.messages.responses;

import de.uulm.in.vs.grn.chat.client.messages.GRNCPMessage;

public abstract class Response implements GRNCPMessage {

	protected final String date;
	
	public Response(String date){
		this.date = date;
	}

	public abstract void handle();

	public String getDate() {
		return date;
	}
	
	
}
