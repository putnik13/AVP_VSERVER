package com.atanor.vserver.facades;

import java.awt.image.BufferedImage;

public interface SnapshotGrabber {

	void start(String mediaSource);

	void stop();

	boolean isPlaying();

	BufferedImage grab();
}
