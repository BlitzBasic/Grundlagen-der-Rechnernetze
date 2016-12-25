package de.uulm.in.vs.grn.sockagram.filter;

import java.io.IOException;

import de.uulm.in.vs.grn.sockagram.filter.presets.BlackWhiteFilter;
import de.uulm.in.vs.grn.sockagram.filter.presets.EightBitFilter;
import de.uulm.in.vs.grn.sockagram.filter.presets.NoFilter;
import de.uulm.in.vs.grn.sockagram.filter.presets.SepiaFilter;
import de.uulm.in.vs.grn.sockagram.filter.presets.SummerFilter;
import de.uulm.in.vs.grn.sockagram.filter.presets.SwagFilter;
import de.uulm.in.vs.grn.sockagram.filter.presets.YoloFilter;

/**
 * The main Filter instance. Provides a list of available filters as enum values. 
 * 
 * @author Benjamin Erb
 *
 */
public enum SockagramFilter implements Filter {
	
	NOFILTER(new NoFilter()), 	
	BLACKWHITE(new BlackWhiteFilter()),
	EIGHTBIT(new EightBitFilter()),
	YOLO(new YoloFilter()),
	SWAG(new SwagFilter()),
	SUMMER(new SummerFilter()),
	SEPIA(new SepiaFilter())
	;
	
	public static final int WIDTH = 500;

	private SockagramFilter(Filter filter) {
		this.filter = filter;
	}

	private final Filter filter;
	
	@Override
	public byte[] apply(byte[] image) throws IOException, IllegalArgumentException {
		if (filter != null) {
			return filter.apply(image);
		} else {
			return image;
		}
	}

	/**
	 * Returns the associated filter of a given byte code.
	 * @param b the byte code to resolve.
	 * @return The associated filter of {@link NoFilter}, if given code is unknown.
	 */
	public static SockagramFilter getFilterByCode(byte b) {
		if(b >= values().length || b < 0  || values()[b] == null){
			return NOFILTER;
		}
		else{
			return 	values()[b];
		}		
	}
}