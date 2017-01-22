package de.uulm.in.vs.grn.chat.client;

import de.uulm.in.vs.grn.chat.client.messages.requests.GRNCPPing;

/**
 * This class resets the lease time by sending a ping message periodically
 * @author Marius
 *
 */
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

	/**
	 * sends Ping every do often to ensure the Server won't close the connection due to inactivity
	 */
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

	/**
	 * disables the connection keeper
	 */
	public void disable() {
		active = false;
	}

}
