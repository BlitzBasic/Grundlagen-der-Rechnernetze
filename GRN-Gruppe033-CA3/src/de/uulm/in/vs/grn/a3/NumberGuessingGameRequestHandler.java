package de.uulm.in.vs.grn.a3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.BindException;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

public class NumberGuessingGameRequestHandler implements Runnable {
	private Socket handlerConnectionSocket;

	public NumberGuessingGameRequestHandler(Socket conSoc) {
		this.handlerConnectionSocket = conSoc;
	}

	public void run() {

		boolean won = false;

		// open streams and auto-close at the end
		try (Socket connectionSocket = this.handlerConnectionSocket;
				InputStream inputStream = connectionSocket.getInputStream();
				OutputStream outputStream = connectionSocket.getOutputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {

			// generate random number
			int number = ThreadLocalRandom.current().nextInt(50);

			// greet the user
			writer.write("Willkommen zum Zahlenraten.\r\n");
			writer.flush();
			// six tries
			for (int i = 0; i < 6; i++) {
				writer.write(("Versuch " + (i + 1) + " von 6\r\nBitte gib eine Zahl ein..\r\n"));
				writer.flush();
				try {
					// read number
					String numberString = reader.readLine();

					// give response to input
					int n = Integer.parseInt(numberString);
					String outString = "";
					if (n > 50 || n < 0) {
						outString = "Die Zahl muss zwischen 0 und 50 sein!\r\n";
						i--;
					} else if (n == number) {
						outString = "Die Zahl " + n + " stimmt. Du hast gewonnen.\r\n";
						writer.write(outString);
						writer.flush();
						won = true;
						break;
					} else if (n > number) {
						outString = "Die Zahl " + n + " ist zu gross!\r\n";
					} else {
						outString = "Die Zahl " + n + " ist zu klein!\r\n";
					}
					writer.write(outString);
					if (!won && i >= 5) {
						writer.write("\r\n");
						writer.write("Du hast verloren!\r\n");
					}
					writer.flush();

					// catch non numeric inputs
				} catch (NumberFormatException numFormatExc) {
					System.out.println("Du solltest eine ganze Zahl zwischen 0 und 50 eingeben.\r\n");
					writer.write("Du solltest eine ganze Zahl zwischen 0 und 50 eingeben.\r\n");
					i--;

					// catch exceptions
				} catch (IOException iOError) {
					iOError.printStackTrace();
				}
			}
		} catch (BindException bindError) {
			System.err.println("Du nix nehmen zweimal gleiche Port! (Vielleicht läuft altes Programm noch)");
			System.exit(1);
		} catch (IOException iOErrorCon) {
			iOErrorCon.printStackTrace();
		}
	}
}
