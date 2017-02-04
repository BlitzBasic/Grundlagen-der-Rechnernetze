package de.uulm.in.vs.grn.chat.server.pubsub;

import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import de.uulm.in.vs.grn.chat.server.messages.events.Event;

public class PubSubHandlerGroup implements Runnable {

	private LinkedList<PubSubHandler> handlers;
	private BlockingQueue<Event> events;
	private boolean active = false;

	public PubSubHandlerGroup() {
		handlers = new LinkedList<>();
		events = new LinkedBlockingQueue<Event>();
		active = true;
	}

	@Override
	public void run() {

		while (active) {
			try {
				Event event = events.take();
				for (PubSubHandler handler : handlers) {
					if (handler.isActive()) {
						handler.addEvent(event);
					} else {
						handlers.remove(handler);
					}
				}
			} catch (InterruptedException e) {
				// nothing
			}
		}

	}

	public void addHandler(PubSubHandler handler) {
		handlers.add(handler);
	}

	public void addEvent(Event event) {
		events.add(event);
	}

	public void disable() {
		active = false;
		for (PubSubHandler handler : handlers) {
			if (handler.isActive()) {
				handler.disable();
			}
		}
	}
}
