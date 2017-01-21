package de.uulm.in.vs.grn.chat.client.messages.requests;

import java.io.IOException;
import java.io.Writer;

import de.uulm.in.vs.grn.chat.client.messages.GRNCPMessage;

/**
 * Represents any kind of request. Offers a send method to send the request
 * @author Marius
 *
 */
public abstract class Request implements GRNCPMessage {

	public abstract void send(Writer writer) throws IOException;

}
