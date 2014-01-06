package com.atanor.vserver.events;

import java.awt.image.BufferedImage;

import com.atanor.vserver.common.entity.Snapshot;

public class PresentationSnapshotEvent {

	private final BufferedImage image;
	private final Snapshot snapshot;

	public PresentationSnapshotEvent(final BufferedImage image, final Snapshot snapshot) {
		this.image = image;
		this.snapshot = snapshot;
	}

	public Snapshot getSnapshot() {
		return snapshot;
	}

	public BufferedImage getImage() {
		return image;
	}

}
