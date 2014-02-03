package com.atanor.vserver.facades.player;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import com.atanor.vserver.async.AsyncConnector;
import com.atanor.vserver.common.entity.Snapshot;
import com.atanor.vserver.common.entity.Snapshot.TYPE;
import com.atanor.vserver.events.GetPresentationSnapshotEvent;
import com.atanor.vserver.events.PresentationSnapshotEvent;
import com.atanor.vserver.facades.SnapshotGrabber;
import com.atanor.vserver.facades.PresentationFacade;
import com.atanor.vserver.services.ConfigDataService;
import com.atanor.vserver.services.ImageService;
import com.atanor.vserver.services.PresentationDataService;
import com.atanor.vserver.util.ImageDecoder;
import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class PresentationFacadeImpl extends PlayerFacade implements PresentationFacade {

	private Timer timer;
	private int snapshotCount = 1;
	private String folderName;
	private final List<Snapshot> snapshots = Lists.newArrayList();

	private final SnapshotGrabber grabber;
	private BufferedImage bufImage;

	@Inject
	private PresentationDataService presentationService;

	@Inject
	private ImageService imageService;

	@Inject
	public PresentationFacadeImpl(final EventBus eventBus, final ConfigDataService configService) {
		super(eventBus, configService);
		grabber = new FFmpegSnapshotGrabber();
	}

	@Override
	public void startPresentation() {
		if (grabber.isPlaying()) {
			folderName = buildFolderName();
			createFolder(folderName);

			grabber.start(config().getPresentationMediaResource());
			AsyncConnector.startSharingSession();
			startTakeSnapshots();
		}
	}

	private void startTakeSnapshots() {
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				getEventBus().post(new GetPresentationSnapshotEvent());
			}
		}, DELAY_TIME, INTERVAL_TIME);

	}

	private String buildFolderName() {
		return "PRESENTAION-" + df.format(new Date());
	}

	private String buildPresentationPdfName() {
		return folderName + ".pdf";
	}

	private String buildSnapshotName() {
		return config().getPresentationSnapshotOutput() + "/" + folderName + "/" + snapshotCount + ".png";
	}

	private void createFolder(final String name) {
		final String fileName = config().getPresentationSnapshotOutput() + "/" + name;
		final File snapshotFolder = new File(fileName);
		if (!snapshotFolder.exists()) {
			snapshotFolder.mkdirs();
		}
	}

	@Override
	public void stopPresentation() {
		stopTimer();
		grabber.stop();
		final String pdfName = buildPresentationPdfName();
		presentationService.saveAndGeneratePdf(pdfName, snapshots);
		AsyncConnector.stopSharingSession(pdfName);
		resetSnapshotCount();
		folderName = null;
		bufImage = null;
		snapshots.clear();
	}

	private void resetSnapshotCount() {
		snapshotCount = 1;
	}

	private void stopTimer() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	@Subscribe
	public void takeSnapshot(final GetPresentationSnapshotEvent event) throws IOException {
		if (grabber.isPlaying()) {

			final BufferedImage prevBufImage = bufImage;
			bufImage = grabber.grab();
			if (bufImage != null) {

				if (prevBufImage != null && imageService.isSimilar(prevBufImage, bufImage)) {
					return;
				}

				final String encodedImage = ImageDecoder.encodeImage(bufImage);
				final String width = String.valueOf(bufImage.getWidth());
				final String height = String.valueOf(bufImage.getHeight());
				final Snapshot snapshot = new Snapshot(TYPE.PRESENTATION, buildSnapshotName(), encodedImage, width,
						height);
				snapshots.add(snapshot);

				getEventBus().post(new PresentationSnapshotEvent(bufImage, snapshot));
				snapshotCount++;
			}
		}
	}

}
