package de.uulm.in.vs.grn.sockagram.filter.presets;

import java.awt.Point;
import java.awt.image.BufferedImage;

import com.jhlabs.image.ContrastFilter;
import com.jhlabs.image.ExposureFilter;
import com.jhlabs.image.GradientFilter;
import com.jhlabs.image.GrayscaleFilter;
import com.jhlabs.image.NoiseFilter;
import com.jhlabs.image.PixelUtils;
import com.jhlabs.image.SmartBlurFilter;

public class SepiaFilter extends AbstractFilter {

	@Override
	BufferedImage apply(BufferedImage image) {

		new GrayscaleFilter().filter(image, image);

		new NoiseFilter().filter(image, image);

		GradientFilter gradientFilter = new GradientFilter(new Point(250, 250),
				new Point(1000, 1000), 0xffEDDA74, 0xffFFF380, false, 2, 3);
		gradientFilter.setPaintMode(PixelUtils.COLOR);
		gradientFilter.filter(image, image);

		ContrastFilter contrastFilter = new ContrastFilter();
		contrastFilter.setContrast(1.4f);
		contrastFilter.setBrightness(1.1f);
		contrastFilter.filter(image, image);

		new SmartBlurFilter().filter(image, image);

		ExposureFilter exposureFilter = new ExposureFilter();
		exposureFilter.setExposure(.8f);

		exposureFilter.filter(image, image);

		return image;
	}

}
