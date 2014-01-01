package com.atanor.vserver.common.async.events;

import com.google.gwt.event.shared.GwtEvent;

public class PresentationOverEvent extends GwtEvent<PresentationOverHandler> {

	private static Type<PresentationOverHandler> TYPE;

	public static Type<PresentationOverHandler> getType() {
		if (TYPE == null) {
			TYPE = new Type<PresentationOverHandler>();
		}
		return TYPE;
	}

	@Override
	public final Type<PresentationOverHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PresentationOverHandler handler) {
		handler.onPresentationOver(this);
	}
}