package com.atanor.vserver.events;

import java.awt.image.BufferedImage;

import com.atanor.vserver.common.entity.Snapshot;

public class PresentationSnapshotEvent {

	private final BufferedImage image;
	private final String fileName;
	private final Snapshot snapshot;

	public PresentationSnapshotEvent(final BufferedImage image, final String fileName, final Snapshot snapshot) {
		this.image = image;
		this.fileName = fileName;
		this.snapshot = snapshot;
	}

	public Snapshot getSnapshot() {
		return snapshot;
	}

	public BufferedImage getImage() {
		return image;
	}

	public String getFileName() {
		return fileName;
	}

}
