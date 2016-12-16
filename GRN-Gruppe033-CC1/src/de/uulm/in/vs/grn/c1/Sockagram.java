package de.uulm.in.vs.grn.c1;

import java.io.*;
import java.net.Socket;

public class Sockagram {

	public static void main(String[] args) {

		if (args.length < 2) {
			System.err.println("Please insert a filter number and the name of an image!");
			System.exit(-1);

		}

		int filterTypeInt = Integer.parseInt(args[0]);
		byte filterTypeByte = (byte) filterTypeInt;

		String filename = args[1];

		File oldFile = new File(filename);

		File newFile = new File("edited_" + filename);

		// open connection
		try (Socket socket = new Socket("134.60.77.152", 7777);

				BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());

				BufferedInputStream in = new BufferedInputStream(socket.getInputStream());

				BufferedInputStream fileIn = new BufferedInputStream(new FileInputStream(oldFile))){

			int fileLength = (int) oldFile.length();

			out.write(new byte[] { filterTypeByte, (byte) (fileLength >>> 24), (byte) (fileLength >>> 16),
					(byte) (fileLength >>> 8), (byte) fileLength });

			byte[] buf = new byte[1000000];
			int bytesRead = 0;
			while ((bytesRead = fileIn.read(buf)) != -1) {
				out.write(buf, 0, bytesRead);
			}
			out.flush();

			
			int status = in.read();
			int newLength = 0;
			for (int i = 0; i < 4; i++) {
				int buffer = in.read();
				newLength += buffer << 8 * (3 - i);
			}

			if (status != 0) {
				String message = "";
				for (int l = 0; l < newLength; l++) {
					message += (char) in.read();
				}
				System.out.println(message);
			} else {
				try(BufferedOutputStream fileOut = new BufferedOutputStream(new FileOutputStream(newFile))){
					
				
				bytesRead = 0;
				while ((bytesRead = in.read(buf)) != -1) {

					fileOut.write(buf, 0, bytesRead);
				}
				fileOut.flush();
				}
			}

		} catch (IOException e1) {
			System.err.println("Couldn't write file");

		}
	}

}
