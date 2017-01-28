package de.uulm.in.vs.grn.chat.server.command;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.uulm.in.vs.grn.chat.server.pubsub.PubSubAcceptor;

public class CommandAcceptor implements Runnable {
	
	boolean active = false;
	
	ServerSocket commandSocket;
	PubSubAcceptor pubSubAcceptor;
	public CommandAcceptor(int port, PubSubAcceptor pubSubAcceptor) throws IOException {
		super();
		active = true;
		commandSocket = new ServerSocket(port);
		this.pubSubAcceptor = pubSubAcceptor;
	}

	@Override
	public void run() {
		ExecutorService pool = Executors.newCachedThreadPool();
		
		while(active){
			try {
				pool.submit(new CommandHandler(commandSocket.accept()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
