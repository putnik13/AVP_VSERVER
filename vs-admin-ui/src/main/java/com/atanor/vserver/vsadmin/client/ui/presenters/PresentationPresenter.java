package com.atanor.vserver.vsadmin.client.ui.presenters;

import java.util.List;

import javax.inject.Inject;

import com.atanor.vserver.common.async.events.PresentationSnapshotReceivedEvent;
import com.atanor.vserver.common.async.events.PresentationSnapshotReceivedHandler;
import com.atanor.vserver.common.rpc.dto.PresentationDto;
import com.atanor.vserver.common.rpc.services.PresentationServiceAsync;
import com.atanor.vserver.vsadmin.client.ui.sections.PresentationSection;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;

public class PresentationPresenter implements PresentationSnapshotReceivedHandler {

	@Inject
	private PresentationServiceAsync presentationService;

	private PresentationSection view;

	@Inject
	public PresentationPresenter(final PresentationSection view) {
		this.view = view;
		view.setPresenter(this);
	}

	public void refreshPresentations() {
		presentationService.getPresentations(new AsyncCallback<List<PresentationDto>>() {

			@Override
			public void onFailure(Throwable caught) {
				SC.say("Error. Can not read presentations data");
			}

			@Override
			public void onSuccess(List<PresentationDto> result) {
				view.setPresentations(result);
			}
		});
	}

	public void getSynchronizationInfo() {
		presentationService.getSynchronizationInfo(new AsyncCallback<List<PresentationDto>>() {

			@Override
			public void onFailure(Throwable caught) {
				SC.say("Error. Can not synchronize presentations data");
			}

			@Override
			public void onSuccess(List<PresentationDto> result) {
				view.onSynchronizationComplete(result);
			}
		});
	}

	public void removePresentations(final List<PresentationDto> presentations) {
		presentationService.removePresentations(presentations, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				SC.say("Error. Can not remove selected presentations");
			}

			@Override
			public void onSuccess(Boolean result) {
				refreshPresentations();
			}
		});
	}

	public void startPresentation() {
		presentationService.startPresentation(new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				SC.say("Error. Can not start presentation");
			}

			@Override
			public void onSuccess(Boolean result) {
				view.onPresentationStarted();
			}
		});
	}

	public void stopPresentation() {
		presentationService.stopPresentation(new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				SC.say("Error. Can not stop presentation");
			}

			@Override
			public void onSuccess(Boolean result) {
				view.onPresentationStopped();
				refreshPresentations();
			}
		});
	}

	@Override
	public void onPresentationSnapshotReceived(PresentationSnapshotReceivedEvent event) {
		view.setSnapshot(event.getSnapshot());
	}

}
