package com.atanor.vserver.services;

import java.util.List;

import com.atanor.vserver.common.entity.Snapshot;
import com.atanor.vserver.domain.entity.Presentation;

public interface PresentationDataService {

	List<Presentation> getPresentations();

	void removePresentations(List<Presentation> presentations);

	List<Presentation> getSynchronizationInfo();
	
	void saveAndGeneratePdf(String fileName, List<Snapshot> snapshots);
}
