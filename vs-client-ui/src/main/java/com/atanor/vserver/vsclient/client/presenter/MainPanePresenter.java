package com.atanor.vserver.vsclient.client.presenter;

import com.atanor.vserver.vsclient.client.events.ConnectionClosedEvent;
import com.atanor.vserver.vsclient.client.events.ConnectionClosedHandler;
import com.atanor.vserver.vsclient.client.events.SnapshotReceivedEvent;
import com.atanor.vserver.vsclient.client.events.SnapshotReceivedHandler;
import com.atanor.vserver.vsclient.client.ui.MainPane;

public class MainPanePresenter implements SnapshotReceivedHandler, ConnectionClosedHandler {

	private MainPane view;

	public void setView(final MainPane view) {
		this.view = view;
	}

	@Override
	public void onSnapshotReceived(final SnapshotReceivedEvent event) {
		view.addSnapshot(event.getSnapshot());
	}

	@Override
	public void onCloseConnection(final ConnectionClosedEvent event) {
		view.onConnectionClosed();
	}

}
