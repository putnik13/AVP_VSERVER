package com.atanor.vserver.vsadmin.client.ui.sections;

import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

public class EditConfigurationSection extends BaseSection {

	public EditConfigurationSection() {

		setPadding(20);

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
		form.setNumCols(4);
		form.setColWidths(200, "*", 200, "*");
		form.setCellPadding(5);		
		//form.setBorder("1px solid #6a6a6a");
		
		final TextAreaItem mediaOptionsAreaItem = new TextAreaItem();
		mediaOptionsAreaItem.setTitle("Media Options");
		mediaOptionsAreaItem.setColSpan(4);
		mediaOptionsAreaItem.setLength(5000);
		mediaOptionsAreaItem.setWidth("*");
		mediaOptionsAreaItem.setCanEdit(false);
		mediaOptionsAreaItem.setTitleStyle("formTitleVs");
		mediaOptionsAreaItem.setValue(":sout=#transcode{vcodec=h264,acodec=mpga,ab=128,channels=2,samplerate=44100}:std{access=file,mux=ts,dst=%s}");
		
		final TextItem outputFolderItem = createTextItem("Output recordings folder");
		outputFolderItem.setValue("D:/tmp");
		
		final TextItem playerPathItem = createTextItem("VLC installation path");
		playerPathItem.setValue("C:/Program Files/VideoLAN/VLC");
		
		final TextItem palantirUrlItem = createTextItem("Palantir URL");
		palantirUrlItem.setValue("192.168.1.80");
		
		final TextItem palantirPortItem = createTextItem("Palantir port");
		palantirPortItem.setValue("5050");
		
		form.setFields(mediaOptionsAreaItem, outputFolderItem, playerPathItem, palantirUrlItem, palantirPortItem);
		
		final VLayout streamControlLayout = new VLayout();
		streamControlLayout.setWidth100();
		streamControlLayout.setPadding(20);
		
		streamControlLayout.addMember(form);
		streamControlSection.addItem(streamControlLayout);

		sectionStack.addSection(streamControlSection);
		sectionStack.addSection(broadcastingSection);
		sectionStack.addSection(conferenceSection);
		sectionStack.addSection(generalSection);
		
		addMember(sectionStack);
	}

	private SectionStackSection createSection(final String title) {
		final SectionStackSection section = new SectionStackSection(title);
		section.setCanCollapse(true);
		return section;
	}
	
	private TextItem createTextItem(final String title) {
		final TextItem item = new TextItem();
		item.setTitle(title);
		item.setCanEdit(false);
		item.setWidth("*");
		item.setTitleStyle("formTitleVs");
		
		return item;
	}
	
}
