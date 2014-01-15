package com.atanor.vserver.images;

import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvShowImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvWaitKey;

import java.net.URL;

import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class TestImage {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		URL url = TestImage.class.getClassLoader().getResource("images/test0.png");
		open(url.getFile());
	}

	public static void open(String filename) {
		IplImage image = cvLoadImage(filename);
		if (image != null) {
			cvShowImage(filename, image);
			cvWaitKey();
		}
	}

}
