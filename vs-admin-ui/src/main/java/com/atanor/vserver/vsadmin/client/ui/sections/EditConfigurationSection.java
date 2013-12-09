package com.atanor.vserver.vsadmin.client.ui.sections;

import com.atanor.vserver.vsadmin.client.SettingsDS;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;

public class EditConfigurationSection extends BaseSection {

	public EditConfigurationSection() {

final HelpCanvas help1 = new HelpCanvas("help1");
		
		HLayout layout = new HLayout();
		layout.setMembersMargin(20);

		final SectionStack sectionStack = new SectionStack();
		sectionStack.setVisibilityMode(VisibilityMode.MUTEX);
		sectionStack.setWidth(800);
		sectionStack.setHeight(600);

		SectionStackSection section1 = new SectionStackSection(
				"Settings for Stream Recorder");
		section1.setExpanded(true);
		section1.setResizeable(false);
	 

		// VLayout layout = new VLayout(25);

		layout.setAlign(Alignment.CENTER);
		layout.setMargin(23);
		layout.setWidth("93%");

		final DataSource dataSource = SettingsDS.getInstance();

		final DynamicForm form = new DynamicForm();
		form.setIsGroup(true);
		form.setGroupTitle("Update");
		form.setNumCols(4);
		form.setDataSource(dataSource);
		form.setMargin(1);

		final ListGrid listGrid = new ListGrid();
		listGrid.setWidth100();
		listGrid.setHeight(130);
		listGrid.setDataSource(dataSource);
		listGrid.setAutoFetchData(true);
		listGrid.addRecordClickHandler(new RecordClickHandler() {
			public void onRecordClick(RecordClickEvent event) {
				form.reset();
				form.editSelectedData(listGrid);
			}
		});

		section1.addItem(listGrid);
		section1.addItem(form);

		IButton butt = new IButton("Save");
		butt.setMargin(2);

		butt.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				form.saveData();
			}
		});
		section1.addItem(butt);

		Button button = new Button("Hello from Admin");
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
			}
		});

		sectionStack.addSection(section1);

		SectionStackSection section2 = new SectionStackSection("Help 1");
		section2.setCanCollapse(true);

		section2.addItem(help1);
		sectionStack.addSection(section2);
		// SectionStackSection section3 = new SectionStackSection("Help 2");
		// section3.setExpanded(true);
		// section3.setCanCollapse(true);
		// section3.addItem(help2);
		// sectionStack.addSection(section3);

		// IButton resizeButton = new IButton("Resize Help 1");
		// resizeButton.setWidth(150);
		// resizeButton.addClickHandler(new ClickHandler() {
		// public void onClick(ClickEvent event) {
		// help1.setHeight(200);
		// }
		// });

		layout.addMember(sectionStack);

		// VLayout buttons = new VLayout(25);
		// buttons.setMembersMargin(10);
		// buttons.addMember(resizeButton);

		// layout.addMember(buttons);

		layout.addMember(button);
		addMember(layout);

		// RootPanel.get().add(topTabSet);
	}

	class HelpCanvas extends Canvas {
		private String contents = "<b>Severity 1</b> - Critical problem<br>System is available in production or "
				+ "*** user's operations."
				;

		public HelpCanvas(String id) {
			setID(id);
			setPadding(10);
			setOverflow(Overflow.AUTO);
			setContents(contents);
		}
	};
		
}
