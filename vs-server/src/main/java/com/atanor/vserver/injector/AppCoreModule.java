package com.atanor.vserver.injector;

import java.util.concurrent.Executors;

import com.atanor.vserver.events.DeadEventListener;
import com.atanor.vserver.facades.PalantirFacade;
import com.atanor.vserver.facades.PresentationFacade;
import com.atanor.vserver.facades.VideoFacade;
import com.atanor.vserver.facades.palantir.PalantirFacadeMock;
import com.atanor.vserver.facades.player.VideoFacadeImpl;
import com.atanor.vserver.facades.player.PresentationFacadeImpl;
import com.atanor.vserver.pdf.PdfGenerator;
import com.atanor.vserver.pdf.impl.PdfGeneratorImpl;
import com.atanor.vserver.services.ConfigDataService;
import com.atanor.vserver.services.ImageService;
import com.atanor.vserver.services.PresentationDataService;
import com.atanor.vserver.services.RecordingDataService;
import com.atanor.vserver.services.impl.ConfigDataServiceImpl;
import com.atanor.vserver.services.impl.ImageServiceImpl;
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

		bind(RecordingDataService.class).to(RecordingDataServiceImpl.class).in(Scopes.SINGLETON);
		bind(PresentationDataService.class).to(PresentationDataServiceImpl.class).in(Scopes.SINGLETON);
		bind(ConfigDataService.class).to(ConfigDataServiceImpl.class);
		bind(ImageService.class).to(ImageServiceImpl.class);
		
		bind(PalantirFacade.class).to(PalantirFacadeMock.class).in(Scopes.SINGLETON);
		bind(VideoFacade.class).to(VideoFacadeImpl.class).in(Scopes.SINGLETON);
		bind(PresentationFacade.class).to(PresentationFacadeImpl.class).in(Scopes.SINGLETON);
		
		bind(PdfGenerator.class).to(PdfGeneratorImpl.class);
	}

}
