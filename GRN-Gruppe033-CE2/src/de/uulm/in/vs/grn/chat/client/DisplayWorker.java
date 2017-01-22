package de.uulm.in.vs.grn.chat.client;

import java.util.concurrent.LinkedBlockingQueue;

import de.uulm.in.vs.grn.chat.client.messages.Displayable;

/**
 * This class can be used to display all messages in their original order
 * @author Marius
 *
 */
public class DisplayWorker extends Thread {

	private LinkedBlockingQueue<Displayable> messages;
	private boolean active = false;

	public DisplayWorker() {
		super();
		messages = new LinkedBlockingQueue<Displayable>();
		active = true;
	}
	
	/**
	 * displays all messages
	 */
	@Override
	public void run() {
		while (active) {

			try {
				Displayable message = messages.take();
				message.display();
			} catch (InterruptedException e) {
				// nothing
			}

		}
	}

	/**
	 * adds elements to the message queue that are to be displayed
	 * @param displayable
	 */
	public void addDisplayable(Displayable displayable) {
		messages.add(displayable);
	}

	/**
	 * disables the DisplayWorker
	 */
	public void disable() {
		active = false;
		this.interrupt();
	}

}
