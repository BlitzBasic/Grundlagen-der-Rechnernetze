package de.uulm.in.vs.grn.a2;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class NumberGuessingGameThreadedServer {
	private static final int PORT = 5555;

	public static void main(String[] args) {

		try (ServerSocket serverSocket = new ServerSocket(PORT)){
			while (true) {
				try {
					Socket connectionSocket = serverSocket.accept();
					NumberGuessingGameRequestHandler game = new NumberGuessingGameRequestHandler(connectionSocket);
					new Thread(game).start();
					
				} catch (BindException bindError) {
					System.err.println("Du nix nehmen zweimal gleiche Port! (Vielleicht läuft altes Programm noch)");
					System.exit(1);
				} catch (IOException iOErrorCon) {
					iOErrorCon.printStackTrace();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
