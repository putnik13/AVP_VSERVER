package com.atanor.vserver.vsadmin.client.ui.sections;

import java.util.Date;
import java.util.List;

import com.atanor.vserver.common.rpc.dto.RecordingDto;
import com.google.common.collect.Lists;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.MouseOutEvent;
import com.smartgwt.client.widgets.events.MouseOutHandler;
import com.smartgwt.client.widgets.events.MouseOverEvent;
import com.smartgwt.client.widgets.events.MouseOverHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class StreamControlSection extends BaseSection {

	private static final DateTimeFormat df = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");
	public static final Integer SNAPSHOT_WIDTH = 265;
	public static final Integer SNAPSHOT_HEIGHT = 200;
	private static final Integer TOOLBAR_SIZE = 30;
	private static final Integer TOOLBAR_SIZE_HOVER = 35;

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
	private final Img synchronizeImg;
	private final Img removeImg;

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
		listGrid.addSelectionChangedHandler(new SelectionChangedHandler() {

			@Override
			public void onSelectionChanged(SelectionEvent event) {
				if (isAnyRecordSelected()) {
					removeImg.setSrc("recycle.png");
				} else {
					removeImg.setSrc("recycle_empty.png");
				}

			}
		});

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

		final HLayout gridToolbar = new HLayout();
		gridToolbar.setHeight(TOOLBAR_SIZE_HOVER);
		gridToolbar.setAlign(Alignment.RIGHT);

		synchronizeImg = createToolbarImage("synchronize.png", "Synchronize Recordings");
		removeImg = createToolbarImage("recycle_empty.png", "Remove Recordings");
		gridToolbar.addMembers(wrape(synchronizeImg), wrape(removeImg));

		final VLayout gridPane = new VLayout();
		gridPane.setWidth100();
		gridPane.setHeight100();
		gridPane.addMembers(gridToolbar, listGrid);

		final HLayout spacer = new HLayout();
		spacer.setHeight(40);

		addMembers(headerPane, spacer, gridPane);
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

	private Img createToolbarImage(final String imgSource, final String tooltip) {
		final Img img = new Img();
		img.setSrc(imgSource);
		img.setTooltip(tooltip);
		img.setWidth(TOOLBAR_SIZE);
		img.setHeight(TOOLBAR_SIZE);
		img.addMouseOverHandler(new MouseOverHandler() {

			@Override
			public void onMouseOver(MouseOverEvent event) {
				img.animateResize(TOOLBAR_SIZE_HOVER, TOOLBAR_SIZE_HOVER, null, 100);
			}
		});
		img.addMouseOutHandler(new MouseOutHandler() {

			@Override
			public void onMouseOut(MouseOutEvent event) {
				img.animateResize(TOOLBAR_SIZE, TOOLBAR_SIZE);
			}
		});

		return img;
	}

	private boolean isAnyRecordSelected() {
		for (final ListGridRecord record : listGrid.getRecords()) {
			if (listGrid.isSelected(record)) {
				return true;
			}
		}
		return false;
	}

	private Canvas wrape(final Img image) {
		Canvas canvas = new Canvas();
		canvas.setWidth(TOOLBAR_SIZE_HOVER);
		canvas.setHeight(TOOLBAR_SIZE_HOVER);
		canvas.addChild(image);
		return canvas;
	}
}
