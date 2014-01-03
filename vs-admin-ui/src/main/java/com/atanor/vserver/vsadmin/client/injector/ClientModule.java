package com.atanor.vserver.vsadmin.client.injector;

import com.atanor.vserver.vsadmin.client.async.AsyncConnector;
import com.atanor.vserver.vsadmin.client.ui.ContentPane;
import com.atanor.vserver.vsadmin.client.ui.HeaderPane;
import com.atanor.vserver.vsadmin.client.ui.MainPane;
import com.atanor.vserver.vsadmin.client.ui.NavigatePane;
import com.atanor.vserver.vsadmin.client.ui.SectionsPane;
import com.atanor.vserver.vsadmin.client.ui.presenters.ConfigurationPresenter;
import com.atanor.vserver.vsadmin.client.ui.presenters.PresentationPresenter;
import com.atanor.vserver.vsadmin.client.ui.presenters.RecordingPresenter;
import com.atanor.vserver.vsadmin.client.ui.sections.BroadcastingSection;
import com.atanor.vserver.vsadmin.client.ui.sections.ConfigurationSection;
import com.atanor.vserver.vsadmin.client.ui.sections.PresentationSection;
import com.atanor.vserver.vsadmin.client.ui.sections.RecordingSection;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class ClientModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(MainPane.class).in(Singleton.class);
		bind(ContentPane.class).in(Singleton.class);
		bind(NavigatePane.class).in(Singleton.class);
		bind(SectionsPane.class).in(Singleton.class);
		bind(HeaderPane.class).in(Singleton.class);
		bind(RecordingSection.class).in(Singleton.class);
		bind(PresentationSection.class).in(Singleton.class);
		bind(BroadcastingSection.class).in(Singleton.class);
		bind(ConfigurationSection.class).in(Singleton.class);
		
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
		
		bind(RecordingPresenter.class).in(Singleton.class);
		bind(PresentationPresenter.class).in(Singleton.class);
		bind(ConfigurationPresenter.class).in(Singleton.class);
		
		bind(AsyncConnector.class).in(Singleton.class);
	}

}
