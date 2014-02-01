package com.atanor.vserver.facades.player;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atanor.vserver.facades.VideoRecorder;

public class FFmpegRecorder implements VideoRecorder {

	private static final Logger LOG = LoggerFactory.getLogger(FFmpegRecorder.class);

	private DefaultExecutor executor;

	@Override
	public void startRecording(final String line, final Map<String, Object> params) {
		if (isPlaying()) {
			return;
		}

		final CommandLine cmdLine = CommandLine.parse(line);
		cmdLine.setSubstitutionMap(params);

		// Takes System.out for dumping the output and System.err for Error
		final PumpStreamHandler streamHandler = new PumpStreamHandler();
		final DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();

		try {
			executor = new DefaultExecutor();
			executor.setStreamHandler(streamHandler);
			executor.setWatchdog(new ExecuteWatchdog(ExecuteWatchdog.INFINITE_TIMEOUT));
			executor.execute(cmdLine, resultHandler);
		} catch (IOException e) {
			LOG.error("Failure to start stream recording..", e);
		}
		LOG.info(">>>>>> FFmpeg recorder started recording.");
	}

	@Override
	public void stopRecording() {
		if (isPlaying()) {
			executor.getWatchdog().destroyProcess();
			executor = null;
			LOG.info("<<<<<< FFmpeg recorder stopped recording.");
		}
	}

	@Override
	public boolean isPlaying() {
		return executor != null;
	}

}
