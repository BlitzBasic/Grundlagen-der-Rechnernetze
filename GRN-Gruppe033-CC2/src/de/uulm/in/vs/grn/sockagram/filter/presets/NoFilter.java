package de.uulm.in.vs.grn.sockagram.filter.presets;

import java.awt.image.BufferedImage;

public class NoFilter extends AbstractFilter {
	@Override
	BufferedImage apply(BufferedImage image) {
		return image;
	}
}
