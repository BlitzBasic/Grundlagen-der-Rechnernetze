package de.uulm.in.vs.grn.chat.server.command;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.uulm.in.vs.grn.chat.server.pubsub.PubSubHandlerGroup;

public class CommandAcceptor implements Runnable {

	private boolean active = false;

	private ServerSocket commandSocket;
	private PubSubHandlerGroup pubSubHandlerGroup;

	public CommandAcceptor(int port, PubSubHandlerGroup pubSubHandlerGroup) throws IOException {
		super();
		commandSocket = new ServerSocket(port);
		this.pubSubHandlerGroup = pubSubHandlerGroup;
		active = true;
	}

	@Override
	public void run() {
		ExecutorService pool = Executors.newCachedThreadPool();
		System.out.println("voll krass gestartet und so");
		while (active) {
			try {
				pool.submit(new CommandHandler(commandSocket.accept(), pubSubHandlerGroup));
				System.out.println("yo");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
