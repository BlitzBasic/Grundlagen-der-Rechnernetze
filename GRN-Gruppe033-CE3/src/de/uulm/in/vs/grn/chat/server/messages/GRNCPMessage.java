package de.uulm.in.vs.grn.chat.server.messages;

import java.io.IOException;
import java.io.Writer;

/**
 * interface to implement methods to be used with all types of messages
 * @author Marius
 *
 */
public interface GRNCPMessage {
	/**
	 * writes this request into the given writer
	 * @param writer target writer
	 * @throws IOException if request can not be written
	 */
	public abstract void send(Writer writer) throws IOException;
	//currently no method
}
