package com.atanor.vserver.xuggler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.imageio.ImageIO;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.xuggler.Global;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IContainer.Type;
import com.xuggle.xuggler.IContainerFormat;

public class FrameCapture2 {

	public static final double SECONDS_BETWEEN_FRAMES = 10;

	private static final String inputFilename = "D:/temp/video/test2.mp4";
	private static final String inputStream = "rtp://localhost:5004";
	private static final String outputFilePrefix = "D:/temp/recordings/img";


	public static final long MICRO_SECONDS_BETWEEN_FRAMES = (long) (Global.DEFAULT_PTS_PER_SECOND * SECONDS_BETWEEN_FRAMES);

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		IContainer container = IContainer.make();
		IContainerFormat containerFormat = IContainerFormat.make();
		containerFormat.setInputFormat("rtp"); 
		Socket socket = new Socket();
		socket.connect(InetSocketAddress.createUnresolved("127.0.0.1", 5004));
		
		container.open(socket.getInputStream(), containerFormat);
		IMediaReader mediaReader = ToolFactory.makeReader(container);
		//IMediaReader mediaReader = ToolFactory.makeReader(inputStream);
		
		
		// stipulate that we want BufferedImages created in BGR 24bit color
		// space
		mediaReader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);

		mediaReader.addListener(ToolFactory.makeDebugListener());
		mediaReader.addListener(new ImageSnapListener());

		// read out the contents of the media file and
		// dispatch events to the attached listener
		while (mediaReader.readPacket() == null)
			;
	}

	private static class ImageSnapListener extends MediaListenerAdapter {

		public void onVideoPicture(IVideoPictureEvent event) {

				String outputFilename = dumpImageToFile(event.getImage());

				// indicate file written
				double seconds = ((double) event.getTimeStamp()) / Global.DEFAULT_PTS_PER_SECOND;
				System.out.printf("at elapsed time of %6.3f seconds wrote: %s\n", seconds, outputFilename);

		}

		private String dumpImageToFile(BufferedImage image) {
			try {
				String outputFilename = outputFilePrefix + System.currentTimeMillis() + ".png";
				ImageIO.write(image, "png", new File(outputFilename));
				return outputFilename;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}

	}
}