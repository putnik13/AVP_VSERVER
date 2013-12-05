package com.atanor.vserver.vsclient.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface SessionStartHandler extends EventHandler {

	void onSessionStart(SessionStartEvent event);
}
