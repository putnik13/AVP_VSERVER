package com.atanor.vserver.images;

import static com.googlecode.javacv.cpp.opencv_core.cvGetSize;
import static com.googlecode.javacv.cpp.opencv_core.cvSplit;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_HIST_SPARSE;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCalcHist;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCreateHist;

import org.apache.commons.lang.Validate;

import com.googlecode.javacv.cpp.opencv_core.CvArr;
import com.googlecode.javacv.cpp.opencv_core.CvMat;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_imgproc.CvHistogram;

public class ColorHistogram {

	private static int numberOfBins = 256;
	private static float _minRange = 0.0f;
	private static float _maxRange = 255.0f;

	/**
	 * Reduce number of colors
	 * 
	 * @param image
	 *            input image that will have colors modified after this call.
	 * @param div
	 *            color reduction factor.
	 */
	public static void colorReduce(final IplImage image, final int div) {
		Validate.notNull(image, "image argument can not be null");		
		final CvMat mat = image.asCvMat();

		// Total number of elements, combining components from each channel
		int nbElements = mat.rows() * mat.cols() * mat.channels();
		for (int i = 0; i < nbElements; i++) {
			// Convert to integer
			int v = new Double(mat.get(i)).intValue();
			// Use integer division to reduce number of values
			int newV = v / div * div + div / 2;
			// Put back into the image
			mat.put(i, newV);
		}
	}

	/**
	 * Computes histogram of an image. Returned CvHistogram object has to be
	 * manually deallocated after use using `cvReleaseHist`.
	 * 
	 * @param image
	 *            input image
	 * @return OpenCV histogram object
	 */
	public static CvHistogram getHistogram(final IplImage image) {
		Validate.notNull(image, "image argument can not be null");
		Validate.isTrue(image.nChannels() == 3, "Expecting 3 channel (color) image");

		// Allocate histogram object
		int dims = 3;
		int[] sizes = { numberOfBins, numberOfBins, numberOfBins };
		int histType = CV_HIST_SPARSE;
		float[] minMax = { _minRange, _maxRange };
		float[][] ranges = { minMax, minMax, minMax };
		int uniform = 1;
		final CvHistogram hist = cvCreateHist(dims, sizes, histType, ranges, uniform);

		// Split bands, as required by `cvCalcHist`
		final IplImage channel0 = IplImage.create(cvGetSize(image), image.depth(), 1);
		final IplImage channel1 = IplImage.create(cvGetSize(image), image.depth(), 1);
		final IplImage channel2 = IplImage.create(cvGetSize(image), image.depth(), 1);
		cvSplit(image, channel0, channel1, channel2, null);

		// Compute histogram
		int accumulate = 0;
		final CvArr mask = null;
		cvCalcHist(new IplImage[] { channel0, channel1, channel2 }, hist, accumulate, mask);

		return hist;
	}

}
