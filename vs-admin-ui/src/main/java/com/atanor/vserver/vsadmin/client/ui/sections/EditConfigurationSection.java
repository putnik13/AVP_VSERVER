package com.atanor.vserver.vsadmin.client.ui.sections;

import com.atanor.vserver.vsadmin.client.ui.UiUtils;
import com.atanor.vserver.vsadmin.client.ui.widgets.EditableForm;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.events.ItemChangedHandler;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;

public class EditConfigurationSection extends BaseSection {

	private final IButton editButton;
	private final IButton saveButton;
	private final IButton cancelButton;

	private final TextAreaItem streamMediaOptionsAreaItem;
	private final TextItem streamOutputFolderItem;
	private final TextItem playerPathItem;
	private final TextItem palantirUrlItem;
	private final TextItem palantirPortItem;

	private final TextAreaItem confMediaOptionsAreaItem;
	private final TextItem confOutputFolderItem;
	private final CheckboxItem addLogoItem;

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
		final SectionStackSection conferenceSection = createSection("Conference settings");
		final SectionStackSection broadcastingSection = createSection("Broadcasting settings");
		final SectionStackSection generalSection = createSection("General settings");

		final EditableForm streamControlForm = new EditableForm();
		streamMediaOptionsAreaItem = EditableForm.createTextAreaItem("Media Options");
		streamOutputFolderItem = EditableForm.createTextItem("Output recordings folder");
		streamOutputFolderItem.setColSpan(4);
		palantirUrlItem = EditableForm.createTextItem("Palantir URL");
		palantirPortItem = EditableForm.createTextItem("Palantir port");
		streamControlForm.setFields(streamMediaOptionsAreaItem, streamOutputFolderItem, palantirUrlItem,
				palantirPortItem);

		final EditableForm conferenceForm = new EditableForm();
		confMediaOptionsAreaItem = EditableForm.createTextAreaItem("Media Options");
		confOutputFolderItem = EditableForm.createTextItem("Output presentations folder");
		confOutputFolderItem.setColSpan(4);
		addLogoItem = new CheckboxItem();
		addLogoItem.setTitle("Add logotype to PDF");
		addLogoItem.setColSpan(4);
		
		conferenceForm.setFields(confMediaOptionsAreaItem, confOutputFolderItem, addLogoItem);

		final EditableForm generalForm = new EditableForm();
		playerPathItem = EditableForm.createTextItem("VLC installation path");
		playerPathItem.setColSpan(4);
		generalForm.setFields(playerPathItem);

		streamControlSection.addItem(streamControlForm);
		conferenceSection.addItem(conferenceForm);
		generalSection.addItem(generalForm);

		sectionStack.addSection(streamControlSection);
		sectionStack.addSection(conferenceSection);
		sectionStack.addSection(broadcastingSection);
		sectionStack.addSection(generalSection);

		setScreenEditable(false);

		setFormItemValues();
		addAnyItemChangedHandler(streamControlForm, conferenceForm, generalForm);

		addMembers(buttonLayout, sectionStack);
	}

	private SectionStackSection createSection(final String title) {
		final SectionStackSection section = new SectionStackSection(title);
		section.setCanCollapse(true);
		return section;
	}

	private void setScreenEditable(boolean isEditable) {
		setStreamControlEditable(isEditable);
		setConferenceEditable(isEditable);
		setGeneralEditable(isEditable);
	}

	private void setStreamControlEditable(boolean isEditable) {
		setFormItemEditable(streamMediaOptionsAreaItem, isEditable);
		setFormItemEditable(streamOutputFolderItem, isEditable);
		setFormItemEditable(palantirUrlItem, isEditable);
		setFormItemEditable(palantirPortItem, isEditable);
	}

	private void setConferenceEditable(boolean isEditable) {
		addLogoItem.setCanEdit(isEditable);
		setFormItemEditable(confMediaOptionsAreaItem, isEditable);
		setFormItemEditable(confOutputFolderItem, isEditable);
	}

	private void setGeneralEditable(boolean isEditable) {
		setFormItemEditable(playerPathItem, isEditable);
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
		setFormItemValue(streamMediaOptionsAreaItem,
				":sout=#tra1nscode{vcodec=h264,acodec=mpga,ab=128,channels=2,samplerate=44100}:std{access=file,mux=ts,dst=%s}");
		setFormItemValue(streamOutputFolderItem, "D:/tmp");
		setFormItemValue(playerPathItem, "C:/Program Files/VideoLAN/VLC");
		setFormItemValue(palantirUrlItem, "192.168.1.80");
		setFormItemValue(palantirPortItem, "5050");
		setFormItemValue(addLogoItem, true);
	}

	private void setFormItemValue(final FormItem item, final Object value) {
		item.setValue(value);
		item.setAttribute(UiUtils.ORIGIN_ITEM_VALUE, value);
	}

	private void addAnyItemChangedHandler(final EditableForm... forms) {
		final ItemChangedHandler handler = createAnyItemChangedHandler(forms);
		for (final EditableForm form : forms) {
			form.addItemChangedHandler(handler);
		}
	}

	private ItemChangedHandler createAnyItemChangedHandler(final EditableForm[] forms) {
		return new ItemChangedHandler() {

			@Override
			public void onItemChanged(ItemChangedEvent event) {
				if (isAnyItemChanged(forms)) {
					saveButton.enable();
				} else {
					saveButton.disable();
				}
			}
		};
	}

	private Boolean isAnyItemChanged(final EditableForm[] forms) {
		for (final EditableForm form : forms) {
			if (form.isModified()) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
}
