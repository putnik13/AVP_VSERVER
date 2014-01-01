package com.atanor.vserver.common.async.events;

import com.google.gwt.event.shared.EventHandler;

public interface PresentationSnapshotReceivedHandler extends EventHandler {

	void onPresentationSnapshotReceived(PresentationSnapshotReceivedEvent event);
}
