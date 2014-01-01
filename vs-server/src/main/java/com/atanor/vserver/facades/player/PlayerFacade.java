package com.atanor.vserver.facades.player;

import java.text.SimpleDateFormat;

import javax.inject.Inject;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.atanor.vserver.domain.entity.VsConfig;
import com.atanor.vserver.services.ConfigDataService;
import com.google.common.eventbus.EventBus;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

public abstract class PlayerFacade {

	protected static final int DELAY_TIME = 0;
	protected static final int INTERVAL_TIME = 3000;
	protected static final String SNAPSHOT_NAME = "vlcj-snapshot.png";
	protected static final SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss");

	private final EventBus eventBus;
	private final ConfigDataService configService;

	private final EmbeddedMediaPlayer streamPlayer;
	private final EmbeddedMediaPlayer imagePlayer;

	@Inject
	public PlayerFacade(final EventBus eventBus, final ConfigDataService configService) {
		this.eventBus = eventBus;
		this.configService = configService;
		eventBus.register(this);

		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), config().getPlayerInstallPath());
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);

		this.streamPlayer = new MediaPlayerFactory().newEmbeddedMediaPlayer();
		this.imagePlayer = new MediaPlayerFactory().newEmbeddedMediaPlayer();
	}

	protected VsConfig config() {
		return configService.getConfig();
	}

	protected EmbeddedMediaPlayer getImagePlayer() {
		return imagePlayer;
	}

	protected EmbeddedMediaPlayer getStreamPlayer() {
		return streamPlayer;
	}

	protected EventBus getEventBus() {
		return eventBus;
	}
}
