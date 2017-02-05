package de.uulm.in.vs.grn.chat.server.pubsub;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Accepts a PubSubConnection
 * @author Andreas
 *
 */
public class PubSubAcceptor implements Runnable {

	private ServerSocket pubSubSocket;
	private PubSubHandlerGroup pubSubHandlerGroup;
	private boolean active = false;
	ExecutorService pool;

	public PubSubAcceptor(int port, PubSubHandlerGroup pubSubHandlerGroup) throws IOException {
		super();
		pubSubSocket = new ServerSocket(port);
		this.pubSubHandlerGroup = pubSubHandlerGroup;
		pool = Executors.newCachedThreadPool();
		active = true;
	}
	
	/**
	 * adds a PubSubHandler to thePubSubHandlerGroup Queue and to the executor pool
	 */
	@Override
	public void run() {

		while (active) {
			try {
				PubSubHandler handler = new PubSubHandler(pubSubSocket.accept());
				pubSubHandlerGroup.addHandler(handler);
				pool.submit(handler);
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
