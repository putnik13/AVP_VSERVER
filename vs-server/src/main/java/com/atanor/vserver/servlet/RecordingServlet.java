package com.atanor.vserver.servlet;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atanor.vserver.common.rpc.dto.RecordingDto;
import com.atanor.vserver.common.rpc.services.RecordingService;
import com.atanor.vserver.domain.converter.RecordingConverter;
import com.atanor.vserver.facades.PalantirFacade;
import com.atanor.vserver.facades.PlayerFacade;
import com.atanor.vserver.facades.palantir.PalantirCommand;
import com.atanor.vserver.services.RecordingDataService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@Singleton
@SuppressWarnings("serial")
public class RecordingServlet extends RemoteServiceServlet implements RecordingService {

	private static final Logger LOG = LoggerFactory.getLogger(RecordingServlet.class);
	
	@Inject
	private RecordingDataService recordingsService;

	@Inject
	private RecordingConverter converter;

	@Inject
	private PalantirFacade palantir;
	
	@Inject
	private PlayerFacade player;
	
	@Override
	public List<RecordingDto> getRecordings() {
		return converter.toListDto(recordingsService.getRecordings());
	}

	@Override
	public Boolean startRecording() {
		LOG.info("Stream recording started..");
		palantir.send(PalantirCommand.START_RECORDING);
		player.startRecording();
		return Boolean.TRUE;
	}

	@Override
	public Boolean stopRecording() {
		LOG.info("Stream recording stopped..");
		palantir.send(PalantirCommand.STOP_RECORDING);
		player.stopRecording();
		return Boolean.TRUE;
	}

	@Override
	public Boolean removeRecordings(List<RecordingDto> recordings) {
		recordingsService.removeRecordings(converter.toListEntities(recordings));
		return Boolean.TRUE;
	}

	@Override
	public List<RecordingDto> getSynchronizationInfo() {
		return converter.toListDto(recordingsService.getSynchronizationInfo());
	}

	@Override
	public String getSnapshot() {
		return player.getSnapshot();
	}

}
