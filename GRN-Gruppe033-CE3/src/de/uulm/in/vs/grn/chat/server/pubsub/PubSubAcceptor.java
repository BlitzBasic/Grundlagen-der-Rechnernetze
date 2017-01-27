package de.uulm.in.vs.grn.chat.server.pubsub;

import java.io.IOException;
import java.net.ServerSocket;

public class PubSubAcceptor implements Runnable {

	ServerSocket pubSubSocket;
	
	public PubSubAcceptor(int port) throws IOException {
		super();
		pubSubSocket = new ServerSocket(port);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
