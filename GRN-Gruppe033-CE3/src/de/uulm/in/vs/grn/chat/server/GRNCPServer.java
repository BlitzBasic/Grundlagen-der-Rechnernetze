package de.uulm.in.vs.grn.chat.server;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.uulm.in.vs.grn.chat.server.command.CommandAcceptor;
import de.uulm.in.vs.grn.chat.server.pubsub.PubSubAcceptor;

/**
 * main class to start the GRNCP chat server
 * 
 * @author Marius
 *
 */
public class GRNCPServer {

	public static final String PROTOCOL_VERSION = "GRNCP/0.1";
	public static final int COMMAND_PORT = 8122;
	public static final int PUB_SUB_PORT = 8123;
	public static final DateTimeFormatter DATIMINATOR = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss");

	public static void main(String[] args) {

		try {
			startServer(COMMAND_PORT, PUB_SUB_PORT);
		} catch (IOException e) {
			System.err.println("Couldn't start server");
			e.printStackTrace();
		}

	}

	public static void startServer(int commandPort, int pubSubPort) throws IOException {

		PubSubHandlerGroup pubSubHandlerGroup = new PubSubHandlerGroup();
		PubSubAcceptor pubSubAcceptor = new PubSubAcceptor(pubSubPort, pubSubHandlerGroup);
		CommandAcceptor commandAcceptor = new CommandAcceptor(commandPort, pubSubHandlerGroup);
		ExecutorService pool = Executors.newFixedThreadPool(3);
		pool.submit(pubSubHandlerGroup);
		pool.submit(pubSubAcceptor);
		pool.submit(commandAcceptor);
		
	}

}
