package de.uulm.in.vs.grn.a2;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NumberGuessingGameThreadedServer {
	private static final int NUMBER_OF_THREADS = 4;
	private static final int PORT = 5555;

	public static void main(String[] args) {

		
		try (ServerSocket serverSocket = new ServerSocket(PORT)) {

			//fixed thread pool for a maximum of NUMBER_OF_THREADS parallel games
			ExecutorService pool = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

			try {
				//always start a new game when the old one ends
				while (true) {
					pool.execute(new Thread(new NumberGuessingGameRequestHandler(serverSocket.accept())));	//wait for a connecting host, start new thread for each game in a thread pool
				}
				
			//catch exceptions
			} catch (BindException bindError) {
				System.err.println("BindError! (Vielleicht läuft letzte Instanz des Programms noch)");
				System.exit(1);
			} catch (IOException iOErrorCon) {
				iOErrorCon.printStackTrace();
			}

		//exit the program on serious exceptions
		} catch (Exception e) {
			System.err.println("Es gab einen unbekannten Fehler");
			e.printStackTrace();
			System.exit(-1);
		}

	}
}
