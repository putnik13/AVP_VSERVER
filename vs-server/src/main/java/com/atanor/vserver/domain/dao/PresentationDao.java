package com.atanor.vserver.domain.dao;

import com.atanor.vserver.domain.entity.Presentation;

public class PresentationDao extends GenericDaoImpl<Presentation, Long> {

	@Override
	public Class<Presentation> getEntityClass() {
		return Presentation.class;
	}

}
