package com.atanor.vserver.vsadmin.client.ui.presenters;

import javax.inject.Inject;

import com.atanor.vserver.common.rpc.dto.VsConfigDto;
import com.atanor.vserver.common.rpc.services.ConfigServiceAsync;
import com.atanor.vserver.vsadmin.client.ui.sections.EditConfigurationSection;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;

public class EditConfigurationPresenter {

	@Inject
	private ConfigServiceAsync configService;

	private EditConfigurationSection view;

	@Inject
	public EditConfigurationPresenter(final EditConfigurationSection view) {
		this.view = view;
		view.setPresenter(this);
	}

	public void refreshConfiguration() {
		configService.getDefaultConfig(new AsyncCallback<VsConfigDto>() {

			@Override
			public void onFailure(Throwable caught) {
				SC.say("Error. Can not read configuration data");
			}

			@Override
			public void onSuccess(VsConfigDto config) {
				view.setConfiguration(config);
			}
		});
	}

	public void updateConfiguration(final VsConfigDto config) {
		configService.update(config, new AsyncCallback<VsConfigDto>() {

			@Override
			public void onFailure(Throwable caught) {
				SC.say("Error. Can not update configuration data");
			}

			@Override
			public void onSuccess(VsConfigDto config) {
				view.setConfiguration(config);
			}
		});
	}
}
