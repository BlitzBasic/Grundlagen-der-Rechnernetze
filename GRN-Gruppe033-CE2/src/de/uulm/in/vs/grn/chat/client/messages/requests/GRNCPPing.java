package de.uulm.in.vs.grn.chat.client.messages.requests;

import java.io.IOException;
import java.io.Writer;

import de.uulm.in.vs.grn.chat.client.GRNCP;

public class GRNCPPing extends Request {

	private final String pingMessage = "PING " + GRNCP.PROTOCOL_VERSION + "\r\n\r\n";

	public GRNCPPing() {
		super();
	}

	@Override
	public void send(Writer writer) throws IOException {
		writer.write(pingMessage);
		writer.flush();
	}

}
