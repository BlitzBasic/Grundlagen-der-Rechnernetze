package de.uulm.in.vs.grn.b1;

import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;

public class URLFetcher {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		if (args[0] == null) {
			System.err.println("Please insert an URL!");
			System.exit(-1);
		}
		
		//TODO: Complete splitting input into URL, extention, ...
		String link = args[0];
		if(link.startsWith("http://")) link=link.substring(6);
		try{
		int extensionIndex;
		if((extensionIndex = link.lastIndexOf('.')) == -1) throw new MalformedURLException();
		String filename = link.substring(link.lastIndexOf('/'), extensionIndex);
		String extention = link.substring(extensionIndex);
		System.out.println(filename + " . " + extention);
		try (Socket socket = new Socket(args[0], 80);
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));) {

			writer.write("GET "+" HTTP/1.1\r\nHost: www.boersen-und-maerkte.de\r\n\r\n");
			writer.flush();

			String statusCode = reader.readLine().split(" ", 4)[1];
			System.out.println(statusCode);
			if (statusCode.equals("200")) {
				try (BufferedWriter fileWriter = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(new File(filename + "." + extention))))) {
					while (!reader.readLine().equals("")) ; // throw away header
					while (reader.ready()) {

						fileWriter.write(reader.readLine()+"\r\n");
					}

					fileWriter.flush();
				} catch (Exception e) {
					System.err.println("Scheiße");
				}

			}
		}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
