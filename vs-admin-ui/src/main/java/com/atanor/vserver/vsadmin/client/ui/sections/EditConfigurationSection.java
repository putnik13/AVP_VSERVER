package com.atanor.vserver.vsadmin.client.ui.sections;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.events.ItemChangedHandler;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

public class EditConfigurationSection extends BaseSection {

	private final IButton editButton;
	private final IButton saveButton;
	private final IButton cancelButton;

	private final TextAreaItem mediaOptionsAreaItem;
	private final TextItem outputFolderItem;
	private final TextItem playerPathItem;
	private final TextItem palantirUrlItem;
	private final TextItem palantirPortItem;

	public EditConfigurationSection() {

		setPadding(20);

		final HLayout buttonLayout = new HLayout();
		buttonLayout.setWidth100();
		buttonLayout.setAlign(Alignment.LEFT);

		editButton = new IButton("Edit");
		editButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				setScreenEditable(true);
				setEditButtonSettings();
			}
		});

		saveButton = new IButton("Save");
		cancelButton = new IButton("Cancel");
		cancelButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				setScreenEditable(false);
				setDefaultButtonSettings();
			}
		});

		buttonLayout.addMembers(editButton, saveButton, cancelButton);
		setDefaultButtonSettings();

		final SectionStack sectionStack = new SectionStack();
		sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		sectionStack.setWidth100();
		sectionStack.setHeight100();

		final SectionStackSection streamControlSection = createSection("Stream Control settings");
		final SectionStackSection broadcastingSection = createSection("Broadcasting settings");
		final SectionStackSection conferenceSection = createSection("Conference settings");
		final SectionStackSection generalSection = createSection("General settings");

		final DynamicForm form = new DynamicForm();
		form.setWidth100();
		form.setHeight100();
		form.setPadding(20);
		form.setNumCols(4);
		form.setColWidths(200, "*", 200, "*");
		form.setCellPadding(5);
		form.addItemChangedHandler(new ItemChangedHandler() {

			@Override
			public void onItemChanged(ItemChangedEvent event) {
				saveButton.enable();
			}
		});

		mediaOptionsAreaItem = createTextAreaItem("Media Options");
		mediaOptionsAreaItem
				.setValue(":sout=#transcode{vcodec=h264,acodec=mpga,ab=128,channels=2,samplerate=44100}:std{access=file,mux=ts,dst=%s}");

		outputFolderItem = createTextItem("Output recordings folder");
		outputFolderItem.setValue("D:/tmp");

		playerPathItem = createTextItem("VLC installation path");
		playerPathItem.setValue("C:/Program Files/VideoLAN/VLC");

		palantirUrlItem = createTextItem("Palantir URL");
		palantirUrlItem.setValue("192.168.1.80");

		palantirPortItem = createTextItem("Palantir port");
		palantirPortItem.setValue("5050");
		palantirPortItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				final String newValue = (String) event.getValue();
				final FormItem item = event.getItem();
				item.setTextBoxStyle("5050".equals(newValue) ? "textItem" : "textItemModified");
			}
		});
		
		form.setFields(mediaOptionsAreaItem, outputFolderItem, playerPathItem, palantirUrlItem, palantirPortItem);

		final VLayout streamControlLayout = new VLayout();
		streamControlLayout.setWidth100();
		streamControlLayout.setPadding(20);

		streamControlLayout.addMember(form);
		streamControlSection.addItem(form);

		sectionStack.addSection(streamControlSection);
		sectionStack.addSection(broadcastingSection);
		sectionStack.addSection(conferenceSection);
		sectionStack.addSection(generalSection);

		setScreenEditable(false);
		
		addMembers(buttonLayout, sectionStack);
	}

	private SectionStackSection createSection(final String title) {
		final SectionStackSection section = new SectionStackSection(title);
		section.setCanCollapse(true);
		return section;
	}

	private TextAreaItem createTextAreaItem(final String title) {
		final TextAreaItem item = new TextAreaItem();
		item.setTitle(title);
		item.setColSpan(4);
		item.setLength(5000);
		item.setWidth("*");
		item.setTitleStyle("formTitleVs");
		item.setTextBoxStyle("textItemReadOnly");
		return item;
	}

	private TextItem createTextItem(final String title) {
		final TextItem item = new TextItem();
		item.setTitle(title);
		item.setWidth("*");
		item.setTitleStyle("formTitleVs");
		item.setTextBoxStyle("textItemReadOnly");
		return item;
	}

	private void setScreenEditable(boolean isEditable) {
		setStreamControlEditable(isEditable);
	}

	private void setStreamControlEditable(boolean isEditable) {
		setFormItemEditable(mediaOptionsAreaItem, isEditable);
		setFormItemEditable(outputFolderItem, isEditable);
		setFormItemEditable(playerPathItem, isEditable);
		setFormItemEditable(palantirUrlItem, isEditable);
		setFormItemEditable(palantirPortItem, isEditable);
	}
	
	private void setFormItemEditable(final FormItem item, final boolean isEditable) {
		item.setCanEdit(isEditable);
		item.setTextBoxStyle(isEditable ? "textItem" : "textItemReadOnly");
	}

	private void setDefaultButtonSettings() {
		editButton.enable();
		saveButton.disable();
		cancelButton.disable();
	}

	private void setEditButtonSettings() {
		editButton.disable();
		saveButton.disable();
		cancelButton.enable();
	}
}
