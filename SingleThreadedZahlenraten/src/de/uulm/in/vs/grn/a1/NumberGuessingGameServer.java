package de.uulm.in.vs.grn.a1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

public class NumberGuessingGameServer {

	private static final int PORT = 5555;

	public static void main(String[] args) {

		while (true) {

			boolean won = false;

			// open connection
			try (ServerSocket serverSocket = new ServerSocket(PORT);
					Socket connectionSocket = serverSocket.accept();
					InputStream inputStream = connectionSocket.getInputStream();
					OutputStream outputStream = connectionSocket.getOutputStream()) {

				int number = ThreadLocalRandom.current().nextInt(50);

				outputStream.write("Willkommen zum Zahlenraten.\r\n".getBytes());
				outputStream.flush();

				// read numbers
				for (int i = 0; i < 6; i++) {
					outputStream.write(("Versuch " + (i + 1) + " von 6\r\nBitte gib eine Zahl ein..\r\n").getBytes());
					outputStream.flush();

					try {
						int readByte;
						String numberString = "";
						while ((readByte = inputStream.read()) != -1) {
							if ((char) readByte == '\r') {
								inputStream.read(); // throw away \n
								break;
							} else if ((char) readByte == '\b') {
								if (numberString.length() != 0)
									numberString = numberString.substring(0, numberString.length() - 1);
							} else {
								numberString += (char) readByte;
							}
						}

						int n = Integer.parseInt(new String(numberString));
						String outString = "";
						if (n > 50 || n < 0) {
							outString = "Die Zahl muss zwischen 0 und 50 sein!\r\n";
							i--;
						} else if (n == number) {
							outString = "Die Zahl " + n + " stimmt. Du hast gewonnen.\r\n";
							outputStream.write(outString.getBytes());
							won = true;
							break;
						} else if (n > number) {
							outString = "Die Zahl " + n + " ist zu gross!\r\n";
						} else {
							outString = "Die Zahl " + n + " ist zu klein!\r\n";
						}
						outputStream.write(outString.getBytes());
						if (!won && i >= 5) {
							outputStream.write("\r\n".getBytes());
							outputStream.write("Du hast verloren!\r\n".getBytes());
						}
						outputStream.flush();
					} catch (NumberFormatException numFormatExc) {
						outputStream.write("Du solltest eine ganze Zahl zwischen 0 und 50 eingeben.\r\n".getBytes());
						i--;
					} catch (IOException iOError) {
						iOError.printStackTrace();
					}
				}
			} catch (BindException bindError) {
				System.err.println("BindError! (Vielleicht läuft letzte Instanz des Programms noch)");
				System.exit(1);
			} catch (IOException iOErrorCon) {
				iOErrorCon.printStackTrace();
			}
		}
	}
}
