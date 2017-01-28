package de.uulm.in.vs.grn.chat.server.pubsub;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import de.uulm.in.vs.grn.chat.server.messages.events.Event;

public class PubSubHandler implements Runnable {

	Socket socket;
	boolean active = false;
	BlockingQueue<Event> events;
	
	public PubSubHandler(Socket socket) {
		this.socket = socket;
		events = new LinkedBlockingQueue<Event>();
		active = true;
	}

	@Override
	public void run() {
		try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))){
		while(active){
			try {
				Event event = events.take();
				event.send(writer);
				
			} catch (InterruptedException e) {
				// TODO: handle exception
			} catch (IOException e){
				System.err.println("Message couldn't be sent");
			}
		}
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

	public boolean isActive() {
		return active;
	}
	
	public void addEvent(Event event){
		events.add(event);
	}
	
	

}
