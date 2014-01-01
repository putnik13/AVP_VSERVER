package com.atanor.vserver.common.async.events;

import com.google.gwt.event.shared.EventHandler;

public interface SnapshotReceivedHandler extends EventHandler {

	void onSnapshotReceived(SnapshotReceivedEvent event);
}
