package com.atanor.vserver.images;

import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import javax.imageio.ImageIO;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class TestImageComparator {

	private static final String IMAGES_TEST1_PNG = "images/test1.png";
	private static final String IMAGES_TEST0_PNG = "images/test0.png";

	private String fileImage0;
	private String fileImage1;

	@Before
	public void setup() {
		fileImage0 = getClass().getClassLoader().getResource(IMAGES_TEST0_PNG).getFile();
		fileImage1 = getClass().getClassLoader().getResource(IMAGES_TEST1_PNG).getFile();
	}

	@Test
	public void compare() {
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

	@Test
	public void compareBufferredImages() throws IOException {
		final File file0 = new File(fileImage0);
		final File file1 = new File(fileImage1);
		
		IplImage image0 = IplImage.createFrom(ImageIO.read(file0));
		IplImage image1 = IplImage.createFrom(ImageIO.read(file1));
		ImageComparator comparator = new ImageComparator(image0);

		int image0Size = image0.width() * image0.height();
		Double score = comparator.compare(image0) / image0Size;
		Assert.assertEquals(1.0d, round(score));

		int image1Size = image1.width() * image1.height();
		score = comparator.compare(image1) / image1Size;
		Assert.assertEquals(0.18d, round(score));
	}

	@Test
	public void compareBufferredAndNativeImages() throws IOException {
		IplImage image1 = IplImage.createFrom(ImageIO.read(new File(fileImage0)));
		IplImage image2 = cvLoadImage(fileImage0);
		
		ImageComparator comparator = new ImageComparator(image1);

		int image2Size = image2.width() * image2.height();
		Double score = comparator.compare(image2) / image2Size;
		Assert.assertEquals(1.0d, round(score));
	}
	
	private static Double round(final Double unrounded) {
		BigDecimal bd = new BigDecimal(unrounded);
		BigDecimal rounded = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return rounded.doubleValue();
	}
}
