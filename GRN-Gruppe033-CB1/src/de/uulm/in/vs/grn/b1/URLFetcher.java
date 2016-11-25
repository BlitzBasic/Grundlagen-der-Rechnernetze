package de.uulm.in.vs.grn.b1;

import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

public class URLFetcher {

	public static void main(String[] args) {

		
		if (args.length == 0) {
			System.err.println("Please insert an URL!");
			System.exit(-1);
		}

		
		try {
			
			URL url = new URL(args[0]);

			//open connection
			try (Socket socket = new Socket(url.getHost(), 80);
					BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));) {

				writer.write("GET " + url.getPath() + " HTTP/1.1\r\nHost: "+url.getHost()+"\r\n\r\n");
				writer.flush();

				//extract status code
				String statusCode = reader.readLine().split(" ", 4)[1];
				
				//read and write if everything is ok
				if (statusCode.equals("200")) {
					try (BufferedWriter fileWriter = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream(new File(url.getFile().substring(1)))))) {
						while (!reader.readLine().equals("")); // throw away header
						while (reader.ready())	fileWriter.write(reader.readLine() + "\r\n");
						fileWriter.flush();
					}

				}else{
					System.err.println("Could not complete request: "+statusCode);
					throw new MalformedURLException(statusCode);
				}
			}
		} catch (MalformedURLException e) {
			System.err.println("You entered an invalid URL");
		} catch (IOException e1) {
			System.err.println("Couldn't write file");
		}

	}

}
