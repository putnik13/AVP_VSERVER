package com.atanor.vserver.injector;

import java.util.concurrent.Executors;

import com.atanor.vserver.events.DeadEventListener;
import com.atanor.vserver.facades.PalantirFacade;
import com.atanor.vserver.facades.PlayerFacade;
import com.atanor.vserver.facades.palantir.PalantirFacadeMock;
import com.atanor.vserver.facades.player.PlayerFacadeMock;
import com.atanor.vserver.services.ConfigDataService;
import com.atanor.vserver.services.PresentationDataService;
import com.atanor.vserver.services.RecordingDataService;
import com.atanor.vserver.services.impl.ConfigDataServiceImpl;
import com.atanor.vserver.services.impl.PresentationDataServiceImpl;
import com.atanor.vserver.services.impl.RecordingDataServiceImpl;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class AppCoreModule extends AbstractModule {

	@Override
	protected void configure() {
		final EventBus eventBus = new AsyncEventBus(Executors.newCachedThreadPool());
		eventBus.register(new DeadEventListener());

		bind(EventBus.class).toInstance(eventBus);

		bind(RecordingDataService.class).to(RecordingDataServiceImpl.class);
		bind(PresentationDataService.class).to(PresentationDataServiceImpl.class);
		bind(ConfigDataService.class).to(ConfigDataServiceImpl.class);
		
		bind(PlayerFacade.class).to(PlayerFacadeMock.class).in(Scopes.SINGLETON);
		bind(PalantirFacade.class).to(PalantirFacadeMock.class).in(Scopes.SINGLETON);
	}

}
