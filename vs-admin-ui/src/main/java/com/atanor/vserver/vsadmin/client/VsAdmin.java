package com.atanor.vserver.vsadmin.client;

import com.atanor.vserver.common.async.events.PresentationSnapshotReceivedEvent;
import com.atanor.vserver.common.async.events.VideoSnapshotReceivedEvent;
import com.atanor.vserver.vsadmin.client.events.SectionAnimationStartedEvent;
import com.atanor.vserver.vsadmin.client.events.SectionSelectedEvent;
import com.atanor.vserver.vsadmin.client.injector.ClientInjector;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class VsAdmin implements EntryPoint {

	private static final ClientInjector injector = GWT.create(ClientInjector.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		RootPanel.get().add(injector.getMainPane());
		register();

		connectAsync();

		initStreamControlSection();
		initShareConferenceSection();
		initEditConfigurationSection();
	}

	private static void register() {
		injector.getEventBus().addHandler(SectionSelectedEvent.getType(), injector.getContentPane());
		injector.getEventBus().addHandler(SectionAnimationStartedEvent.getType(), injector.getNavigatePane());
		injector.getEventBus().addHandler(VideoSnapshotReceivedEvent.getType(), injector.getStreamControlPresenter());
		injector.getEventBus().addHandler(PresentationSnapshotReceivedEvent.getType(), injector.getShareConferencePresenter());
	}

	private static void connectAsync() {
		injector.getAsyncConnector().connect();
	}

	private static void initStreamControlSection() {
		injector.getStreamControlPresenter().refreshRecordings();
	}

	private static void initShareConferenceSection() {
		injector.getShareConferencePresenter().refreshPresentations();
	}

	private static void initEditConfigurationSection() {
		injector.getEditConfigurationPresenter().refreshConfiguration();
	}

}
