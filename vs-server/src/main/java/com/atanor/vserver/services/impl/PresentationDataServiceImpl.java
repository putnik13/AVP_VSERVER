package com.atanor.vserver.services.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atanor.vserver.async.AsyncConnector;
import com.atanor.vserver.domain.dao.PresentationDao;
import com.atanor.vserver.domain.entity.Presentation;
import com.atanor.vserver.events.PresentationSnapshotEvent;
import com.atanor.vserver.services.ConfigDataService;
import com.atanor.vserver.services.PresentationDataService;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.persist.Transactional;

public class PresentationDataServiceImpl implements PresentationDataService {

	private static final Logger LOG = LoggerFactory.getLogger(PresentationDataServiceImpl.class);

	@Inject
	private PresentationDao presentationDao;

	@Inject
	private ConfigDataService configService;

	@Inject
	public PresentationDataServiceImpl(final EventBus eventBus) {
		eventBus.register(this);
	}

	@Override
	public List<Presentation> getPresentations() {
		return presentationDao.findAll();
	}

	@Transactional
	@Override
	public void removePresentations(final List<Presentation> presentations) {
		for (final Presentation presentation : presentations) {
			final Presentation presentationFromDb = presentationDao.find(presentation.getId());
			presentationDao.delete(presentationFromDb);
			removeFromDisk(presentation.getName());
		}
	}

	private void removeFromDisk(final String name) {
		final File file = new File(buildPresentationPath(name));
		if (file.exists()) {
			file.delete();
		}
	}

	private String buildPresentationPath(final String presentationName) {
		return configService.getConfig().getPresentationsOutput() + "/" + presentationName;
	}

	@Override
	public List<Presentation> getSynchronizationInfo() {
		final List<Presentation> presentations = presentationDao.findAll();
		for (final Presentation presentation : presentations) {
			checkOutdated(presentation);
		}
		return presentations;
	}

	private void checkOutdated(final Presentation presentation) {
		final File file = new File(buildPresentationPath(presentation.getName()));
		if (!file.exists()) {
			presentation.setOutdated(Boolean.TRUE);
		}
	}

	@Subscribe
	public void onSnapshotTaken(final PresentationSnapshotEvent event) {
		AsyncConnector.broadcastSnapshot(event.getSnapshot());
		saveSnapshot(event.getImage(), event.getFileName());
	}

	private void saveSnapshot(final BufferedImage image, final String fileName) {
		final File outputfile = new File(fileName);
		try {
			ImageIO.write(image, "png", outputfile);
		} catch (IOException e) {
			LOG.error("Error during attempt to write presentation snapshot file", e);
		}
	}

}
