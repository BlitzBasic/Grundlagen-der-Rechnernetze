package de.uulm.in.vs.grn.chat.client;

import de.uulm.in.vs.grn.chat.client.messages.requests.GRNCPPing;

public class ConnectionKeeper extends Thread {

	private final long leaseTime;
	private boolean active = false;
	private CommandCommunicator communicator;

	public ConnectionKeeper(long leaseTime, CommandCommunicator communicator) {
		super();
		this.leaseTime = leaseTime;
		this.communicator = communicator;
		active = true;

	}

	public void run() {
		while (active) {
			try {
				communicator.addRequest(new GRNCPPing());
				sleep(leaseTime);
			} catch (InterruptedException e) {
				// nothing
			}
		}

	}

	public void disable() {
		active = false;
	}

}
