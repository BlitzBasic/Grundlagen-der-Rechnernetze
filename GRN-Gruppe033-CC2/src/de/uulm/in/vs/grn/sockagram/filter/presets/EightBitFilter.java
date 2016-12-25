package de.uulm.in.vs.grn.sockagram.filter.presets;

import java.awt.image.BufferedImage;

import com.jhlabs.image.BlockFilter;
import com.jhlabs.image.QuantizeFilter;

public class EightBitFilter extends AbstractFilter {

	@Override
	BufferedImage apply(BufferedImage image) {
		QuantizeFilter quantizeFilter =new QuantizeFilter();
		quantizeFilter.setNumColors(64);
		quantizeFilter.filter(image, image);
		new BlockFilter(6).filter(image, image);
		return image;
	}

}
