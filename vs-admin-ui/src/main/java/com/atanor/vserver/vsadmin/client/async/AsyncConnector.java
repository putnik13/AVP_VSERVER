package com.atanor.vserver.vsadmin.client.async;

import javax.inject.Inject;

import com.atanor.vserver.common.async.BaseAsyncConnector;
import com.atanor.vserver.common.async.events.PresentationSnapshotReceivedEvent;
import com.atanor.vserver.common.async.events.VideoSnapshotReceivedEvent;
import com.atanor.vserver.common.entity.Snapshot;
import com.google.web.bindery.event.shared.EventBus;

public class AsyncConnector extends BaseAsyncConnector {

	@Inject
	private EventBus eventBus;

	public void connect() {
		connectWebsocket();
	}

	@Override
	protected void handleMessage(Object message) {

		if (message instanceof Snapshot) {
			final Snapshot snapshot = (Snapshot) message;

			switch (snapshot.getType()) {
			case VIDEO:
				eventBus.fireEvent(new VideoSnapshotReceivedEvent(snapshot));
				break;
			case PRESENTATION:
				eventBus.fireEvent(new PresentationSnapshotReceivedEvent(snapshot));
				break;
			default:
				break;
			}
		}
	}

}
