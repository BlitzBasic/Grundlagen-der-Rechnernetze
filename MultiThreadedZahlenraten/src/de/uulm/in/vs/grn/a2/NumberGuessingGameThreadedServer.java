package de.uulm.in.vs.grn.a2;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NumberGuessingGameThreadedServer {
	private static final int NUMBER_OF_THREADS = 4;
	private static final int PORT = 5555;

	public static void main(String[] args) {

		
		try (ServerSocket serverSocket = new ServerSocket(PORT)) {

			ExecutorService pool = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

			try {
				while (true) {
					pool.execute(new Thread(new NumberGuessingGameRequestHandler(serverSocket.accept())));
				}
			} catch (BindException bindError) {
				System.err.println("BindError! (Vielleicht läuft letzte Instanz des Programms noch)");
				System.exit(1);
			} catch (IOException iOErrorCon) {
				iOErrorCon.printStackTrace();
			}

		} catch (Exception e) {
			System.err.println("Es gab einen Fehler");
			System.exit(-1);
		}

	}
}
