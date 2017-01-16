package de.uulm.in.vs.grn.chat.client.messages.requests;

import java.io.IOException;
import java.io.Writer;

import de.uulm.in.vs.grn.chat.client.GRNCP;

public class GRNCPLogin extends Request {
	
	private String loginMessage = "";
	
	public GRNCPLogin(){
		
	}
	
	public GRNCPLogin(String username) {
		loginMessage = "LOGIN " + GRNCP.PROTOCOL_VERSION + "\r\nUsername: " + username + "\r\n";
	}
	
	public void setUsername(String username){
		loginMessage = "LOGIN " + GRNCP.PROTOCOL_VERSION + "\r\nUsername: " + username + "\r\n";
	}
	
	@Override
	public void send(Writer writer) throws IOException {
		if(loginMessage != ""){
			writer.write(loginMessage);
		}
	}
}
