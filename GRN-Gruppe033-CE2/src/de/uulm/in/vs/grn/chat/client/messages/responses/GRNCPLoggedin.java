package de.uulm.in.vs.grn.chat.client.messages.responses;

public class GRNCPLoggedin extends Response {
	public GRNCPLoggedin(String date){
		super(date);
	}
	
	@Override
	public void handle() {
		System.out.println("logged in");
	}

}
