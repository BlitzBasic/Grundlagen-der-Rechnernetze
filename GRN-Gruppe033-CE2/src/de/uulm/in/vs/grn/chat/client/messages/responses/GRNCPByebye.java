package de.uulm.in.vs.grn.chat.client.messages.responses;

public class GRNCPByebye extends Response {
	public GRNCPByebye(String date){
		super(date);
	}
	
	@Override
	public void handle() {
		// TODO Auto-generated method stub

	}

	@Override
	public void display() {
		System.out.println(date + " | You are logged out.");
		
	}

}
