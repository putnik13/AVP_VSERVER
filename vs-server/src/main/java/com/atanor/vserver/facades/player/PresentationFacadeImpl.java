package com.atanor.vserver.facades.player;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import com.atanor.vserver.async.AsyncConnector;
import com.atanor.vserver.common.entity.Snapshot;
import com.atanor.vserver.common.entity.Snapshot.TYPE;
import com.atanor.vserver.events.GetSnapshotEvent;
import com.atanor.vserver.facades.PresentationFacade;
import com.atanor.vserver.services.ConfigDataService;
import com.atanor.vserver.util.ImageDecoder;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class PresentationFacadeImpl extends PlayerFacade implements PresentationFacade {

	private Timer timer;

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
			}
		}, DELAY_TIME, INTERVAL_TIME);
	}

	@Override
	public void stopPresentation() {
		AsyncConnector.stopSharingSession();
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
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
		AsyncConnector.broadcastSnapshot(new Snapshot(TYPE.PRESENTATION, encodedImage, "795", "586"));
	}

}
