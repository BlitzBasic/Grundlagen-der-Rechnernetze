package de.uulm.in.vs.grn.chat.client.messages.events;

public class GRNCPEvent extends Event {

	private final String description;

	public GRNCPEvent(String date, String description) {
		super(date);
		this.description = description;
	}


	@Override
	public void display() {
		System.out.println(date + " | " + description);
		
	}


	public String getDescription() {
		return description;
	}
	
	

}
