package com.atanor.vserver.services.impl;

import java.util.List;

import javax.inject.Inject;

import com.atanor.vserver.domain.dao.VsConfigDao;
import com.atanor.vserver.domain.entity.VsConfig;
import com.atanor.vserver.services.ConfigDataService;

public class ConfigDataServiceImpl implements ConfigDataService {

	@Inject
	private VsConfigDao configDao;

	@Override
	public VsConfig getDefaultConfig() {
		List<VsConfig> configs = configDao.findAll();
		for (VsConfig config : configs) {
			if (config.isDefault()) {
				return config;
			}
		}

		throw new IllegalStateException("Default configuration is not set.");
	}

	@Override
	public VsConfig getConfigById(Long id) {
		return configDao.find(id);
	}

	@Override
	public void update(VsConfig config) {
		configDao.update(config);
	}

}
