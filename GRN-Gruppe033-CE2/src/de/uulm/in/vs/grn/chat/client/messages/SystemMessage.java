package de.uulm.in.vs.grn.chat.client.messages;

/**
 * Message type to represent internal messages
 * @author Marius
 *
 */
public class SystemMessage implements Displayable {

	private String message;
	
	public SystemMessage(String message) {
		this.message=message;
	}

	/**
	 * print the system message
	 */
	@Override
	public void display() {
		System.out.println(message);

	}

}
