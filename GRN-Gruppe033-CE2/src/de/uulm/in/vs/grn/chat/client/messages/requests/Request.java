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

	/**
	 * writes this request into the given writer
	 * @param writer target writer
	 * @throws IOException if request can not be written
	 */
	public abstract void send(Writer writer) throws IOException;

}
