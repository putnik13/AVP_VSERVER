package com.atanor.vserver.vsadmin.client.ui.sections;

import java.util.Date;
import java.util.List;

import com.atanor.vserver.common.rpc.dto.RecordingDto;
import com.google.common.collect.Lists;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;

public class StreamControlSection extends BaseSection {

	private static final DateTimeFormat df = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");
	public static final Integer SNAPSHOT_WIDTH = 265;
	public static final Integer SNAPSHOT_HEIGHT = 200;

	private static final String DTO_GRID_ATTR = "dto";
	private static final String DURATION_GRID_ATTR = "duration";
	private static final String START_TIME_GRID_ATTR = "startTime";
	private static final String END_TIME_GRID_ATTR = "endTime";
	private static final String FILE_NAME_GRID_ATTR = "fileName";
	private static final String ENCODED_IMAGE_ATTR = "encodeImage";
	private static final String OUTDATED_FLAG_ATTR = "outdated";
	
	private final IButton startRecord;
	private final IButton stopRecord;
	private final Canvas snapshotBox;
	private final ListGrid listGrid;

	public StreamControlSection() {
		setPadding(20);
		
		snapshotBox = createSnapshotBox();

		startRecord = new IButton("Start Recording");
		startRecord.setWidth(90);

		stopRecord = new IButton("Stop Recording");
		stopRecord.setWidth(90);
		stopRecord.setDisabled(true);
		
		final HLayout headerPane = new HLayout();
		headerPane.addMembers(snapshotBox, startRecord, stopRecord);
		headerPane.setMembersMargin(10);

		listGrid = new ListGrid();
		listGrid.setCanHover(true);
		listGrid.setShowHover(true);
		listGrid.setShowHoverComponents(true);
		listGrid.setShowRowNumbers(true);
		listGrid.setSelectionType(SelectionStyle.SIMPLE);
		listGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);

		final ListGridField fileName = new ListGridField(FILE_NAME_GRID_ATTR, "File Name");
		final ListGridField startTime = new ListGridField(START_TIME_GRID_ATTR, "Start Time");
		startTime.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (value == null) {
					return null;
				}
				return df.format((Date) value);
			}
		});
		final ListGridField endTime = new ListGridField(END_TIME_GRID_ATTR, "End Time");
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
		listGrid.setFields(fileName, startTime, endTime, duration);
		
		addMembers(headerPane, listGrid);
	}

	private Canvas createSnapshotBox() {
		final Canvas canvas = new Canvas();
		canvas.setWidth(SNAPSHOT_WIDTH);
		canvas.setHeight(SNAPSHOT_HEIGHT);
		canvas.setShowEdges(true);
		canvas.setBackgroundColor("black");
		return canvas;
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
