package de.uulm.in.vs.grn.chat.server.command;

import java.io.IOException;
import java.net.ServerSocket;

import de.uulm.in.vs.grn.chat.server.pubsub.PubSubAcceptor;

public class CommandAcceptor implements Runnable {

	ServerSocket commandSocket;
	PubSubAcceptor pubSubAcceptor;
	public CommandAcceptor(int port, PubSubAcceptor pubSubAcceptor) throws IOException {
		super();
		commandSocket = new ServerSocket(port);
		this.pubSubAcceptor = pubSubAcceptor;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
