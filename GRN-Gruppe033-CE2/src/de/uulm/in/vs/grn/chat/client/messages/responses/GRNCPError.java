package de.uulm.in.vs.grn.chat.client.messages.responses;

/**
 * represents an error response
 * @author Marius
 *
 */
public class GRNCPError extends Response {
	private final String reason;
	
	public GRNCPError(String date, String reason){
		super(date);
		this.reason = reason;
	}
	

	public String getReason(){
		return reason;
	}

	@Override
	public void display() {
		System.out.println(date + " | " + reason);
		
	}
}
