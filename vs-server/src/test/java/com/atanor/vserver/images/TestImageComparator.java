package com.atanor.vserver.images;

import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;

import java.math.BigDecimal;

import junit.framework.Assert;

import org.junit.Test;

import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class TestImageComparator {

	@Test
	public void compare() {
		String fileImage0 = getClass().getClassLoader().getResource("images/test0.png").getFile();
		String fileImage1 = getClass().getClassLoader().getResource("images/test1.png").getFile();

		IplImage image0 = cvLoadImage(fileImage0);
		IplImage image1 = cvLoadImage(fileImage1);
		ImageComparator comparator = new ImageComparator(image0);

		int image0Size = image0.width() * image0.height();
		Double score = comparator.compare(image0) / image0Size;
		Assert.assertEquals(1.0d, round(score));

		int image1Size = image1.width() * image1.height();
		score = comparator.compare(image1) / image1Size;
		Assert.assertEquals(0.18d, round(score));
	}

	private static Double round(final Double unrounded) {
		BigDecimal bd = new BigDecimal(unrounded);
		BigDecimal rounded = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return rounded.doubleValue();
	}
}
