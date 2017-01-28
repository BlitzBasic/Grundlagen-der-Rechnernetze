package de.uulm.in.vs.grn.chat.server;

import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import de.uulm.in.vs.grn.chat.server.messages.events.Event;
import de.uulm.in.vs.grn.chat.server.pubsub.PubSubHandler;

public class PubSubHandlerGroup implements Runnable {

	LinkedList<PubSubHandler> handlers;
	BlockingQueue<Event> events;
	boolean active = false;
	
	public PubSubHandlerGroup() {
		handlers = new LinkedList<>();
		events = new LinkedBlockingQueue<Event>();
		active = true;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		
		while(active){
			try {
				Event event = events.take();
				for(PubSubHandler handler : handlers){
					if(handler.isActive()){
						handler.addEvent(event);
					}else{
						handlers.remove(handler);
					}
				}
			} catch (InterruptedException e) {
				// TODO: handle exception
			}
		}

	}
	
	public void addHandler(PubSubHandler handler){
		handlers.add(handler);
	}
	
	public void addEvent(Event event){
		events.add(event);
	}

}
