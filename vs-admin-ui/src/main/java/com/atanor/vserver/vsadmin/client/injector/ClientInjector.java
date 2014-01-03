package com.atanor.vserver.vsadmin.client.injector;

import com.atanor.vserver.common.rpc.services.ConfigService;
import com.atanor.vserver.common.rpc.services.PresentationService;
import com.atanor.vserver.common.rpc.services.RecordingService;
import com.atanor.vserver.vsadmin.client.async.AsyncConnector;
import com.atanor.vserver.vsadmin.client.ui.MainPane;
import com.atanor.vserver.vsadmin.client.ui.NavigatePane;
import com.atanor.vserver.vsadmin.client.ui.SectionsPane;
import com.atanor.vserver.vsadmin.client.ui.presenters.ConfigurationPresenter;
import com.atanor.vserver.vsadmin.client.ui.presenters.PresentationPresenter;
import com.atanor.vserver.vsadmin.client.ui.presenters.RecordingPresenter;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.web.bindery.event.shared.EventBus;

@GinModules(ClientModule.class)
public interface ClientInjector extends Ginjector {

	EventBus getEventBus();

	MainPane getMainPane();

	SectionsPane getContentPane();

	NavigatePane getNavigatePane();

	RecordingPresenter getStreamControlPresenter();

	PresentationPresenter getShareConferencePresenter();

	ConfigurationPresenter getEditConfigurationPresenter();

	RecordingService getRecordingService();

	PresentationService getPresentationService();
	
	ConfigService getConfigService();
	
	AsyncConnector getAsyncConnector();
}
