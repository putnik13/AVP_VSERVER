package com.atanor.vserver.common.async.events;

import com.google.gwt.event.shared.EventHandler;

public interface SvgSendHandler extends EventHandler {

	void onSvgSend(SvgSendEvent event);
}
