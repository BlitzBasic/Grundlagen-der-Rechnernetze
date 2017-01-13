package de.uulm.in.vs.grn.chat.client.events;

public abstract class GRNCPEvent {

	protected final String date;

	public GRNCPEvent(String date) {
		super();
		this.date = date;
	}

	public String getDate() {
		return date;
	}

	public abstract void handle();

}
