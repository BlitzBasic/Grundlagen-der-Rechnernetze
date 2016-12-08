package de.uulm.in.vs.grn.b2;

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
					InputStream in = socket.getInputStream();
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));) {

				writer.write("GET " + url.getPath() + " HTTP/1.1\r\nHost: "+url.getHost()+"\r\n\r\n");
				writer.flush();

				//extract status code
				while((char)in.read()!=' ');
				String statusCode = "";
				char temp;
				while((temp=(char)in.read())!=' ') statusCode+=temp;
				
				//read and write if everything is ok
				if (statusCode.equals("200")) {
					int filenameIndex = url.getFile().lastIndexOf('/')+1;
					String filename = url.getFile().substring(filenameIndex);
					
					try (FileOutputStream fileWriter = (new FileOutputStream(new File(filename)))){
						
						int readbyte = in.read();
						while (true){
							
							if(readbyte == -1) throw new IllegalStateException();	// if an error occurs the problem has to be in an illegal state, so the exception seems fitting
							if((char)readbyte == '\r'){
								if((char)in.read() == '\n' && (char)in.read() == '\r' && (char)in.read() == '\n') break;
							}
							readbyte = in.read();
						}
						
						byte[] buf = new byte[100000];
						int bytesRead = 0;
						while ((bytesRead=in.read(buf))!=-1)	fileWriter.write(buf, 0, bytesRead);
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
