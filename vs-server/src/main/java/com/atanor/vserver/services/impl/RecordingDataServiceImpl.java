package com.atanor.vserver.services.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;

import com.atanor.vserver.async.AsyncConnector;
import com.atanor.vserver.domain.dao.RecordingDao;
import com.atanor.vserver.domain.entity.Recording;
import com.atanor.vserver.events.VideoSnapshotEvent;
import com.atanor.vserver.services.ConfigDataService;
import com.atanor.vserver.services.RecordingDataService;
import com.atanor.vserver.util.FormatTime;
import com.atanor.vserver.util.ImageDecoder;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.persist.Transactional;

public class RecordingDataServiceImpl implements RecordingDataService {

	@Inject
	private RecordingDao recordingDao;

	@Inject
	private ConfigDataService configService;

	@Inject
	public RecordingDataServiceImpl(final EventBus eventBus) {
		eventBus.register(this);
	}

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
		return configService.getConfig().getRecordingsOutput() + "/" + recordingName;
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

	@Override
	public Long createRecording(final String fileName, final Date startTime) {
		Validate.notEmpty(fileName, "fileName can not be empty or null");
		Validate.notNull(startTime, "startTime can not be null");

		final Recording recording = new Recording(fileName);
		recording.setStartTime(startTime);
		return recordingDao.insert(recording);
	}

	@Override
	public void updateDuration(final Long recordingId, final Date endTime) {
		Validate.notNull(recordingId, "recordingId can not be null");
		Validate.notNull(endTime, "endTime can not be null");

		final Recording recording = recordingDao.find(recordingId);
		if (recording == null) {
			throw new IllegalStateException("Can not find recording with id=" + recordingId);
		}

		final String duration = FormatTime.format(endTime.getTime() - recording.getStartTime().getTime());
		recording.setDuration(duration);
		recording.setEndTime(endTime);
		recordingDao.update(recording);
	}

	@Override
	public void saveSnapshot(final Long recordingId, final BufferedImage image) {
		Validate.notNull(recordingId, "recordingId can not be null");
		Validate.notNull(image, "image can not be null");

		final Recording recording = recordingDao.find(recordingId);
		if (recording == null) {
			throw new IllegalStateException("Can not find recording with id=" + recordingId);
		}

		recording.setImageBlob(ImageDecoder.encodeImage(image));
		recordingDao.update(recording);
	}

	@Subscribe
	public void onSnapshotTaken(final VideoSnapshotEvent event) {
		AsyncConnector.broadcastSnapshot(event.getSnapshot());
	}
}
