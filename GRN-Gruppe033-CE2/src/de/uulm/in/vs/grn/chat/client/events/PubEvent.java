package de.uulm.in.vs.grn.chat.client.events;

public class PubEvent extends GRNCPEvent {

	private final String description;

	public PubEvent(String date, String description) {
		super(date);
		this.description = description;
	}

	@Override
	public void handle() {
		System.out.println(date + " | " + description);

	}

}
