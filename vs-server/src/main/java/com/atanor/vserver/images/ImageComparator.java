package com.atanor.vserver.images;

import static com.googlecode.javacv.cpp.opencv_imgproc.CV_COMP_INTERSECT;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCompareHist;

import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_imgproc.CvHistogram;

public class ImageComparator {

	/**
	 * Color reduction factor. The comparison will be made on images with the
	 * color space reduced by this factor in each dimension
	 */
	private final int colorReductionFactor = 32;
	private final CvHistogram baseHist;

	public ImageComparator(final IplImage image) {
		ColorHistogram.colorReduce(image, colorReductionFactor);
		baseHist = ColorHistogram.getHistogram(image);
	}

	/**
	 * Compare the reference image with the given input image and return
	 * similarity score.
	 */
	public Double compare(final IplImage image) {
		ColorHistogram.colorReduce(image, colorReductionFactor);
		final CvHistogram inputHist = ColorHistogram.getHistogram(image);
		return cvCompareHist(baseHist, inputHist, CV_COMP_INTERSECT);
	}
}
