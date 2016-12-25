package de.uulm.in.vs.grn.sockagram.filter.presets;

import java.awt.image.BufferedImage;

import com.jhlabs.image.GainFilter;
import com.jhlabs.image.SmartBlurFilter;


public class YoloFilter extends AbstractFilter{

	@Override
	BufferedImage apply(BufferedImage image) {
		new SmartBlurFilter().filter(image, image);
		GainFilter gainFilter =  new GainFilter();
		gainFilter.setBias(.6f);
		gainFilter.setGain(0.1f);
		gainFilter.filter(image, image);
		
		return image;
	}

}
