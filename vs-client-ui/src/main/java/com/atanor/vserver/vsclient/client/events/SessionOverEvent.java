package com.atanor.vserver.vsclient.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class SessionOverEvent extends GwtEvent<SessionOverHandler> {

	private static Type<SessionOverHandler> TYPE;

	public static Type<SessionOverHandler> getType() {
		if (TYPE == null) {
			TYPE = new Type<SessionOverHandler>();
		}
		return TYPE;
	}

	@Override
	public final Type<SessionOverHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SessionOverHandler handler) {
		handler.onSessionOver(this);
	}
}