package de.uulm.in.vs.grn.chat.client;

import java.util.LinkedList;
import java.util.Queue;

import de.uulm.in.vs.grn.chat.client.events.GRNCPEvent;

public class EventWorker extends Thread {

	private Queue<GRNCPEvent> events;
	private boolean active = false;

	public EventWorker() {
		super();
		events = new LinkedList<GRNCPEvent>();
		active = true;
	}

	@Override
	public void run() {
		while (active) {
			handleQueue();
		}
	}

	public synchronized void addEvent(GRNCPEvent event) {
		events.add(event);
		notify();

	}

	public synchronized void handleQueue() {
		try {
			wait();
		} catch (InterruptedException e) {
			// nothing
		}
		while (!events.isEmpty()) {
			GRNCPEvent event = events.poll();
			event.handle();
		}
	}

}
