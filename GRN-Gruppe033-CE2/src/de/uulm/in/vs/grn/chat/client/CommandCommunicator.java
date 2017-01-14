package de.uulm.in.vs.grn.chat.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import de.uulm.in.vs.grn.chat.client.messages.requests.Request;

public class CommandCommunicator extends Thread {

	private Queue<Request> requests;
	private InetAddress address;
	private int port;
	private boolean active = false;

	public CommandCommunicator(InetAddress address, int port) {
		super();
		this.address = address;
		this.port = port;
		requests = new LinkedList<Request>();
		active = true;
	}

	public void run() {
		try (Socket commandSocket = new Socket(address, port);
				BufferedReader commandSocketReader = new BufferedReader(
						new InputStreamReader(commandSocket.getInputStream()));
				BufferedWriter commandSocketWriter = new BufferedWriter(
						new OutputStreamWriter(commandSocket.getOutputStream()))) {

			while (active) {
				workOfQueue(commandSocketWriter);
			}

		} catch (Exception e) {
			System.out.println("An exception occured, please reconnect.");
			GRNCP.initiateConnection();
		}

	}

	public synchronized void addRequest(Request request) {
		requests.add(request);
		notify();

	}

	public synchronized void workOfQueue(Writer writer) {
		try {
			wait();
		} catch (InterruptedException e) {
			// nothing
		}
		while (!requests.isEmpty()) {
			Request request = requests.poll();
			request.send(writer);
		}
	}

	public void disable() {
		active = false;
		
	}
}
