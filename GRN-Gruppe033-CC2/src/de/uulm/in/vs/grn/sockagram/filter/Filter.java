package de.uulm.in.vs.grn.sockagram.filter;

import java.io.IOException;


/**
 * Generic filter interface.
 * 
 * @author Benjamin Erb
 *
 */
public interface Filter {
	
	/**
	 * This method applies the current filter on an image and returns the resulting image.
	 * @param image The source image as a byte array
	 * @return The resulting image as a byte array
	 * @throws IOException 
	 * @throws IllegalArgumentException In case of illegal formats or image files.
	 */
	public byte[] apply(byte[] image) throws IOException, IllegalArgumentException;
}
