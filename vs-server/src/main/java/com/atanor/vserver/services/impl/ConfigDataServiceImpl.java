package com.atanor.vserver.services.impl;

import java.util.List;

import javax.inject.Inject;

import com.atanor.vserver.domain.dao.VsConfigDao;
import com.atanor.vserver.domain.entity.VsConfig;
import com.atanor.vserver.services.ConfigDataService;

public class ConfigDataServiceImpl implements ConfigDataService {

	private static final String APP_CONFIG = "APPCONFIG";
	private static final String DEFAULT_CONFIG = "DEFAULTCONFIG";
	
	@Inject
	private VsConfigDao configDao;

	@Override
	public VsConfig getConfig() {
		final List<VsConfig> configs = configDao.findAll();
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

	@Override
	public void applyDefaultConfig() {
		final VsConfig currentCfg = getConfig();
		final VsConfig defaultCfg = getDefaultConfig();
		
		currentCfg.setAddLogo(defaultCfg.getAddLogo());
		currentCfg.setPalantirPort(defaultCfg.getPalantirPort());
		currentCfg.setPalantirUrl(defaultCfg.getPalantirUrl());
		currentCfg.setPlayerInstallPath(defaultCfg.getPlayerInstallPath());
		currentCfg.setRecordingsOutput(defaultCfg.getRecordingsOutput());
		currentCfg.setRecordingSnapshotOutput(defaultCfg.getRecordingSnapshotOutput());
		currentCfg.setPresentationsOutput(defaultCfg.getPresentationsOutput());
		currentCfg.setPresentationSnapshotOutput(defaultCfg.getPresentationSnapshotOutput());
		currentCfg.setStreamMediaOptions(defaultCfg.getStreamMediaOptions());
		currentCfg.setStreamMediaResource(defaultCfg.getStreamMediaResource());
		currentCfg.setConfMediaOptions(defaultCfg.getConfMediaOptions());
		currentCfg.setConfMediaResource(defaultCfg.getConfMediaResource());
		
		configDao.update(currentCfg);
	}
	
	public VsConfig getDefaultConfig() {
		final List<VsConfig> configs = configDao.findAll();
		for (VsConfig config : configs) {
			if (DEFAULT_CONFIG.equals(config.getName())) {
				return config;
			}
		}

		throw new IllegalStateException("Application default configuration is not found.");
	}

}
