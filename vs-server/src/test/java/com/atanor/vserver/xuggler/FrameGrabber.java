package com.atanor.vserver.xuggler;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.FFmpegFrameGrabber;
import com.googlecode.javacv.FFmpegFrameRecorder;
import com.googlecode.javacv.cpp.avcodec;
import com.googlecode.javacv.cpp.avutil;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

import static com.googlecode.javacv.cpp.avutil.*;

public class FrameGrabber {

	private static final String inputFilename = "D:/temp/video/test2.mp4";
	private static final String output = "D:/temp/video/test222.mp4";
	
	private static int sampleAudioRateInHz = 44100;
	private static int imageWidth = 320;
	private static int imageHeight = 240;
	private static int frameRate = 30;

	/**
	 * @param args
	 * @throws Exception
	 * @throws com.googlecode.javacv.FrameGrabber.Exception
	 */
	public static void main(String[] args) throws Exception {

//		 recorder.setFormat("mp4");
//		 recorder.setSampleRate(sampleAudioRateInHz);
//		 recorder.setFrameRate(frameRate);
//		 recorder.start();

//		CanvasFrame canvas = new CanvasFrame("Client");
//		canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		//canvas.setCanvasSize(640, 480);
		
		FFmpegFrameGrabber stream = new FFmpegFrameGrabber("rtp://localhost:5004");
		//FFmpegFrameGrabber stream = new FFmpegFrameGrabber("D:/temp/video/test2.mp4");
		//stream.setFormat("mp4");
		// recorder.setFormat("mp4");
		// recorder.setSampleRate(sampleAudioRateInHz);
		// recorder.setFrameRate(frameRate);
		stream.start();
		IplImage grabImg = stream.grab();
		
		FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(output, 1280, 720);
	    recorder.setFormat("mp4");
	    recorder.setFrameRate(25);
	    //recorder.setAudioChannels(stream.getAudioChannels());
	    recorder.setAudioCodec(avcodec.AV_CODEC_ID_MP2);
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
//	    recorder.setPixelFormat(AV_PIX_FMT_YUV420P);
	    //recorder.setVideoBitrate(10 * 1024 * 1024);
//	    recorder.setVideoOption("preset", "ultrafast");
//	    recorder.setVideoQuality(20.0);
	    //recorder.setSampleRate(sampleAudioRateInHz);
	    
		recorder.start();
		while ((grabImg = stream.grab()) != null) {
			IplImage image = stream.grab();
			if (image != null) {
				//canvas.setCanvasSize(640, 480);
				//canvas.showImage(image);
				recorder.record(grabImg);
			}
		}
		stream.stop();
	    recorder.stop();
	    
		// FrameGrabber grabber = new VideoInputFrameGrabber(0);
		// //OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
		// grabber.start();
		// IplImage grabbedImage = grabber.grab();
		//
		// CanvasFrame canvasFrame = new CanvasFrame("Cam");
		// canvasFrame.setCanvasSize(grabbedImage.width(),
		// grabbedImage.height());
		//
		// System.out.println("framerate = " + grabber.getFrameRate());
		// grabber.setFrameRate(grabber.getFrameRate());
		// FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(FILENAME,
		// grabber.getImageWidth(),grabber.getImageHeight());
		//
		// recorder.setVideoCodec(13);
		// recorder.setFormat("mp4");
		// recorder.setPixelFormat(avutil.PIX_FMT_YUV420P);
		// recorder.setFrameRate(30);
		// recorder.setVideoBitrate(10 * 1024 * 1024);
		//
		// recorder.start();
		// while (canvasFrame.isVisible() && (grabbedImage = grabber.grab()) !=
		// null) {
		// canvasFrame.showImage(grabbedImage);
		// recorder.record(grabbedImage);
		// }
		// recorder.stop();
		// grabber.stop();
		// canvasFrame.dispose();

	}

}
