package com.atanor.vserver.services;

import com.atanor.vserver.domain.entity.VsConfig;

public interface ConfigDataService {

	VsConfig getConfig();

	VsConfig getConfigById(Long id);

	void update(VsConfig config);
}
