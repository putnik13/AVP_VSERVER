package com.atanor.vserver.services.impl;

import java.util.List;

import javax.inject.Inject;

import com.atanor.vserver.domain.dao.RecordingDao;
import com.atanor.vserver.domain.entity.Recording;
import com.atanor.vserver.services.RecordingDataService;

public class RecordingDataServiceImpl implements RecordingDataService {

	@Inject
	private RecordingDao recordingDao;

	@Override
	public List<Recording> getRecordings() {
		return recordingDao.findAll();
	}

}
