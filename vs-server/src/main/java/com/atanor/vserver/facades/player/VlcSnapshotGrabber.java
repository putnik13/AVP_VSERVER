package com.atanor.vserver.facades.player;

import java.awt.image.BufferedImage;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.atanor.vserver.facades.SnapshotGrabber;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

public class VlcSnapshotGrabber implements SnapshotGrabber {

	private static final String[] VLC_ARGS = {
		"--intf", "dummy", // no interface
		"--vout", "dummy", // no video output
		"--no-audio", // no audio decoding
		"--no-video-title-show", // do not display title
		"--no-stats", // no stats
		"--no-sub-autodetect-file", // no subtitles
		"--no-snapshot-preview", // nosnapshot preview
		"--live-caching=50", // reduce capture lag/latency
		"--quiet", // turn off VLC warnings 
	};
	
	private final MediaPlayer player;
	
	private final int width;
	private final int height;
	
	public VlcSnapshotGrabber(final String installPath){
		this(installPath, 0, 0);
	}
	
	public VlcSnapshotGrabber(final String installPath, final int width, final int height) {
		this.width = width;
		this.height = height;
		
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), installPath);
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
		player = new MediaPlayerFactory(VLC_ARGS).newHeadlessMediaPlayer();
	}

	@Override
	public void start(String mediaSource) {
		if(isPlaying()){
			return;
		}
		player.startMedia(mediaSource);
	}

	@Override
	public void stop() {
		player.stop();
	}

	@Override
	public boolean isPlaying() {
		return player.isPlaying();
	}

	@Override
	public BufferedImage grab() {
		final boolean isResize = (width != 0) || (height != 0);
		return isResize ? player.getSnapshot(width, height) : player.getSnapshot();
	}

}
