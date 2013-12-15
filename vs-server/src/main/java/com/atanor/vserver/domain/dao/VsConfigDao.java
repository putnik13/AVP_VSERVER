package com.atanor.vserver.domain.dao;

import com.atanor.vserver.domain.entity.VsConfig;

public class VsConfigDao extends GenericDaoImpl<VsConfig, Long> {

	@Override
	public Class<VsConfig> getEntityClass() {
		return VsConfig.class;
	}

}
