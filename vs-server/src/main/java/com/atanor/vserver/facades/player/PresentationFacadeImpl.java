package com.atanor.vserver.facades.player;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import com.atanor.vserver.async.AsyncConnector;
import com.atanor.vserver.common.entity.Snapshot;
import com.atanor.vserver.common.entity.Snapshot.TYPE;
import com.atanor.vserver.events.GetSnapshotEvent;
import com.atanor.vserver.events.PresentationSnapshotEvent;
import com.atanor.vserver.facades.PresentationFacade;
import com.atanor.vserver.services.ConfigDataService;
import com.atanor.vserver.util.ImageDecoder;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class PresentationFacadeImpl extends PlayerFacade implements PresentationFacade {

	private Timer timer;
	private int snapshotCount = 0;

	@Inject
	public PresentationFacadeImpl(EventBus eventBus, ConfigDataService configService) {
		super(eventBus, configService);
		getImagePlayer().setSnapshotDirectory(config().getPresentationsOutput());
	}

	@Override
	public void startPresentation() {
		AsyncConnector.startSharingSession();
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				getEventBus().post(new GetSnapshotEvent());
				snapshotCount++;
			}
		}, DELAY_TIME, INTERVAL_TIME);
	}

	@Override
	public void stopPresentation() {
		stopTimer();
		snapshotCount = 0;
		AsyncConnector.stopSharingSession();
	}

	private void stopTimer() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}
	
	@Subscribe
	public void onGetSnapshot(final GetSnapshotEvent event) throws IOException {
		System.out.println("onGetSnapshot() called");

		final Long random = Math.round(Math.random() * 4);
		final File file = new File("d:/projects/AVP_VSERVER/vs-launch/src/main/webapp/images/test" + random + ".png");
		if (!file.exists()) {
			throw new IllegalStateException("Snapshot is not exist!");
		}

		final String encodedImage = ImageDecoder.encodeImage(file);
		final Snapshot snapshot = new Snapshot(TYPE.PRESENTATION, encodedImage, "795", "586");
		final BufferedImage in = ImageIO.read(file);
		final String fileName = config().getPresentationSnapshotOutput() + "/" + snapshotCount + ".png";
		getEventBus().post(new PresentationSnapshotEvent(in, fileName, snapshot));
	}

}
