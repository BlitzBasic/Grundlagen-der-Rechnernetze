package de.uulm.in.vs.grn.chat.client.messages.requests;

import java.io.IOException;
import java.io.Writer;

import de.uulm.in.vs.grn.chat.client.GRNCP;

public class GRNCPPing extends Request {

	private final String pingMessage = "PING " + GRNCP.PROTOCOL_VERSION + "\r\n";

	public GRNCPPing() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void send(Writer writer) throws IOException {
		writer.write(pingMessage);

	}

}
