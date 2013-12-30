package com.atanor.vserver.services.impl;

import java.util.List;

import javax.inject.Inject;

import com.atanor.vserver.domain.dao.VsConfigDao;
import com.atanor.vserver.domain.entity.VsConfig;
import com.atanor.vserver.services.ConfigDataService;

public class ConfigDataServiceImpl implements ConfigDataService {

	private static final String APP_CONFIG = "APPCONFIG";
	
	@Inject
	private VsConfigDao configDao;

	@Override
	public VsConfig getConfig() {
		List<VsConfig> configs = configDao.findAll();
		for (VsConfig config : configs) {
			if (APP_CONFIG.equals(config.getName())) {
				return config;
			}
		}

		throw new IllegalStateException("Application configuration is not found.");
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
