package de.uulm.in.vs.grn.c2;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadedServer {
	private static final int PORT = 7777;
 
	public static void main(String[] args) {

		
		try (ServerSocket serverSocket = new ServerSocket(PORT)) {

			ExecutorService pool = Executors.newFixedThreadPool(1);

			try {
				
				while (true) {
					pool.execute(new SockagramRequestHandler(serverSocket.accept()));	//wait for a connecting host, start new thread for each
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
