package de.uulm.in.vs.grn.chat.client.messages.events;

/**
 * represents an incoming message
 * @author Marius
 *
 */
public class GRNCPMessageEvent extends Event {

	private final String username;
	private final String text;

	/**
	 * specific event version that also has a text to it, that will be displayed alongside the user who sent this message
	 * @param date
	 * @param username
	 * @param text
	 */
	public GRNCPMessageEvent(String date, String username, String text) {
		super(date);
		this.username = username;
		this.text = text;
	}

	/**
	 * displays a Message in the command line
	 */
	@Override
	public void display() {
		System.out.println(date + " | " + username + ": " + text);
		
	}



	/**
	 * gets the username who sent a the message
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * gets the text of the message that was sent
	 * @return
	 */
	public String getText() {
		return text;
	}
	
	
	
}
