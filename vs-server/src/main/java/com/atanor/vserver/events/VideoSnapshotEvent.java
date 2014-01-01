package com.atanor.vserver.events;

import com.atanor.vserver.common.entity.Snapshot;

public class VideoSnapshotEvent {

	private final Snapshot snapshot;

	public VideoSnapshotEvent(final Snapshot snapshot) {
		this.snapshot = snapshot;
	}

	public Snapshot getSnapshot() {
		return snapshot;
	}

}
