package de.uulm.in.vs.grn.chat.server.command;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.uulm.in.vs.grn.chat.server.pubsub.PubSubHandlerGroup;

/**
 * Accepts command connections
 * @author Andreas
 *
 */
public class CommandAcceptor implements Runnable {

	private boolean active = false;

	private ServerSocket commandSocket;
	private PubSubHandlerGroup pubSubHandlerGroup;
	ExecutorService pool;

	public CommandAcceptor(int port, PubSubHandlerGroup pubSubHandlerGroup) throws IOException {
		super();
		commandSocket = new ServerSocket(port);
		this.pubSubHandlerGroup = pubSubHandlerGroup;
		pool = Executors.newCachedThreadPool();
		active = true;
	}

	/**
	 * adds a new command handler to the pool that can communicate with the clients
	 */
	@Override
	public void run() {

		while (active) {
			try {
				pool.submit(new CommandHandler(commandSocket.accept(), pubSubHandlerGroup));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void disable() {
		active = false;
		pool.shutdown();
	}

}
