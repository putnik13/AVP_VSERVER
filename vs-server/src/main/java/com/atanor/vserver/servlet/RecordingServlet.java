package com.atanor.vserver.servlet;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.atanor.vserver.common.rpc.dto.RecordingDto;
import com.atanor.vserver.common.rpc.services.RecordingService;
import com.atanor.vserver.domain.converter.RecordingConverter;
import com.atanor.vserver.services.RecordingDataService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@Singleton
@SuppressWarnings("serial")
public class RecordingServlet extends RemoteServiceServlet implements RecordingService {

	@Inject
	private RecordingDataService recordingsService;

	@Inject
	private RecordingConverter converter;

	@Override
	public List<RecordingDto> getRecordings() {
		return converter.toListDto(recordingsService.getRecordings());
	}

	@Override
	public Boolean startRecording() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean stopRecording() {
		// TODO Auto-generated method stub
		return null;
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

}
