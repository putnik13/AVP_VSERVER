package com.atanor.vserver.servlet;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atanor.vserver.common.rpc.dto.PresentationDto;
import com.atanor.vserver.common.rpc.services.PresentationService;
import com.atanor.vserver.domain.converter.PresentationConverter;
import com.atanor.vserver.facades.PlayerFacade;
import com.atanor.vserver.services.PresentationDataService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@Singleton
@SuppressWarnings("serial")
public class PresentationServlet extends RemoteServiceServlet implements PresentationService {

	private static final Logger LOG = LoggerFactory.getLogger(PresentationServlet.class);

	@Inject
	private PresentationDataService presentationService;

	@Inject
	private PresentationConverter converter;

	@Inject
	private PlayerFacade player;
	
	@Override
	public List<PresentationDto> getPresentations() {
		return converter.toListDto(presentationService.getPresentations());
	}

	@Override
	public Boolean startPresentation() {
		LOG.info("Presentation session started..");
		player.startPresentation();
		return Boolean.TRUE;
	}

	@Override
	public Boolean stopPresentation() {
		LOG.info("Presentation session stopped..");
		player.stopPresentation();
		return Boolean.TRUE;
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

	@Override
	public String getSnapshot() {
		// TODO Auto-generated method stub
		return null;
	}

}
