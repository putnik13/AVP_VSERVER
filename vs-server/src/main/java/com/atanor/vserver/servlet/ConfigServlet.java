package com.atanor.vserver.servlet;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.atanor.vserver.common.rpc.dto.VsConfigDto;
import com.atanor.vserver.common.rpc.services.ConfigService;
import com.atanor.vserver.domain.converter.ConfigConverter;
import com.atanor.vserver.services.ConfigDataService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@Singleton
@SuppressWarnings("serial")
public class ConfigServlet extends RemoteServiceServlet implements ConfigService {

	@Inject
	private ConfigDataService configService;

	@Inject
	private ConfigConverter converter;

	@Override
	public VsConfigDto getDefaultConfig() {
		return converter.toDto(configService.getDefaultConfig());
	}

	@Override
	public VsConfigDto update(final VsConfigDto configDto) {
		configService.update(converter.toEntity(configDto));
		return converter.toDto(configService.getConfigById(configDto.getId()));
	}

}
