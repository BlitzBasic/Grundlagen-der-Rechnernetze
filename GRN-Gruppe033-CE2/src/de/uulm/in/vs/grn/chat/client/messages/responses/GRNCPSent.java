package de.uulm.in.vs.grn.chat.client.messages.responses;

public class GRNCPSent extends Response {
	public GRNCPSent(String date){
		super(date);
	}
	
	@Override
	public void display() {
		System.out.println(date + " | Your message was succesfully sent.");
		
	}

}
