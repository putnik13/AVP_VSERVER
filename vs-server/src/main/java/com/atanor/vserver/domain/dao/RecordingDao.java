package com.atanor.vserver.domain.dao;

import com.atanor.vserver.domain.entity.Recording;

public class RecordingDao extends GenericDaoImpl<Recording, Long> {

	@Override
	public Class<Recording> getEntityClass() {
		return Recording.class;
	}

}
