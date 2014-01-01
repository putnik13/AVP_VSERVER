package com.atanor.vserver.common.async.events;

import com.google.gwt.event.shared.EventHandler;

public interface PresentationStartHandler extends EventHandler {

	void onPresentationStart(PresentationStartEvent event);
}
