package com.atanor.vserver.common.async.events;

import com.google.gwt.event.shared.EventHandler;

public interface SessionOverHandler extends EventHandler {

	void onSessionOver(SessionOverEvent event);
}
