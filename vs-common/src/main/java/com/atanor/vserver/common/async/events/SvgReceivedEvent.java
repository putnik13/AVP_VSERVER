package com.atanor.vserver.common.async.events;

import com.atanor.vserver.common.entity.SvgMessage;
import com.google.gwt.event.shared.GwtEvent;

public class SvgReceivedEvent extends GwtEvent<SvgReceivedHandler> {

	private static Type<SvgReceivedHandler> TYPE;

	public static Type<SvgReceivedHandler> getType() {
		if (TYPE == null) {
			TYPE = new Type<SvgReceivedHandler>();
		}
		return TYPE;
	}

	private final SvgMessage svgMessage;

	public SvgReceivedEvent(final SvgMessage svgMessage) {
		this.svgMessage = svgMessage;
	}

	public SvgMessage getSvgMessage() {
		return svgMessage;
	}

	@Override
	public final Type<SvgReceivedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SvgReceivedHandler handler) {
		handler.onSvgReceived(this);
	}
}