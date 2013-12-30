package com.atanor.vserver.services.impl;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import com.atanor.vserver.domain.dao.PresentationDao;
import com.atanor.vserver.domain.entity.Presentation;
import com.atanor.vserver.services.ConfigDataService;
import com.atanor.vserver.services.PresentationDataService;
import com.google.inject.persist.Transactional;

public class PresentationDataServiceImpl implements PresentationDataService {

	@Inject
	private PresentationDao presentationDao;

	@Inject
	private ConfigDataService configService;

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

}
