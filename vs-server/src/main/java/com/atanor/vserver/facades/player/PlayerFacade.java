package com.atanor.vserver.facades.player;

import java.text.SimpleDateFormat;

import com.atanor.vserver.domain.entity.VsConfig;
import com.atanor.vserver.services.ConfigDataService;
import com.google.common.eventbus.EventBus;

public abstract class PlayerFacade {

	protected static final int DELAY_TIME = 0;
	protected static final int INTERVAL_TIME = 3000;
	protected static final String SNAPSHOT_NAME = "vlcj-snapshot.png";
	protected static final SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss");

	private final EventBus eventBus;
	private final ConfigDataService configService;

	public PlayerFacade(final EventBus eventBus, final ConfigDataService configService) {
		this.eventBus = eventBus;
		this.configService = configService;
		eventBus.register(this);
	}

	protected VsConfig config() {
		return configService.getConfig();
	}

	protected EventBus getEventBus() {
		return eventBus;
	}
}
