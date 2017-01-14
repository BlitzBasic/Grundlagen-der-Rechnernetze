package de.uulm.in.vs.grn.chat.client.messages;

public class SystemMessage implements Displayable {

	private String message;
	
	public SystemMessage(String message) {
		this.message=message;
	}

	@Override
	public void display() {
		System.out.println(message);

	}

}
