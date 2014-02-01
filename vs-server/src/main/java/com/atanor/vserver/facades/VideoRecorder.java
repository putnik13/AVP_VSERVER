package com.atanor.vserver.facades;

import java.util.Map;

public interface VideoRecorder {

	void startRecording(String media, Map<String, Object> params);

	void stopRecording();

	boolean isPlaying();
}
