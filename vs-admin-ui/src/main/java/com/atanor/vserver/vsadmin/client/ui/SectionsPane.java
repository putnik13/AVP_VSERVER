package com.atanor.vserver.vsadmin.client.ui;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import com.atanor.vserver.vsadmin.client.events.SectionAnimationStartedEvent;
import com.atanor.vserver.vsadmin.client.events.SectionSelectedEvent;
import com.atanor.vserver.vsadmin.client.events.SectionSelectedHandler;
import com.atanor.vserver.vsadmin.client.ui.sections.BaseSection;
import com.atanor.vserver.vsadmin.client.ui.sections.BroadcastingSection;
import com.atanor.vserver.vsadmin.client.ui.sections.ConfigurationSection;
import com.atanor.vserver.vsadmin.client.ui.sections.PresentationSection;
import com.atanor.vserver.vsadmin.client.ui.sections.RecordingSection;
import com.google.web.bindery.event.shared.EventBus;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.AnimationCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;

public class SectionsPane extends HLayout implements SectionSelectedHandler {

	private static final int ANIMATION_TIME = 500;

	@Inject
	private EventBus eventBus;

	private final RecordingSection recording;
	private final PresentationSection presentation;
	private final BroadcastingSection broadcasting;
	private final ConfigurationSection configuration;

	private final List<BaseSection> sections;

	private BaseSection selectedSection;
	private boolean isAnimationInProgress = false;

	@Inject
	public SectionsPane(final RecordingSection recording, final PresentationSection presentation,
			final BroadcastingSection broadcasting, final ConfigurationSection configuration) {
		this.recording = recording;
		this.presentation = presentation;
		this.broadcasting = broadcasting;
		this.configuration = configuration;

		this.sections = Arrays.asList(recording, presentation, broadcasting, configuration);

		setWidth100();
		setHeight100();

		final Canvas canvas = new Canvas();
		canvas.setWidth100();
		canvas.setHeight100();
		canvas.setOverflow(Overflow.HIDDEN);

		canvas.addChild(recording);
		canvas.addChild(presentation);
		canvas.addChild(broadcasting);
		canvas.addChild(configuration);

		setToFrontSection(recording);

		addMember(canvas);
	}

	@Override
	public void onSectionSelected(final SectionSelectedEvent event) {
		if (isAnimationInProgress) {
			return;
		}

		BaseSection newSelectedSection = null;
		switch (event.getSection()) {
		case RECORDING:
			newSelectedSection = recording;
			break;
		case PRESENTATION:
			newSelectedSection = presentation;
			break;
		case BROADCASTING:
			newSelectedSection = broadcasting;
			break;
		case CONFIGURATION:
			newSelectedSection = configuration;
			break;
		default:
			throw new IllegalStateException("Section not found " + event.getSection().name());
		}

		eventBus.fireEvent(new SectionAnimationStartedEvent(event.getSection()));
		move(newSelectedSection);
	}

	private void move(final BaseSection section) {
		isAnimationInProgress = true;
		section.animateMove(0, 0, null, ANIMATION_TIME);
		selectedSection.animateMove(0, section.getHeight(), new AnimationCallback() {

			@Override
			public void execute(boolean earlyFinish) {
				setToFrontSection(section);
				isAnimationInProgress = false;
			}

		}, 500);

	}

	private void setToFrontSection(final BaseSection selected) {
		for (BaseSection section : sections) {
			if (section != selected) {
				resetPosition(section);
			}
		}
		selected.bringToFront();
		this.selectedSection = selected;
	}

	private void resetPosition(final BaseSection section) {
		section.moveTo(0, -section.getHeight());
	}

}
