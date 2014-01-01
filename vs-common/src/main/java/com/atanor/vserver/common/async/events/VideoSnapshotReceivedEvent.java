package com.atanor.vserver.common.async.events;

import com.atanor.vserver.common.entity.Snapshot;
import com.google.gwt.event.shared.GwtEvent;

public class VideoSnapshotReceivedEvent extends GwtEvent<VideoSnapshotReceivedHandler> {

	private static Type<VideoSnapshotReceivedHandler> TYPE;

	public static Type<VideoSnapshotReceivedHandler> getType() {
		if (TYPE == null) {
			TYPE = new Type<VideoSnapshotReceivedHandler>();
		}
		return TYPE;
	}

	private final Snapshot snapshot;

	public VideoSnapshotReceivedEvent(final Snapshot snapshot) {
		this.snapshot = snapshot;
	}

	public Snapshot getSnapshot() {
		return snapshot;
	}

	@Override
	public final Type<VideoSnapshotReceivedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(VideoSnapshotReceivedHandler handler) {
		handler.onVideoSnapshotReceived(this);
	}
}