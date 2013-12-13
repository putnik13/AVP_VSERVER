package com.atanor.vserver.vsadmin.client.ui.sections;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.atanor.vserver.common.rpc.dto.RecordingDto;
import com.google.common.collect.Lists;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;  
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;  
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class BroadcastingSection extends BaseGridSection {

	private static final String DTO_GRID_ATTR = "dto";
	private static final String DURATION_GRID_ATTR = "duration";
	private static final String START_TIME_GRID_ATTR = "startTime";
	private static final String END_TIME_GRID_ATTR = "endTime";
	private static final String FILE_NAME_GRID_ATTR = "fileName";
	private static final String ENCODED_IMAGE_ATTR = "encodeImage";
	private static final String OUTDATED_FLAG_ATTR = "outdated";

	private final IButton startStream;
	private final IButton previewStream;
	private final IButton stopStream;
	private final Canvas snapshotBox;
	private final ListGrid listGrid;
	private final Img synchronizeImg;
	private final Img removeImg;

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
  
		
		 RadioGroupItem input = new RadioGroupItem();  
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
		headerPane.addMembers(snapshotBox, previewStream, startStream, stopStream);
		headerPane.setMembersMargin(10);

		listGrid = new ListGrid();
		listGrid.setCanHover(true);
		listGrid.setShowHover(true);
		listGrid.setShowHoverComponents(true);
		listGrid.setShowRowNumbers(true);
		listGrid.setSelectionType(SelectionStyle.SIMPLE);
		listGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);
		listGrid.addSelectionChangedHandler(new SelectionChangedHandler() {

			@Override
			public void onSelectionChanged(SelectionEvent event) {
				if (isAnyRecordSelected(listGrid)) {
					removeImg.setSrc("recycle.png");
				} else {
					removeImg.setSrc("recycle_empty.png");
				}

			}
		});

		final ListGridField fileName = new ListGridField(FILE_NAME_GRID_ATTR, "File Name");

		final ListGridField endTime = new ListGridField(END_TIME_GRID_ATTR, "Date of Record");
		endTime.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (value == null) {
					return null;
				}
				return df.format((Date) value);
			}
		});
		final ListGridField duration = new ListGridField(DURATION_GRID_ATTR, "Duration");
		listGrid.setFields(fileName, endTime, duration);

		final HLayout gridToolbar = new HLayout();
		gridToolbar.setHeight(TOOLBAR_HEIGHT);
		gridToolbar.setAlign(Alignment.RIGHT);

		synchronizeImg = createToolbarImage("synchronize.png", "Synchronize Recordings");
		removeImg = createToolbarImage("recycle_empty.png", "Remove Recordings");
		gridToolbar.addMembers(wrape(synchronizeImg), wrape(removeImg));
		
		
		DynamicForm controls = new DynamicForm();  
        controls.setFields(input); 
	
		final VLayout gridPane = new VLayout();
		gridPane.setWidth100();
		gridPane.setHeight100();
		gridPane.addMembers(gridToolbar, listGrid);
		gridPane.setVisible(false);

		final HLayout spacer = new HLayout();
		spacer.setHeight(40);

		addMembers(headerPane, spacer, controls, spacer, gridPane);
	}

	public void setRecordings(final List<RecordingDto> recordings) {
		List<ListGridRecord> records = createGridRecords(recordings);
		listGrid.setData(records.toArray(new ListGridRecord[] {}));
	}

	private List<ListGridRecord> createGridRecords(final List<RecordingDto> recordings) {
		List<ListGridRecord> records = Lists.newArrayList();
		for (RecordingDto dto : recordings) {
			ListGridRecord record = new ListGridRecord();
			record.setAttribute(DTO_GRID_ATTR, dto);
			record.setAttribute(FILE_NAME_GRID_ATTR, dto.getName());
			record.setAttribute(START_TIME_GRID_ATTR, dto.getStartTime());
			record.setAttribute(END_TIME_GRID_ATTR, dto.getEndTime());
			record.setAttribute(DURATION_GRID_ATTR, dto.getDuration());
			record.setAttribute(ENCODED_IMAGE_ATTR, dto.getEncodedImage());
			record.setAttribute(OUTDATED_FLAG_ATTR, dto.isOutdated());

			records.add(record);
		}

		return records;
	}

}
