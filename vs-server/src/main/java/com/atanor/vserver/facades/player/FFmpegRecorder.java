package com.atanor.vserver.facades.player;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;

import com.google.common.collect.Maps;


public class FFmpegRecorder {

	private DefaultExecutor executor;
	
	public void startRecording() throws ExecuteException, IOException, InterruptedException {
		if(executor != null){
			return;
		}
		
		final CommandLine cmdLine = new CommandLine("ffmpeg");
		cmdLine.addArgument("-i").addArgument("${mediasource}");
		cmdLine.addArgument("-vcodec").addArgument("copy");
		cmdLine.addArgument("-acodec").addArgument("copy");
		cmdLine.addArgument("${output}");
		
		final Map<String, Object> map = Maps.newHashMap();
		map.put("mediasource", "D:/temp/video/Flower.mp4");
		//map.put("mediasource", "rtp://127.0.0.1:5004");
		map.put("output", "D:/temp/video/temp222.mp4");
		
		cmdLine.setSubstitutionMap(map);
		
		//Takes System.out for dumping the output and System.err for Error
		final PumpStreamHandler streamHandler = new PumpStreamHandler();
		final DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
		executor = new DefaultExecutor();
		executor.setStreamHandler(streamHandler);
		executor.setWatchdog(new ExecuteWatchdog(ExecuteWatchdog.INFINITE_TIMEOUT));
		executor.execute(cmdLine, resultHandler);
		System.out.println("Recording started asynchronously..");
		//Thread.sleep(20000);
	}

	public void stopRecording() {
		if(executor != null){
			executor.getWatchdog().destroyProcess();
		}
	}
	
}
