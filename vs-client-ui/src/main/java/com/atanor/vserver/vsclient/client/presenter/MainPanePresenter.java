package com.atanor.vserver.vsclient.client.presenter;

import com.atanor.vserver.common.async.events.PresentationOverEvent;
import com.atanor.vserver.common.async.events.PresentationOverHandler;
import com.atanor.vserver.common.async.events.PresentationStartEvent;
import com.atanor.vserver.common.async.events.PresentationStartHandler;
import com.atanor.vserver.common.async.events.PresentationSnapshotReceivedEvent;
import com.atanor.vserver.common.async.events.PresentationSnapshotReceivedHandler;
import com.atanor.vserver.vsclient.client.ui.MainPane;

public class MainPanePresenter implements PresentationSnapshotReceivedHandler, PresentationOverHandler, PresentationStartHandler {

	private MainPane view;

	public void setView(final MainPane view) {
		this.view = view;
	}

	@Override
	public void onPresentationSnapshotReceived(final PresentationSnapshotReceivedEvent event) {
		view.addSnapshot(event.getSnapshot());
	}

	@Override
	public void onPresentationOver(final PresentationOverEvent event) {
		view.onSessionOver(event.getPdfFileName());
	}

	@Override
	public void onPresentationStart(PresentationStartEvent event) {
		view.onSessionStart();
	}

}
