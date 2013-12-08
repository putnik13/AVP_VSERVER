package com.atanor.vserver.injector;

import java.util.concurrent.Executors;

import com.atanor.vserver.domain.converter.RecordingConverter;
import com.atanor.vserver.events.DeadEventListener;
import com.atanor.vserver.facades.PlayerFacadeMock;
import com.atanor.vserver.services.PresentationDataService;
import com.atanor.vserver.services.PresentationDataServiceImpl;
import com.atanor.vserver.services.RecordingDataService;
import com.atanor.vserver.services.RecordingDataServiceImpl;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;

public class AppCoreModule extends AbstractModule {

	@Override
	protected void configure() {
		final EventBus eventBus = new AsyncEventBus(Executors.newCachedThreadPool());
		eventBus.register(new DeadEventListener());

		bind(EventBus.class).toInstance(eventBus);
		bind(PlayerFacadeMock.class).asEagerSingleton();
		
		bind(RecordingConverter.class);
		bind(RecordingDataService.class).to(RecordingDataServiceImpl.class);
		bind(PresentationDataService.class).to(PresentationDataServiceImpl.class);
	}

}
