package de.uulm.in.vs.grn.chat.client.messages.responses;

public class GRNCPError extends Response {
	private final String reason;
	
	public GRNCPError(String date, String reason){
		super(date);
		this.reason = reason;
	}
	
	@Override
	public void handle() {
		// TODO Auto-generated method stub

	}

	public String getReason(){
		return reason;
	}
}
