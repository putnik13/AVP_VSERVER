package com.atanor.vserver.facades;

public interface PlayerFacade {
	
	void startRecording();

	void stopRecording();	
	
	String getSnapshot();
	
	void startPresentation();
	
	void stopPresentation();
}
