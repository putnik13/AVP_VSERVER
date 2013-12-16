package com.atanor.vserver.vsadmin.client.ui.presenters;

import java.util.List;

import javax.inject.Inject;

import com.atanor.vserver.common.rpc.dto.RecordingDto;
import com.atanor.vserver.common.rpc.services.RecordingServiceAsync;
import com.atanor.vserver.vsadmin.client.ui.sections.StreamControlSection;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;

public class StreamControlPresenter {

	@Inject
	private RecordingServiceAsync recordingService;

	private StreamControlSection view;

	@Inject
	public StreamControlPresenter(final StreamControlSection view) {
		this.view = view;
		view.setPresenter(this);
	}

	public void refreshRecordings() {
		recordingService.getRecordings(new AsyncCallback<List<RecordingDto>>() {

			@Override
			public void onFailure(Throwable caught) {
				SC.say("Error. Can not read recordings data");
			}

			@Override
			public void onSuccess(List<RecordingDto> result) {
				view.setRecordings(result);
			}
		});
	}

	public void getSynchronizationInfo() {
		recordingService.getSynchronizationInfo(new AsyncCallback<List<RecordingDto>>() {

			@Override
			public void onFailure(Throwable caught) {
				SC.say("Error. Can not synchronize recordings data");
			}

			@Override
			public void onSuccess(List<RecordingDto> result) {
				view.onSynchronizationComplete(result);
			}
		});
	}

	public void removeRecordings(final List<RecordingDto> recordings) {
		recordingService.removeRecordings(recordings, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				SC.say("Error. Can not remove selected recordings");
			}

			@Override
			public void onSuccess(Boolean result) {
				refreshRecordings();
			}
		});
	}
}
