package com.atanor.vserver.common.async.events;

import com.google.gwt.event.shared.GwtEvent;

public class PresentationStartEvent extends GwtEvent<PresentationStartHandler> {

	private static Type<PresentationStartHandler> TYPE;

	public static Type<PresentationStartHandler> getType() {
		if (TYPE == null) {
			TYPE = new Type<PresentationStartHandler>();
		}
		return TYPE;
	}

	@Override
	public final Type<PresentationStartHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PresentationStartHandler handler) {
		handler.onPresentationStart(this);
	}
}