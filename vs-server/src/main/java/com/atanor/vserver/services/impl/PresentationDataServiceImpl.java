package com.atanor.vserver.services.impl;

import java.util.List;

import javax.inject.Inject;

import com.atanor.vserver.domain.dao.PresentationDao;
import com.atanor.vserver.domain.entity.Presentation;
import com.atanor.vserver.services.PresentationDataService;

public class PresentationDataServiceImpl implements PresentationDataService {

	@Inject
	private PresentationDao presentationDao;

	@Override
	public List<Presentation> getPresentations() {
		return presentationDao.findAll();
	}

}
