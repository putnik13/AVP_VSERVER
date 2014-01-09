package com.atanor.vserver.common.async.events;

import com.google.gwt.event.shared.GwtEvent;

public class PresentationOverEvent extends GwtEvent<PresentationOverHandler> {

	private static Type<PresentationOverHandler> TYPE;

	private final String pdfFileName;

	public PresentationOverEvent(final String pdfFileName) {
		this.pdfFileName = pdfFileName;
	}

	public String getPdfFileName() {
		return pdfFileName;
	}

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