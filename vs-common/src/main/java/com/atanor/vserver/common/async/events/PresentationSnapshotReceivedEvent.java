package com.atanor.vserver.common.async.events;

import com.atanor.vserver.common.entity.Snapshot;
import com.google.gwt.event.shared.GwtEvent;

public class PresentationSnapshotReceivedEvent extends GwtEvent<PresentationSnapshotReceivedHandler> {

	private static Type<PresentationSnapshotReceivedHandler> TYPE;

	public static Type<PresentationSnapshotReceivedHandler> getType() {
		if (TYPE == null) {
			TYPE = new Type<PresentationSnapshotReceivedHandler>();
		}
		return TYPE;
	}

	private final Snapshot snapshot;
	
	public PresentationSnapshotReceivedEvent(final Snapshot snapshot){
		this.snapshot = snapshot;
	}
	
	public Snapshot getSnapshot() {
		return snapshot;
	}

	@Override
	public final Type<PresentationSnapshotReceivedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PresentationSnapshotReceivedHandler handler) {
		handler.onPresentationSnapshotReceived(this);
	}
}