package com.atanor.vserver.facades.player;

import java.util.Map;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.atanor.vserver.facades.VideoRecorder;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

public class VlcRecorder implements VideoRecorder {

	private final MediaPlayer player;

	public VlcRecorder(final String installPath) {
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), installPath);
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
		player = new MediaPlayerFactory().newEmbeddedMediaPlayer();
	}

	@Override
	public void startRecording(final String media, final Map<String, Object> params) {
		if (isPlaying()) {
			return;
		}
		final String[] options = { (String) params.get("options") };
		player.playMedia(media, options);
	}

	@Override
	public void stopRecording() {
		player.stop();
	}

	@Override
	public boolean isPlaying() {
		return player.isPlaying();
	}

}
