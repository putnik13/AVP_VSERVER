package com.atanor.vserver.common.async.events;

import com.google.gwt.event.shared.EventHandler;

public interface SvgReceivedHandler extends EventHandler {

	void onSvgReceived(SvgReceivedEvent event);
}
