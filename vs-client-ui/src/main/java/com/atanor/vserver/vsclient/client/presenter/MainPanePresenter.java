package com.atanor.vserver.vsclient.client.presenter;

import com.atanor.vserver.vsclient.client.events.SessionOverEvent;
import com.atanor.vserver.vsclient.client.events.SessionOverHandler;
import com.atanor.vserver.vsclient.client.events.SessionStartEvent;
import com.atanor.vserver.vsclient.client.events.SessionStartHandler;
import com.atanor.vserver.vsclient.client.events.SnapshotReceivedEvent;
import com.atanor.vserver.vsclient.client.events.SnapshotReceivedHandler;
import com.atanor.vserver.vsclient.client.ui.MainPane;

public class MainPanePresenter implements SnapshotReceivedHandler, SessionOverHandler, SessionStartHandler {

	private MainPane view;

	public void setView(final MainPane view) {
		this.view = view;
	}

	@Override
	public void onSnapshotReceived(final SnapshotReceivedEvent event) {
		view.addSnapshot(event.getSnapshot());
	}

	@Override
	public void onSessionOver(final SessionOverEvent event) {
		view.onSessionOver();
	}

	@Override
	public void onSessionStart(SessionStartEvent event) {
		// TODO Auto-generated method stub
		
	}

}
