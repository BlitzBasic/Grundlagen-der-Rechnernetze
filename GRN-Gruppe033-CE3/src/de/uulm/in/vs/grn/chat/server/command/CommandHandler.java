package de.uulm.in.vs.grn.chat.server.command;

import java.net.Socket;

public class CommandHandler implements Runnable {

	private Socket socket;
	
	CommandHandler(Socket socket){
		this.socket = socket;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
