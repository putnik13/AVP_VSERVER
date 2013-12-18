package com.atanor.vserver.vsadmin.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface GetStreamSnapshotHandler extends EventHandler {

	void onGetStreamSnapshot(GetStreamSnapshotEvent event);
}
