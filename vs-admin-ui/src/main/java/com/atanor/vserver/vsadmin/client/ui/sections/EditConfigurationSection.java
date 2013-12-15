package com.atanor.vserver.vsadmin.client.ui.sections;

import com.atanor.vserver.vsadmin.client.ui.UiUtils;
import com.atanor.vserver.vsadmin.client.ui.widgets.EditableForm;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
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

		final DynamicForm form = new EditableForm() {

			@Override
			protected void onAnyItemChanged(ItemChangedEvent event) {
				saveButton.enable();
			}
		};
			
		mediaOptionsAreaItem = EditableForm.createTextAreaItem("Media Options");
		outputFolderItem = EditableForm.createTextItem("Output recordings folder");
		playerPathItem = EditableForm.createTextItem("VLC installation path");
		palantirUrlItem = EditableForm.createTextItem("Palantir URL");
		palantirPortItem = EditableForm.createTextItem("Palantir port");

		setFormItemValues();

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

	private void setFormItemValues() {
		setFormItemValue(mediaOptionsAreaItem,
				":sout=#tra1nscode{vcodec=h264,acodec=mpga,ab=128,channels=2,samplerate=44100}:std{access=file,mux=ts,dst=%s}");
		setFormItemValue(outputFolderItem, "D:/tmp");
		setFormItemValue(playerPathItem, "C:/Program Files/VideoLAN/VLC");
		setFormItemValue(palantirUrlItem, "192.168.1.80");
		setFormItemValue(palantirPortItem, "5050");
	}

	private void setFormItemValue(final FormItem item, final String value) {
		item.setValue(value);
		item.setAttribute(UiUtils.ORIGIN_ITEM_VALUE, value);
	}
}
