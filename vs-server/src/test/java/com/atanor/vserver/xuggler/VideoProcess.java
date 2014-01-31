package com.atanor.vserver.xuggler;

import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.exec.environment.EnvironmentUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VideoProcess {

	/**
	 * @param args
	 * @throws IOException
	 * @throws ExecuteException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws ExecuteException, IOException, InterruptedException {
		// CommandLine command = new CommandLine("ls");
		// PumpStreamHandler streamHandler = new PumpStreamHandler();
		// DefaultExecutor executor = new DefaultExecutor();
		// executor.setStreamHandler(streamHandler);
		// executor.execute(command);

		CommandLine command = new CommandLine("ffmpeg");
		command.addArguments(new String[]{"-i", "test.mp4 ","-vcodec", "copy", "-acodec", "copy", "test2.mp4"});// Number of seconds to sleep
		//System.out.println("Before Sleep");
		//System.out.println("VARS: " + EnvironmentUtils.getProcEnvironment());
		DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
		DefaultExecutor executor = new DefaultExecutor();
		PumpStreamHandler streamHandler = new PumpStreamHandler();
		executor.setStreamHandler(streamHandler);
		//executor.setExitValue(1);
		executor.execute(command, EnvironmentUtils.getProcEnvironment());
		

//		// Wait for the command to finish execution.
//		resultHandler.waitFor();
//		// Obtain the exit value of the command.
//		int exitValue = resultHandler.getExitValue();
//		if (executor.isFailure(exitValue)) {
//			System.out.println("Command execution failed");
//		} else {
//			System.out.println("Command execution Successful");
//		}
	}

}
