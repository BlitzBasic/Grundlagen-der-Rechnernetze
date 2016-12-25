package de.uulm.in.vs.grn.sockagram.filter.presets;

import java.awt.image.BufferedImage;

import com.jhlabs.image.RaysFilter;
import com.jhlabs.image.UnsharpFilter;

public class SwagFilter extends AbstractFilter {

	@Override
	BufferedImage apply(BufferedImage image) {
		new UnsharpFilter().filter(image,image);
		new RaysFilter().filter(image,image);
		return image;
	}

}
