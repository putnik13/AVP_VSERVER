package com.atanor.vserver.facades.player;

import java.awt.image.BufferedImage;

import net.coobird.thumbnailator.Thumbnails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atanor.vserver.facades.ImageGrabber;
import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.FFmpegFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class FFmpegImageGrabber implements ImageGrabber {

	private static final Logger LOG = LoggerFactory.getLogger(FFmpegImageGrabber.class);

	FFmpegFrameGrabber grabber;
	CanvasFrame canvas;
	
	@Override
	public void start(final String mediaSource) {
		if (isPlaying()) {
			return;
		}
		canvas = new CanvasFrame("Client");
		canvas.setCanvasSize(640, 480);
		try {
			grabber = new FFmpegFrameGrabber(mediaSource);
			grabber.start();
			LOG.info(">>>>>> FFmpeg image grabber started grabbing");
		} catch (Exception e) {
			LOG.error("Failure to start image grabber..", e);
		}
	}

	@Override
	public void stop() {
		try {
			if (grabber != null) {
				grabber.stop();
				grabber.release();
				LOG.info("<<<<<< FFmpeg image grabber stopped grabbing");
			}
		} catch (Exception e) {
			LOG.error("Failure to stop image grabber..", e);
		} finally {
			grabber = null;
		}
	}

	@Override
	public boolean isPlaying() {
		return grabber != null;
	}

	@Override
	public BufferedImage grab() {
		return isPlaying() ? grabImage(0, 0) : null;
	}

	@Override
	public BufferedImage grab(final int width, final int height) {
		return isPlaying() ? grabImage(width, height) : null;
	}

	public BufferedImage grabImage(final int width, final int height) {
		try {
			final IplImage img = grabber.grab();
			canvas.showImage(img);
			final boolean isResize = width != 0 || height != 0;
			return isResize ? Thumbnails.of(img.getBufferedImage()).size(width, height).asBufferedImage() : img
					.getBufferedImage();
		} catch (Exception e) {
			LOG.error("Image grabbing error..", e);
		}
		return null;
	}
}
