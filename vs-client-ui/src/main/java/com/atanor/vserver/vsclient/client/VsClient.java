package com.atanor.vserver.vsclient.client;


import com.atanor.vserver.common.async.events.PresentationOverEvent;
import com.atanor.vserver.common.async.events.PresentationStartEvent;
import com.atanor.vserver.common.async.events.PresentationSnapshotReceivedEvent;
import com.atanor.vserver.vsclient.client.async.AsyncConnector;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class VsClient implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		RootPanel.get().add(Client.getView());
		Client.bind();
		register();
		
		AsyncConnector.connect();
	}
	
	private static void register() {
		Client.getEventBus().addHandler(PresentationSnapshotReceivedEvent.getType(), Client.getPresenter());
		Client.getEventBus().addHandler(PresentationOverEvent.getType(), Client.getPresenter());
		Client.getEventBus().addHandler(PresentationStartEvent.getType(), Client.getPresenter());
	}
}
