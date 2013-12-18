package com.atanor.vserver.common.rpc.services;

import java.util.List;

import com.atanor.vserver.common.rpc.dto.PresentationDto;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the Presentation Service.
 */
@RemoteServiceRelativePath("presentation")
public interface PresentationService extends RemoteService {

	Boolean startPresentation();

	Boolean stopPresentation();

	List<PresentationDto> getPresentations();

	Boolean removePresentations(List<PresentationDto> recordings);

	List<PresentationDto> getSynchronizationInfo();
	
	String getSnapshot();
}
