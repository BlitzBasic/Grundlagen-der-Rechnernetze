package de.uulm.in.vs.grn.chat.client.messages.events;

/**
 * represents a chat event
 * @author Marius
 *
 */
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
