package com.atanor.vserver.facades.player;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;
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
import com.atanor.vserver.facades.VideoFacade;
import com.atanor.vserver.services.ConfigDataService;
import com.atanor.vserver.services.RecordingDataService;
import com.atanor.vserver.util.ImageDecoder;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class VideoFacadeImpl extends PlayerFacade implements VideoFacade {

	@Inject
	private RecordingDataService recordingService;

	private Timer timer;
	private Long currentRecordingId;

	@Inject
	public VideoFacadeImpl(EventBus eventBus, ConfigDataService configService) {
		super(eventBus, configService);
		getImagePlayer().setSnapshotDirectory(config().getRecordingsOutput());
	}

	@Override
	public void startRecording() {
		if (!isPlaying()) {
			final Date startTime = new Date();
			final String fileName = buildRecordingName(startTime);
			final String[] options = { String.format(config().getRecordingMediaOptions(), buildRecordingPath(fileName)) };
			getStreamPlayer().playMedia(config().getRecordingMediaResource(), options);
			getImagePlayer().playMedia(config().getRecordingMediaResource());

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
		if (getImagePlayer().isPlaying()) {
			final BufferedImage bufImage = getImagePlayer().getSnapshot(Constants.SNAPSHOT_WIDTH,
					Constants.SNAPSHOT_HEIGHT);
			if (bufImage != null) {
				getEventBus().post(new VideoSnapshotEvent(createSnapshot(bufImage)));
			}
		}
	}

	@Override
	public void stopRecording() {
		stopTimer();
		getStreamPlayer().stop();
		getImagePlayer().stop();
		recordingService.updateDuration(currentRecordingId, new Date());

	}

	private void stopTimer() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	@Subscribe
	public void onCreateAndSaveEvent(final CreateAndSaveSnapshotEvent event) throws InterruptedException {
		TimeUnit.SECONDS.sleep(10);
		saveSnapshot();
	}

	private boolean isPlaying() {
		return getStreamPlayer().isPlaying() && getImagePlayer().isPlaying();
	}

	private static String buildRecordingName(final Date date) {
		return "RECORDING-" + df.format(date) + ".mp4";
	}

	private String buildRecordingPath(final String recordingName) {
		return config().getRecordingsOutput() + "/" + recordingName;
	}

	private String buildSnapshotName() {
		return config().getRecordingsOutput() + "/" + SNAPSHOT_NAME;
	}

	private void saveSnapshot() {
		if (getImagePlayer().isPlaying()) {
			final String snapshotName = buildSnapshotName();
			final File file = new File(snapshotName);
			file.deleteOnExit();
			getImagePlayer().saveSnapshot(file, Constants.SNAPSHOT_WIDTH, Constants.SNAPSHOT_HEIGHT);
			recordingService.saveSnapshot(currentRecordingId, snapshotName);
		}
	}

	private Snapshot createSnapshot(final BufferedImage bufImage) {
		final String encodedImage = ImageDecoder.encodeImage(bufImage);
		return new Snapshot(TYPE.VIDEO, encodedImage, String.valueOf(bufImage.getWidth()), String.valueOf(bufImage
				.getHeight()));
	}
}
