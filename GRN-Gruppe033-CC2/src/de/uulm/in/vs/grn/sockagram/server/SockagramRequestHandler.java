package de.uulm.in.vs.grn.sockagram.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.Socket;

import de.uulm.in.vs.grn.sockagram.filter.SockagramFilter;

public class SockagramRequestHandler implements Runnable {
	private Socket handlerConnectionSocket;

	public SockagramRequestHandler(Socket conSoc) {
		this.handlerConnectionSocket = conSoc;
	}

	public void run() {

		// open streams and auto-close at the end
		try (Socket connectionSocket = this.handlerConnectionSocket;
				BufferedInputStream inputStream = new BufferedInputStream(connectionSocket.getInputStream());
				BufferedOutputStream outputStream = new BufferedOutputStream(connectionSocket.getOutputStream())) {

			int code = inputStream.read();
			int size = 0;
			for (int i = 0; i < 4; i++) {
				int buffer = inputStream.read();
				size += buffer << 8 * (3 - i);
			}
			System.out.println("Handler: "+size);
			
			byte[] data = new byte[size];
			
			int readBytes = inputStream.read(data,0,size);

			System.out.println("read: " + readBytes);
			byte[] result = "Something went wrong".getBytes();
			int status = -1;
			
			//TODO: Correct filters
			switch (code) {
			case -1:
				status=0;
				result = "No Data received".getBytes();
				break;
				
			case 0:
				status=0;
				result = SockagramFilter.NOFILTER.apply(data);
				break;

			case 1:
				status=0;
				result = SockagramFilter.NOFILTER.apply(data);
				break;

			case 2:
				status=0;
				result = SockagramFilter.NOFILTER.apply(data);
				break;

			case 3:
				status=0;
				result = SockagramFilter.NOFILTER.apply(data);
				break;

			case 4:
				status=0;
				result = SockagramFilter.NOFILTER.apply(data);
				break;

			case 5:
				status=0;
				result = SockagramFilter.NOFILTER.apply(data);
				break;

			case 6:
				status=0;
				result = SockagramFilter.NOFILTER.apply(data);
				break;

			case 7:
				status=0;
				result = SockagramFilter.NOFILTER.apply(data);
				break;

			case 8:
				status=0;
				result = SockagramFilter.NOFILTER.apply(data);
				break;

			default:
				status=0;
				result = SockagramFilter.NOFILTER.apply(data);
				break;
			}
			
			int fileLength=result.length;
			outputStream.write(new byte[] { (byte)status, (byte) (fileLength >>> 24), (byte) (fileLength >>> 16),
					(byte) (fileLength >>> 8), (byte) fileLength });
			
			
			System.out.println("fertig");
			outputStream.write(result);
			System.out.println("fertig2");
			outputStream.flush();
			
			
		} catch (BindException bindError) {
			System.err.println("BindError! (Vielleicht läuft altes Programm noch)");
			System.exit(1);
		} catch (IOException iOErrorCon) {
			iOErrorCon.printStackTrace();
		}
		
	}
}
