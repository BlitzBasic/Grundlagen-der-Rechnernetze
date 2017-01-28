package de.uulm.in.vs.grn.chat.server.command;

import java.net.Socket;

import de.uulm.in.vs.grn.chat.server.PubSubHandlerGroup;

public class CommandHandler implements Runnable {

	private Socket socket;
	private PubSubHandlerGroup handlerGroup;
	boolean active = false;

	CommandHandler(Socket socket, PubSubHandlerGroup handlerGroup) {
		this.socket = socket;
		this.handlerGroup = handlerGroup;
		active = true;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (active) {
			// 1. check login state
			// 2. wait for request
			// 3. forward to handlerGroup
			// 4. send response
		}

	}

}
