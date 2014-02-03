package com.atanor.vserver.facades.player;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import com.atanor.vserver.common.Constants;
import com.atanor.vserver.common.entity.Snapshot;
import com.atanor.vserver.common.entity.Snapshot.TYPE;
import com.atanor.vserver.events.CreateAndSaveSnapshotEvent;
import com.atanor.vserver.events.GetVideoSnapshotEvent;
import com.atanor.vserver.events.VideoSnapshotEvent;
import com.atanor.vserver.facades.ImageGrabber;
import com.atanor.vserver.facades.VideoFacade;
import com.atanor.vserver.facades.VideoRecorder;
import com.atanor.vserver.services.ConfigDataService;
import com.atanor.vserver.services.RecordingDataService;
import com.atanor.vserver.util.ImageDecoder;
import com.google.common.collect.Maps;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class FFmpegVideoFacadeImpl extends PlayerFacade implements VideoFacade {

	@Inject
	private RecordingDataService recordingService;

	private final VideoRecorder recorder;
	private final ImageGrabber grabber;

	private Timer timer;
	private Long currentRecordingId;

	@Inject
	public FFmpegVideoFacadeImpl(final EventBus eventBus, final ConfigDataService configService) {
		super(eventBus, configService);
		recorder = new FFmpegRecorder();
		grabber = new FFmpegImageGrabber(Constants.SNAPSHOT_WIDTH, Constants.SNAPSHOT_HEIGHT);
	}

	@Override
	public void startRecording() {
		if (!isPlaying()) {
			final Date startTime = new Date();
			final String fileName = buildRecordingName(startTime);

			final Map<String, Object> params = Maps.newHashMap();
			params.put("input", config().getRecordingMediaResource());
			params.put("output", buildRecordingPath(fileName));
			//recorder.startRecording(config().getRecordingMediaOptions(), params);
			grabber.start(config().getRecordingMediaResource());
			
			currentRecordingId = recordingService.createRecording(fileName, startTime);
			startTakeSnapshots();
			getEventBus().post(new CreateAndSaveSnapshotEvent(currentRecordingId));
		}
	}

	private void startTakeSnapshots() {
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				getEventBus().post(new GetVideoSnapshotEvent());
			}
		}, DELAY_TIME, INTERVAL_TIME);

	}

	@Subscribe
	public void takeSnapshot(final GetVideoSnapshotEvent event) {
		System.out.println("-- take snapshot");
		if (grabber.isPlaying()) {
			final BufferedImage image = grabber.grab();
			System.out.println("-- grabbed image: " + image);
			if (image != null) {
				getEventBus().post(new VideoSnapshotEvent(createSnapshot(image)));
			}
		}
	}

	@Override
	public void stopRecording() {
		stopTimer();
		cleanSnapshotFolder();
		//recorder.stopRecording();
		grabber.stop();
		recordingService.updateDuration(currentRecordingId, new Date());
	}

	private void stopTimer() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	private void cleanSnapshotFolder() {
		final File snapshot = new File(buildSnapshotName());
		if (snapshot.exists()) {
			snapshot.delete();
		}
	}

	@Subscribe
	public void onCreateAndSaveEvent(final CreateAndSaveSnapshotEvent event) throws InterruptedException {
		TimeUnit.SECONDS.sleep(10);
		saveSnapshot();
	}

	private static String buildRecordingName(final Date date) {
		return "RECORDING-" + df.format(date) + ".mp4";
	}

	private boolean isPlaying() {
		return /*recorder.isPlaying() &&*/ grabber.isPlaying();
	}

	private String buildSnapshotName() {
		return config().getRecordingSnapshotOutput() + "/" + SNAPSHOT_NAME;
	}

	private String buildRecordingPath(final String recordingName) {
		return config().getRecordingsOutput() + "/" + recordingName;
	}

	private void saveSnapshot() {
		if (grabber.isPlaying()) {
			final BufferedImage image = grabber.grab();
			if (image != null) {
				recordingService.saveSnapshot(currentRecordingId, image);
			}
		}
	}

	private Snapshot createSnapshot(final BufferedImage bufImage) {
		final String encodedImage = ImageDecoder.encodeImage(bufImage);
		final String width = String.valueOf(bufImage.getWidth());
		final String height = String.valueOf(bufImage.getHeight());
		return new Snapshot(TYPE.VIDEO, buildSnapshotName(), encodedImage, width, height);
	}
}
