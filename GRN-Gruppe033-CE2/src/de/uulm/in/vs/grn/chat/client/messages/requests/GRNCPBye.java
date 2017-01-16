package de.uulm.in.vs.grn.chat.client.messages.requests;

import java.io.IOException;
import java.io.Writer;

import de.uulm.in.vs.grn.chat.client.GRNCP;

public class GRNCPBye extends Request {
	
	private final String byeMessage = "BYE " + GRNCP.PROTOCOL_VERSION + "\r\n";
	
	public GRNCPBye() {
		
	}

	@Override
	public void send(Writer writer) throws IOException {
		writer.write(byeMessage);
	}
}
