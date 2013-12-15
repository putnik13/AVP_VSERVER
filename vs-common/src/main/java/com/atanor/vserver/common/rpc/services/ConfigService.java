package com.atanor.vserver.common.rpc.services;

import com.atanor.vserver.common.rpc.dto.VsConfigDto;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the Recording Service.
 */
@RemoteServiceRelativePath("config")
public interface ConfigService extends RemoteService {

	VsConfigDto getDefaultConfig();

	VsConfigDto update(VsConfigDto configDto);
}
