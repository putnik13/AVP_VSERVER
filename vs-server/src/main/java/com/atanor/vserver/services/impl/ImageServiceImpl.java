package com.atanor.vserver.services.impl;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;

import org.apache.commons.lang.Validate;

import com.atanor.vserver.images.ImageComparator;
import com.atanor.vserver.services.ImageService;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class ImageServiceImpl implements ImageService {

	private static final double IMAGES_SIMILARITY_THESHOLD_SCORE = 0.9d;

	@Override
	public boolean isSimilar(final BufferedImage img1, final BufferedImage img2) {
		Validate.notNull(img1, "img1 argument can not be null");
		Validate.notNull(img2, "img2 argument can not be null");

		final IplImage image0 = IplImage.createFrom(img1);
		final IplImage image1 = IplImage.createFrom(img2);
		final ImageComparator comparator = new ImageComparator(image0);

		final int normFactor = image0.width() * image0.height();
		final Double score = comparator.compare(image1) / normFactor;

		return round(score) >= IMAGES_SIMILARITY_THESHOLD_SCORE;
	}

	private static Double round(final Double unrounded) {
		final BigDecimal bd = new BigDecimal(unrounded);
		final BigDecimal rounded = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return rounded.doubleValue();
	}
	
}
