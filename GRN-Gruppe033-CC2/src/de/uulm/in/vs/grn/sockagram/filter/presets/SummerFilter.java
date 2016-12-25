package de.uulm.in.vs.grn.sockagram.filter.presets;

import java.awt.Point;
import java.awt.image.BufferedImage;

import com.jhlabs.image.GlowFilter;
import com.jhlabs.image.GradientFilter;
import com.jhlabs.image.PixelUtils;

public class SummerFilter extends AbstractFilter {

	@Override
	BufferedImage apply(BufferedImage image) {

		GradientFilter gradientFilter =  new GradientFilter(new Point(0, 0), new Point(1000,1000), 0xffff69b4, 0xffff8c00, false, 1, 3);
		gradientFilter.setPaintMode(PixelUtils.COLOR);
		gradientFilter.filter(image,image);
		
		GlowFilter glowFilter = new GlowFilter();
		glowFilter.setAmount(.4f);
		glowFilter.filter(image,image);

		
		return image;
	}

}
