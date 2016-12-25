package de.uulm.in.vs.grn.sockagram.filter.presets;

import java.awt.image.BufferedImage;

import com.jhlabs.image.*;

public class BlackWhiteFilter extends AbstractFilter{

	@Override
	BufferedImage apply(BufferedImage image) {
		new GrayscaleFilter().filter(image, image);
		
		ContrastFilter contrastFilter = new ContrastFilter();
		contrastFilter.setContrast(1.9f);
		contrastFilter.setBrightness(1.1f);
		contrastFilter.filter(image, image);

		return image;
	}

}
