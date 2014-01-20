package com.atanor.vserver.common.async.events;

import com.atanor.vserver.common.entity.SvgMessage;
import com.google.gwt.event.shared.GwtEvent;

public class SvgSendEvent extends GwtEvent<SvgSendHandler> {

	private static Type<SvgSendHandler> TYPE;

	public static Type<SvgSendHandler> getType() {
		if (TYPE == null) {
			TYPE = new Type<SvgSendHandler>();
		}
		return TYPE;
	}

	private final SvgMessage svgMessage;

	public SvgSendEvent(final SvgMessage svgMessage) {
		this.svgMessage = svgMessage;
	}

	public SvgMessage getSvgMessage() {
		return svgMessage;
	}

	@Override
	public final Type<SvgSendHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SvgSendHandler handler) {
		handler.onSvgSend(this);
	}
}