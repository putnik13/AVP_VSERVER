package com.atanor.vserver.facades.player;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.atanor.vserver.async.AsyncConnector;
import com.atanor.vserver.common.Constants;
import com.atanor.vserver.common.entity.Snapshot;
import com.atanor.vserver.domain.entity.VsConfig;
import com.atanor.vserver.events.CloseSessionEvent;
import com.atanor.vserver.events.CreateAndSaveSnapshotEvent;
import com.atanor.vserver.events.GetSnapshotEvent;
import com.atanor.vserver.events.OpenSessionEvent;
import com.atanor.vserver.facades.PlayerFacade;
import com.atanor.vserver.services.ConfigDataService;
import com.atanor.vserver.services.RecordingDataService;
import com.atanor.vserver.util.ImageDecoder;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

public class PlayerFacadeMock implements PlayerFacade {

	private static final Logger LOG = LoggerFactory.getLogger(PlayerFacadeMock.class);

	private static final int DELAY_TIME = 0;
	private static final int INTERVAL_TIME = 3000;
	private static final String SNAPSHOT_NAME = "vlcj-snapshot.png";
	private static final SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss");

	@Inject
	private RecordingDataService recordingService;

	private final EventBus eventBus;
	private final ConfigDataService configService;
	private Timer timer;
	private Long currentRecordingId;

	private final EmbeddedMediaPlayer streamPlayer;
	private final EmbeddedMediaPlayer imagePlayer;

	@Inject
	public PlayerFacadeMock(final EventBus eventBus, final ConfigDataService configService) {
		this.eventBus = eventBus;
		this.configService = configService;
		eventBus.register(this);

		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), config().getPlayerInstallPath());
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);

		this.streamPlayer = new MediaPlayerFactory().newEmbeddedMediaPlayer();
		this.imagePlayer = new MediaPlayerFactory().newEmbeddedMediaPlayer();
		imagePlayer.setSnapshotDirectory(config().getRecordingsOutput());
		imagePlayer.addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
			@Override
			public void snapshotTaken(MediaPlayer mediaPlayer, String filename) {
				LOG.info("snapshotTaken(filename={})", filename);
			}
		});
	}

	@Subscribe
	public void onGetSnapshot(final GetSnapshotEvent event) {
		System.out.println("onGetSnapshot() called");

		final Long random = Math.round(Math.random() * 4);
		final File file = new File("d:/projects/AVP_VSERVER/vs-launch/src/main/webapp/images/test" + random + ".png");
		if (!file.exists()) {
			throw new IllegalStateException("Snapshot is not exist!");
		}

		final String encodedImage = ImageDecoder.encodeImage(file);
		AsyncConnector.broadcastSnapshot(new Snapshot(encodedImage, "795", "586"));
	}

	@Subscribe
	public void onOpenSession(final OpenSessionEvent event) {
		AsyncConnector.startSharingSession();
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				eventBus.post(new GetSnapshotEvent());
			}
		}, DELAY_TIME, INTERVAL_TIME);
	}

	@Subscribe
	public void onCloseSession(final CloseSessionEvent event) {
		AsyncConnector.stopSharingSession();
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	@Override
	public void startRecording() {
		if (!isPlaying()) {
			final Date startTime = new Date();
			final String fileName = buildRecordingName(startTime);
			final String[] options = { String.format(config().getStreamMediaOptions(), buildRecordingPath(fileName)) };
			streamPlayer.playMedia(config().getStreamMediaResource(), options);
			imagePlayer.playMedia(config().getStreamMediaResource());

			currentRecordingId = recordingService.createRecording(fileName, startTime);
			eventBus.post(new CreateAndSaveSnapshotEvent(currentRecordingId));
		}
	}

	@Override
	public void stopRecording() {
		streamPlayer.stop();
		imagePlayer.stop();
		recordingService.updateDuration(currentRecordingId, new Date());

	}

	@Override
	public String getSnapshot() {
		if (imagePlayer.isPlaying()) {
			final BufferedImage bufImage = imagePlayer.getSnapshot(Constants.SNAPSHOT_WIDTH, Constants.SNAPSHOT_HEIGHT);
			if (bufImage != null) {
				return ImageDecoder.encodeImage(bufImage);
			}
		}
		return null;
	}

	@Subscribe
	public void onCreateAndSaveEvent(final CreateAndSaveSnapshotEvent event) throws InterruptedException {
		TimeUnit.SECONDS.sleep(10);
		saveSnapshot();
	}

	private boolean isPlaying() {
		return streamPlayer.isPlaying() && imagePlayer.isPlaying();
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

	private VsConfig config() {
		return configService.getConfig();
	}

	private void saveSnapshot() {
		if (imagePlayer.isPlaying()) {
			final String snapshotName = buildSnapshotName();
			final File file = new File(snapshotName);
			file.deleteOnExit();
			imagePlayer.saveSnapshot(file, Constants.SNAPSHOT_WIDTH, Constants.SNAPSHOT_HEIGHT);
			recordingService.saveSnapshot(currentRecordingId, snapshotName);
		}
	}
}
