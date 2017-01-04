package de.uulm.in.vs.grn.c2;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadedServer {
	private static final int PORT = 7777;

	public static void main(String[] args) {

		while (true) { // restart if something crashes
			try (ServerSocket serverSocket = new ServerSocket(PORT)) {
				// use multiple threads to handle multiple parallel requests
				ExecutorService pool = Executors.newCachedThreadPool();

				try {

					while (true) {
						// start a thread for every request
						pool.execute(new SockagramRequestHandler(serverSocket.accept()));
					}

					// catch exceptions
				} catch (BindException bindError) {
					System.err.println("BindError! (Vielleicht läuft letzte Instanz des Programms noch)");
					System.exit(-1);
				} catch (IOException iOErrorCon) {
					// nothing
				}

				// exit the program on serious exceptions
			} catch (Exception e) {
				System.err.println("Es gab einen unbekannten Fehler");
				e.printStackTrace();
				System.exit(-1);
			}

		}
	}
}
