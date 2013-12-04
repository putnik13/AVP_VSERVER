package com.atanor.vserver.vsclient.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class ConnectionClosedEvent extends GwtEvent<ConnectionClosedHandler> {

	private static Type<ConnectionClosedHandler> TYPE;

	public static Type<ConnectionClosedHandler> getType() {
		if (TYPE == null) {
			TYPE = new Type<ConnectionClosedHandler>();
		}
		return TYPE;
	}

	@Override
	public final Type<ConnectionClosedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ConnectionClosedHandler handler) {
		handler.onCloseConnection(this);
	}
}