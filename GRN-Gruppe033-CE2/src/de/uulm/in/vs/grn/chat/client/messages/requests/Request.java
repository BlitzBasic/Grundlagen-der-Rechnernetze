package de.uulm.in.vs.grn.chat.client.messages.requests;

import java.io.Writer;

import de.uulm.in.vs.grn.chat.client.messages.GRNCPMessage;

public abstract class Request implements GRNCPMessage {

	public abstract void send(Writer writer);

}
