package de.uulm.in.vs.grn.chat.client.events;


public abstract class GRNCPEvent {

	private final String date;

	public GRNCPEvent(String date) {
		super();
		this.date = date;
	}
	
	
	public String getDate() {
		return date;
	}


	public abstract void handle();
	
	
	
	
	
	
}
