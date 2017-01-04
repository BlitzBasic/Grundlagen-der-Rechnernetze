package de.uulm.in.vs.grn.c1;

import java.io.*;
//import java.net.InetAddress; //for own server
import java.net.Socket;

public class Sockagram {

	public static void main(String[] args) {

		// read parameters initial parameters
		if (args.length < 2) {
			System.err.println("Please insert a filter number and the name of an image!");
			System.exit(-1);
		}
		int filterTypeInt = Integer.parseInt(args[0]);
		byte filterTypeByte = (byte) filterTypeInt;
		String filename = args[1];
		File oldFile = new File(filename);
		File newFile = new File("edited_" + filename+ ".png");
		
		// open connection
		try (
//				Socket socket = new Socket(InetAddress.getLocalHost(), 7777); //own server
				Socket socket = new Socket("134.60.77.152", 7777);//uni server
				BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
				BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
				BufferedInputStream fileIn = new BufferedInputStream(new FileInputStream(oldFile))) {

			int fileLength = (int) oldFile.length();

			// write file length into stream
			out.write(new byte[] { filterTypeByte, (byte) (fileLength >>> 24), (byte) (fileLength >>> 16),
					(byte) (fileLength >>> 8), (byte) fileLength });
			


	        byte[] data = new byte[(int) oldFile.length()];
	        int bytesRead = fileIn.read(data);
	        out.write(data);
			out.flush();

			int status = in.read();
			int newLength = 0;
			for (int i = 0; i < 4; i++) {
				int buffer = in.read();
				newLength += buffer << 8 * (3 - i);
			}
			System.out.println();

			if (status != 0) {
				// read error message
				String message = "";
				for (int l = 0; l < newLength; l++) {
					message += (char) in.read();
				}
				System.out.println(message);
			} else {
				// read picture
				try (BufferedOutputStream fileOut = new BufferedOutputStream(new FileOutputStream(newFile))) {
					data=new byte[newLength];
					while(newLength>0){
						bytesRead = in.read(data,data.length - newLength, newLength);
						if(bytesRead==-1){
							continue;
						}
						newLength -= bytesRead;
					}
					
					fileOut.write(data);
					fileOut.flush();
				}
			}

		} catch (IOException e1) {
			e1.printStackTrace();

		}
	}

}
