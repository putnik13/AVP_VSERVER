package com.atanor.vserver.vsadmin.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class GetStreamSnapshotEvent extends GwtEvent<GetStreamSnapshotHandler> {

	private static Type<GetStreamSnapshotHandler> TYPE;

	public static Type<GetStreamSnapshotHandler> getType() {
		if (TYPE == null) {
			TYPE = new Type<GetStreamSnapshotHandler>();
		}
		return TYPE;
	}

	@Override
	public final Type<GetStreamSnapshotHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(GetStreamSnapshotHandler handler) {
		handler.onGetStreamSnapshot(this);
	}
}