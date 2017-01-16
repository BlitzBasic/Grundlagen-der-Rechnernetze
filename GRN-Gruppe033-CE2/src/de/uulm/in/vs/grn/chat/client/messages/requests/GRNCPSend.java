package de.uulm.in.vs.grn.chat.client.messages.requests;

import java.io.IOException;
import java.io.Writer;

import de.uulm.in.vs.grn.chat.client.GRNCP;

public class GRNCPSend extends Request {
	
	private String sendMessage = "";
	
	public GRNCPSend() {
		
	}
	
	public GRNCPSend(String text){
		sendMessage = "SEND " + GRNCP.PROTOCOL_VERSION + "\r\nText: " + text + "\r\n";
	}
	
	public void setText(String text){
		sendMessage = "SEND " + GRNCP.PROTOCOL_VERSION + "\r\nText: " + text + "\r\n";
	}

	@Override
	public void send(Writer writer) throws IOException {
		if(sendMessage != ""){
			writer.write(sendMessage);
		}
	}
}
