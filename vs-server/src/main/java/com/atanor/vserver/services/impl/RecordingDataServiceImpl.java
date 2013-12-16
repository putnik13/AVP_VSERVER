package com.atanor.vserver.services.impl;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import com.atanor.vserver.domain.dao.RecordingDao;
import com.atanor.vserver.domain.entity.Recording;
import com.atanor.vserver.services.ConfigDataService;
import com.atanor.vserver.services.RecordingDataService;
import com.google.inject.persist.Transactional;

public class RecordingDataServiceImpl implements RecordingDataService {

	@Inject
	private RecordingDao recordingDao;
	
	@Inject
	private ConfigDataService configService;
	
	@Override
	public List<Recording> getRecordings() {
		return recordingDao.findAll();
	}

	@Transactional
	@Override
	public void removeRecordings(final List<Recording> recordings) {
		for (final Recording recording : recordings) {
			final Recording recordingFromDb = recordingDao.find(recording.getId());
			recordingDao.delete(recordingFromDb);
			removeFromDisk(recording.getName());
		}
	}

	private void removeFromDisk(final String name) {
		final File file = new File(buildRecordingPath(name));
		if (file.exists()) {
			file.delete();
		}
	}
	
	private String buildRecordingPath(final String recordingName) {
		return configService.getDefaultConfig().getRecordingsOutput() + "/" + recordingName;
	}
	
	@Override
	public List<Recording> getSynchronizationInfo() {
		final List<Recording> recordings = recordingDao.findAll();
		for (final Recording recording : recordings) {
			checkOutdated(recording);
		}
		return recordings;
	}
	
	private void checkOutdated(final Recording recording) {
		final File file = new File(buildRecordingPath(recording.getName()));
		if (!file.exists()) {
			recording.setOutdated(Boolean.TRUE);
		}
	}

}
