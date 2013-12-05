package com.atanor.vserver.vsclient.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class SessionStartEvent extends GwtEvent<SessionStartHandler> {

	private static Type<SessionStartHandler> TYPE;

	public static Type<SessionStartHandler> getType() {
		if (TYPE == null) {
			TYPE = new Type<SessionStartHandler>();
		}
		return TYPE;
	}

	@Override
	public final Type<SessionStartHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SessionStartHandler handler) {
		handler.onSessionStart(this);
	}
}