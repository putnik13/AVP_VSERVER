package com.atanor.vserver.vsadmin.client.ui.presenters;

import java.util.List;

import javax.inject.Inject;

import com.atanor.vserver.common.rpc.dto.RecordingDto;
import com.atanor.vserver.common.rpc.services.RecordingServiceAsync;
import com.atanor.vserver.vsadmin.client.events.GetStreamSnapshotEvent;
import com.atanor.vserver.vsadmin.client.events.GetStreamSnapshotHandler;
import com.atanor.vserver.vsadmin.client.ui.sections.StreamControlSection;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;
import com.smartgwt.client.util.SC;

public class StreamControlPresenter implements GetStreamSnapshotHandler {

	private static final int GET_SNAPSHOT_INTERVAL = 5000;

	@Inject
	private RecordingServiceAsync recordingService;
	
	@Inject
	private EventBus eventBus;

	private Timer snapshotTimer;
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

	public void startRecording() {
		recordingService.startRecording(new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				SC.say("Error. Can not start recording");
			}

			@Override
			public void onSuccess(Boolean result) {
				view.onRecordingStarted();
				startGettingSnapshots();
			}
		});
	}

	private void startGettingSnapshots() {
		snapshotTimer = new Timer() {
			public void run() {
				eventBus.fireEvent(new GetStreamSnapshotEvent());
			}
		};
		snapshotTimer.scheduleRepeating(GET_SNAPSHOT_INTERVAL);
	}

	public void stopRecording() {
		recordingService.stopRecording(new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				snapshotTimer.cancel();
				SC.say("Error. Can not stop recording");
			}

			@Override
			public void onSuccess(Boolean result) {
				snapshotTimer.cancel();
				view.onRecordingStopped();
				refreshRecordings();
			}
		});
	}

	@Override
	public void onGetStreamSnapshot(GetStreamSnapshotEvent event) {
		recordingService.getSnapshot(new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				snapshotTimer.cancel();
				SC.say("Error. Can not get stream snapshot");
			}

			@Override
			public void onSuccess(String snapshot) {
				if (snapshot != null) {
					view.setSnapshot(snapshot);
				}
			}
		});
		
	}
}
