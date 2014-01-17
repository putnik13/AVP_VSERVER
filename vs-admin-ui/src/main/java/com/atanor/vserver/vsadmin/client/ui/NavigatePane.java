package com.atanor.vserver.vsadmin.client.ui;

import javax.inject.Inject;

import com.atanor.vserver.vsadmin.client.events.SectionAnimationStartedEvent;
import com.atanor.vserver.vsadmin.client.events.SectionAnimationStartedHandler;
import com.atanor.vserver.vsadmin.client.events.SectionSelectedEvent;
import com.atanor.vserver.vsadmin.client.ui.sections.Section;
import com.google.web.bindery.event.shared.EventBus;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.MouseOutEvent;
import com.smartgwt.client.widgets.events.MouseOutHandler;
import com.smartgwt.client.widgets.events.MouseOverEvent;
import com.smartgwt.client.widgets.events.MouseOverHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;

public class NavigatePane extends HLayout implements SectionAnimationStartedHandler {

	@Inject
	private EventBus eventBus;

	final Canvas streamControl;
	final Canvas shareConference;
	final Canvas broadcasting;
	final Canvas editConfiguration;

	public NavigatePane() {
		setWidth(150);
		setHeight100();
		setBackgroundColor("grey");

		streamControl = createNavigateControl(Section.RECORDING, "stream_control.png", "Stream Control");
		streamControl.setDisabled(true);
		shareConference = createNavigateControl(Section.PRESENTATION, "share_conference.png", "Share Conference");
		broadcasting = createNavigateControl(Section.BROADCASTING, "broadcasting.png", "Broadcasting");
		editConfiguration = createNavigateControl(Section.CONFIGURATION, "edit_config.png", "Edit Configuration");

		final VLayout controlsLayout = new VLayout();
		controlsLayout.setWidth100();
		controlsLayout.setHeight100();
		controlsLayout.setMembersMargin(10);
		controlsLayout.setDefaultLayoutAlign(Alignment.CENTER);

		controlsLayout.addMembers(new LayoutSpacer(), streamControl, shareConference, broadcasting, editConfiguration,
				new LayoutSpacer());

		addMember(controlsLayout);
	}

	private ClickHandler buildClickHandler(final Section section) {
		return new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new SectionSelectedEvent(section));
			}
		};
	}

	private void enableAllControls() {
		resetControl(streamControl);
		resetControl(shareConference);
		resetControl(broadcasting);
		resetControl(editConfiguration);
	}

	private void resetControl(final Canvas control) {
		control.enable();
		control.setWidth(UiUtils.NAVIGATE_ICON_CONTROL_SIZE);
		control.setHeight(UiUtils.NAVIGATE_ICON_CONTROL_SIZE);
	}

	@Override
	public void onAnimationStarted(final SectionAnimationStartedEvent event) {
		enableAllControls();
		switch (event.getSection()) {
		case RECORDING:
			streamControl.disable();
			break;
		case PRESENTATION:
			shareConference.disable();
			break;
		case BROADCASTING:
			broadcasting.disable();
			break;
		case CONFIGURATION:
			editConfiguration.disable();
			break;
		default:
			throw new IllegalStateException("Section not found " + event.getSection().name());
		}
	}

	private Canvas createNavigateControl(final Section section, final String imgSource, final String tooltip) {
		final Img img = new Img();
		img.setSrc(imgSource);
		img.setCursor(Cursor.HAND);
		img.setTooltip(tooltip);
		img.setWidth(UiUtils.NAVIGATE_ICON_CONTROL_SIZE);
		img.setHeight(UiUtils.NAVIGATE_ICON_CONTROL_SIZE);
		img.addClickHandler(buildClickHandler(section));
		img.addMouseOverHandler(new MouseOverHandler() {

			@Override
			public void onMouseOver(MouseOverEvent event) {
				img.animateResize(UiUtils.NAVIGATE_ICON_CONTROL_SIZE_HOVER, UiUtils.NAVIGATE_ICON_CONTROL_SIZE_HOVER,
						null, 100);
			}
		});
		img.addMouseOutHandler(new MouseOutHandler() {

			@Override
			public void onMouseOut(MouseOutEvent event) {
				img.animateResize(UiUtils.NAVIGATE_ICON_CONTROL_SIZE, UiUtils.NAVIGATE_ICON_CONTROL_SIZE);
			}
		});

		return img;
	}
}