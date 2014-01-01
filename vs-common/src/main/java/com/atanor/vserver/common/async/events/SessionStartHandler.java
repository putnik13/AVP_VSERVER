package com.atanor.vserver.common.async.events;

import com.google.gwt.event.shared.EventHandler;

public interface SessionStartHandler extends EventHandler {

	void onSessionStart(SessionStartEvent event);
}
