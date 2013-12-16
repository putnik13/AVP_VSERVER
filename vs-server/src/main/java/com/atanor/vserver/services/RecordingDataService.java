package com.atanor.vserver.services;

import java.util.List;

import com.atanor.vserver.domain.entity.Recording;

public interface RecordingDataService {

	List<Recording> getRecordings();

	void removeRecordings(List<Recording> recordings);

	List<Recording> getSynchronizationInfo();
}
