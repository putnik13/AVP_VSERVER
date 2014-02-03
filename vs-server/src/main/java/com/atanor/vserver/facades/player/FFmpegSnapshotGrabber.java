package com.atanor.vserver.facades.player;

import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atanor.vserver.facades.SnapshotGrabber;
import com.googlecode.javacv.FFmpegFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class FFmpegSnapshotGrabber implements SnapshotGrabber {

	private static final Logger LOG = LoggerFactory.getLogger(FFmpegSnapshotGrabber.class);

	private FFmpegFrameGrabber grabber;
	private ExecutorService executor;
	
	private final int width;
	private final int height;
	
	boolean stopped;
	
	public FFmpegSnapshotGrabber() {
		this(0, 0);
	}

	public FFmpegSnapshotGrabber(final int width, final int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void start(final String mediaSource) {
		if (isPlaying()) {
			return;
		}

		try {
			stopped = false;
			grabber = new FFmpegFrameGrabber("rtp://127.0.0.1:5005");
			if (isResize()) {
				grabber.setImageWidth(width);
				grabber.setImageHeight(height);
			}
			
			LOG.info(">>>>>> FFmpeg image grabber started grabbing");
			
			executor = Executors.newSingleThreadExecutor();
			executor.execute(new WorkerThread());
			executor.shutdown();
		} catch (Exception e) {
			LOG.error("Failure to start image grabber..", e);
		}
	}

	@Override
	public void stop() {
		try {
			if (grabber != null) {
				stopped = true;
				// to close grabbing process silently
				TimeUnit.SECONDS.sleep(1);
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
		return isPlaying() ? grabImage() : null;
	}

	public BufferedImage grabImage() {
		try {
			final IplImage img = grabber.grab();
			return img != null ? img.getBufferedImage() : null;
		} catch (Exception e) {
			LOG.error("Image grabbing error..", e);
		}
		return null;
	}

	private boolean isResize() {
		return (width != 0) || (height != 0);
	}
	
	class WorkerThread implements Runnable {

		@Override
		public void run() {
			try {
				grabber.start();
				while (!stopped && grabber.grab() != null);
			} catch (Exception e) {
				LOG.error("Fail to proceed image grabbing process..", e);
			}
		}
	}
}
