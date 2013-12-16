package com.atanor.vserver.vsadmin.client.ui.presenters;

import java.util.List;

import javax.inject.Inject;

import com.atanor.vserver.common.rpc.dto.PresentationDto;
import com.atanor.vserver.common.rpc.services.PresentationServiceAsync;
import com.atanor.vserver.vsadmin.client.ui.sections.ShareConferenceSection;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;

public class ShareConferencePresenter {

	@Inject
	private PresentationServiceAsync presentationService;

	private ShareConferenceSection view;

	@Inject
	public ShareConferencePresenter(final ShareConferenceSection view) {
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

}
