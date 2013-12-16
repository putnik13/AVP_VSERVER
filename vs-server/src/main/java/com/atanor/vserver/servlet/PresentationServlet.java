package com.atanor.vserver.servlet;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.atanor.vserver.common.rpc.dto.PresentationDto;
import com.atanor.vserver.common.rpc.services.PresentationService;
import com.atanor.vserver.domain.converter.PresentationConverter;
import com.atanor.vserver.services.PresentationDataService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@Singleton
@SuppressWarnings("serial")
public class PresentationServlet extends RemoteServiceServlet implements PresentationService {

	@Inject
	private PresentationDataService presentationService;

	@Inject
	private PresentationConverter converter;

	@Override
	public List<PresentationDto> getPresentations() {
		return converter.toListDto(presentationService.getPresentations());
	}

	@Override
	public Boolean startPresentation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean stopPresentation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean removePresentations(List<PresentationDto> presentations) {
		presentationService.removePresentations(converter.toListEntities(presentations));
		return Boolean.TRUE;
	}

	@Override
	public List<PresentationDto> getSynchronizationInfo() {
		return converter.toListDto(presentationService.getSynchronizationInfo());
	}

}
