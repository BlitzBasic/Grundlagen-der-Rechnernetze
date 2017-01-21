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

	public void addDisplayable(Displayable displayable) {
		messages.add(displayable);
	}

	public void disable() {
		active = false;
	}

}
