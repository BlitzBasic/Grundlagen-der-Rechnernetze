package de.uulm.in.vs.grn.sockagram.filter.presets;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import de.uulm.in.vs.grn.sockagram.filter.Filter;
import de.uulm.in.vs.grn.sockagram.filter.SockagramFilter;

abstract class AbstractFilter implements Filter {

	abstract BufferedImage apply(BufferedImage image);

	@Override
	public byte[] apply(byte[] image) throws IOException, IllegalArgumentException {

		InputStream in = new ByteArrayInputStream(image);
		final BufferedImage croppedImage = cropScaleImage(ImageIO.read(in), SockagramFilter.WIDTH);

		BufferedImage result = croppedImage;

		result = apply(croppedImage);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(result, "png", baos);
		baos.flush();
		return baos.toByteArray();
	}

	private static BufferedImage cropScaleImage(BufferedImage image, int width) {
		int h = image.getHeight();
		int w = image.getWidth();

		double scaleFactor;

		if (w > h) {
			scaleFactor = (double) width / h;
		} else {
			scaleFactor = (double) width / w;
		}
		int resultH =  (int )(Math.ceil( (double)(scaleFactor * h)));
		int resultW =  (int )(Math.ceil( (double)(scaleFactor * w)));
		
		BufferedImage result = new BufferedImage(resultW, resultH, BufferedImage.TYPE_INT_ARGB);
		AffineTransform at = new AffineTransform();
		at.scale(scaleFactor, scaleFactor);
		AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		result = scaleOp.filter(image, result);
		
		int cropW = (int )(Math.ceil( (double)(resultW - SockagramFilter.WIDTH) / 2d));
		int cropH = (int )(Math.ceil( (double)(resultH - SockagramFilter.WIDTH) / 2d));
	
		return result.getSubimage(cropW, cropH, width, width);
	}
}