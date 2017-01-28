package de.uulm.in.vs.grn.chat.server.command;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.uulm.in.vs.grn.chat.server.PubSubHandlerGroup;

public class CommandAcceptor implements Runnable {

	boolean active = false;

	ServerSocket commandSocket;
	PubSubHandlerGroup pubSubHandlerGroup;

	public CommandAcceptor(int port, PubSubHandlerGroup pubSubHandlerGroup) throws IOException {
		super();
		active = true;
		commandSocket = new ServerSocket(port);
		this.pubSubHandlerGroup = pubSubHandlerGroup;
	}

	@Override
	public void run() {
		ExecutorService pool = Executors.newCachedThreadPool();

		while (active) {
			try {
				pool.submit(new CommandHandler(commandSocket.accept(), pubSubHandlerGroup));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
