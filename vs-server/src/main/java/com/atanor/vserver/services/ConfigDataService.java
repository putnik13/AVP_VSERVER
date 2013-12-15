package com.atanor.vserver.services;

import com.atanor.vserver.domain.entity.VsConfig;

public interface ConfigDataService {

	VsConfig getDefaultConfig();

	VsConfig getConfigById(Long id);

	void update(VsConfig config);
}
