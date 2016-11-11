package de.uulm.in.vs.grn.a2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.BindException;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

public class NumberGuessingGameRequestHandler implements Runnable {
	private Socket connectionSocket;

	public NumberGuessingGameRequestHandler(Socket conSoc) {
		this.connectionSocket = conSoc;
	}

	public void run(){

		boolean won = false;
		try (	Socket connectionSocket=this.connectionSocket;
				InputStream inputStream = connectionSocket.getInputStream();
				OutputStream outputStream = connectionSocket.getOutputStream()) {

			int number = ThreadLocalRandom.current().nextInt(50);
			
			outputStream.write("Willkommen zum Zahlenraten.\r\n".getBytes());
			outputStream.flush();

			for (int i = 0; i < 6; i++) {
				outputStream.write(("Versuch " + (i+1) +" von 6\r\nBitte gib eine Zahl ein..\r\n").getBytes());
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

					//outputStream.write(numberString.getBytes());
					int n = Integer.parseInt(new String(numberString));
//					outputStream.write("\r\n".getBytes());
					String outString = "";
					if (n == number) {
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
					System.out.println("Du solltest eine ganze Zahl zwischen 0 und 50 eingeben.\r\n");
					outputStream.write("Du solltest eine ganze Zahl zwischen 0 und 50 eingeben.\r\n".getBytes());
					i--;
				} catch (IOException iOError) {
					iOError.printStackTrace();
				}
			}
		}catch (BindException bindError) {
			System.err.println("Du nix nehmen zweimal gleiche Port! (Vielleicht läuft altes Programm noch)");
			System.exit(1);
		}
		catch (IOException iOErrorCon) {
			iOErrorCon.printStackTrace();
		}
	}
}
