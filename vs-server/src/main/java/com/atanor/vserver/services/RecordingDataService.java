package com.atanor.vserver.services;

import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.List;

import com.atanor.vserver.domain.entity.Recording;

public interface RecordingDataService {

	List<Recording> getRecordings();

	void removeRecordings(List<Recording> recordings);

	List<Recording> getSynchronizationInfo();
	
	Long createRecording(String fileName, Date startTime);
	
	void updateDuration(Long recordingId, Date endTime);

	void saveSnapshot(Long recordingId, BufferedImage image);
}
