package com.atanor.vserver.vsclient.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface ConnectionClosedHandler extends EventHandler {

	void onCloseConnection(ConnectionClosedEvent event);
}
