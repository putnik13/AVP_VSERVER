package com.atanor.vserver.xuggler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.imageio.ImageIO;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.xuggler.Converter;
import com.xuggle.xuggler.Global;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IContainer.Type;
import com.xuggle.xuggler.IContainerFormat;
import com.xuggle.xuggler.IPacket;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;

public class FrameCapture2 {

	public static final double SECONDS_BETWEEN_FRAMES = 10;

	private static final String inputFilename = "D:/temp/video/test2.mp4";
	private static final String inputStream = "rtp://127.0.0.1:5004";
	private static final String outputFilePrefix = "D:/temp/recordings/img";


	public static final long MICRO_SECONDS_BETWEEN_FRAMES = (long) (Global.DEFAULT_PTS_PER_SECOND * SECONDS_BETWEEN_FRAMES);

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws IOException, ParseException {

//		IContainer container = IContainer.make();
//		IContainerFormat containerFormat = IContainerFormat.make();
//		containerFormat.setInputFormat("rtp"); 
//		
//		container.open(inputStream, Type.READ, containerFormat);
//		IMediaReader mediaReader = ToolFactory.makeReader(container);
//		mediaReader.setAddDynamicStreams(true);
//        mediaReader.setQueryMetaData(true);
		//IMediaReader mediaReader = ToolFactory.makeReader(inputStream);
		
		//mediaReader.addListener(ToolFactory.makeWriter("D:/temp/video/test222.mp4", mediaReader));

		// read out the contents of the media file and
		// dispatch events to the attached listener
//		while (mediaReader.readPacket() == null)
//			;

		final IContainer input = IContainer.make();
		final IContainer output = IContainer.make();
		output.open("D:/temp/video/test222.mp4", IContainer.Type.WRITE, null);
		
		input.open(inputFilename, IContainer.Type.READ, null);
		int numStreams = input.getNumStreams();
		for(int i = 0; i < numStreams; i++)
	    {
	      final IStream stream = input.getStream(i);
	      final IStreamCoder coder = stream.getStreamCoder();
	      coder.open(null, null);
	      
	      output.addNewStream(coder.getCodec());
	      IStreamCoder newCoder = IStreamCoder.make(IStreamCoder.Direction.ENCODING, coder);
	      output.getStream(i).setStreamCoder(newCoder);	   
	      newCoder.open(null, null);
	    }
		
		
		//output.writeHeader();
		for (;;) {
			final IPacket pkt = IPacket.make();
			if (input.readNextPacket(pkt) < 0)
				break;

			output.writePacket(pkt, false);
		}
		
		//output.writeTrailer();
		
		int streams = output.getNumStreams();
		for (int j = 0; j < streams; j++) {
			output.getStream(j).getStreamCoder().close();
		}
		output.close();
	}

	
}