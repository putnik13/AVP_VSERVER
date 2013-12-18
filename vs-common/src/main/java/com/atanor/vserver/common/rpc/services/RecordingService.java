package com.atanor.vserver.common.rpc.services;

import java.util.List;

import com.atanor.vserver.common.rpc.dto.RecordingDto;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the Recording Service.
 */
@RemoteServiceRelativePath("recording")
public interface RecordingService extends RemoteService {

	Boolean startRecording();

	Boolean stopRecording();
	
	List<RecordingDto> getRecordings();
	
	Boolean removeRecordings(List<RecordingDto> recordings);
	
	List<RecordingDto> getSynchronizationInfo();
	
	String getSnapshot();
}
