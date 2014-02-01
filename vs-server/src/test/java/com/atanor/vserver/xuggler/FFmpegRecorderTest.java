package com.atanor.vserver.xuggler;

import java.io.IOException;

import org.apache.commons.exec.ExecuteException;

import com.atanor.vserver.facades.player.FFmpegRecorder;

public class FFmpegRecorderTest {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ExecuteException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws ExecuteException, IOException, InterruptedException {
		FFmpegRecorder recorder = new FFmpegRecorder();
		//recorder.startRecording();

		System.out.println("hello from test");
	}

}
