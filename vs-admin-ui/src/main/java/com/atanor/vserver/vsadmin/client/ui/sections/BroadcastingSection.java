package com.atanor.vserver.vsadmin.client.ui.sections;

import java.util.LinkedHashMap;

import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;

public class BroadcastingSection extends BaseGridSection {

	private final IButton startStream;
	private final IButton previewStream;
	private final IButton stopStream;
	private final Canvas snapshotBox;

	public BroadcastingSection() {
		setPadding(20);

		snapshotBox = createSnapshotBox();

		previewStream = new IButton("Preview Stream");
		previewStream.setWidth(90);

		startStream = new IButton("Start Streaming");
		startStream.setWidth(90);

		stopStream = new IButton("Stop Streaming");
		stopStream.setWidth(90);
		stopStream.setDisabled(true);

		final LinkedHashMap<String, String> sourceSelector = new LinkedHashMap<String, String>();
		sourceSelector.put("streamOnline", "Online stream");
		sourceSelector.put("fileToStream", "From File");

		final RadioGroupItem input = new RadioGroupItem();
		input.setDefaultValue("streamOnline");
		input.setWidth(300);
		input.setVAlign(VerticalAlignment.TOP);
		input.setTitle("Input Source Selector");
		input.setShowTitle(true);
		input.setValueMap(sourceSelector);
		input.addChangedHandler(new ChangedHandler() {
			public void onChanged(ChangedEvent event) {
				System.out.println(event.getSource().toString());
			}
		});

		final HLayout headerPane = new HLayout();
		headerPane.setMembersMargin(10);
		headerPane.addMembers(snapshotBox, previewStream, startStream, stopStream);

		final DynamicForm controls = new DynamicForm();
		controls.setFields(input);

		final HLayout spacer = new HLayout();
		spacer.setHeight(40);

		addMembers(headerPane, new LayoutSpacer(), controls, spacer);
	}

}
