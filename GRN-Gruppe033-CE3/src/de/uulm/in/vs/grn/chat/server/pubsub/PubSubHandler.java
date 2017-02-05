package de.uulm.in.vs.grn.chat.server.pubsub;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import de.uulm.in.vs.grn.chat.server.messages.events.Event;

/**
 * writes events for the user the Handler is handling
 * @author Andreas
 *
 */
public class PubSubHandler implements Runnable {

	private Socket socket;
	private boolean active = false;
	private BlockingQueue<Event> events;

	public PubSubHandler(Socket socket) {
		this.socket = socket;
		events = new LinkedBlockingQueue<Event>();
		active = true;
	}

	@Override
	public void run() {
		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
			while (active) {
				try {
					Event event = events.take();
					event.send(writer);

				} catch (InterruptedException e) {
					//nothing
				} catch (IOException e) {
					disable();
				}
			}
		} catch (Exception e) {
			disable();
		}
	}

	public boolean isActive() {
		return active;
	}

	public void addEvent(Event event) {
		events.add(event);
	}

	public void disable() {
		active = false;
	}

}
