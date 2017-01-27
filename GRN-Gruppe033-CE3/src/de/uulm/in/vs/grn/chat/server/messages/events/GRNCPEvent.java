package de.uulm.in.vs.grn.chat.server.messages.events;

/**
 * represents a chat event
 * @author Marius
 *
 */
public class GRNCPEvent extends Event {

	private final String description;

	/**
	 * extends the event object with an description
	 * @param date
	 * @param description
	 */
	public GRNCPEvent(String date, String description) {
		super(date);
		this.description = description;
	}


	/**
	 * used to show all events in the command line
	 */
	@Override
	public void display() {
		System.out.println(date + " | " + description);
		
	}

	/**
	 * used to determine what to use
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	
	

}
