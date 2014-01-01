package com.atanor.vserver.common.async.events;

import com.google.gwt.event.shared.EventHandler;

public interface VideoSnapshotReceivedHandler extends EventHandler {

	void onVideoSnapshotReceived(VideoSnapshotReceivedEvent event);
}
