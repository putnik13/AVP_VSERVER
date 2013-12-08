package com.atanor.vserver.vsadmin.client.ui.sections;

import java.util.Date;
import java.util.List;

import com.atanor.vserver.common.rpc.dto.PresentationDto;
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

public class ShareConferenceSection extends BaseSection {

	private static final DateTimeFormat df = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");
	public static final Integer SNAPSHOT_WIDTH = 265;
	public static final Integer SNAPSHOT_HEIGHT = 200;

	private static final String DTO_GRID_ATTR = "dto";
	private static final String CREATE_TIME_GRID_ATTR = "createTime";
	private static final String FILE_NAME_GRID_ATTR = "fileName";

	private final IButton startPresentation;
	private final IButton stopPresentation;
	private final Canvas snapshotBox;
	private final ListGrid listGrid;

	public ShareConferenceSection() {
		setPadding(20);

		snapshotBox = createSnapshotBox();

		startPresentation = new IButton("Start Presentation");
		startPresentation.setWidth(100);

		stopPresentation = new IButton("Stop Presentation");
		stopPresentation.setWidth(100);
		stopPresentation.setDisabled(true);

		final HLayout headerPane = new HLayout();
		headerPane.addMembers(snapshotBox, startPresentation, stopPresentation);
		headerPane.setMembersMargin(10);

		listGrid = new ListGrid();
		listGrid.setCanHover(true);
		listGrid.setShowHover(true);
		listGrid.setShowHoverComponents(true);
		listGrid.setShowRowNumbers(true);
		listGrid.setSelectionType(SelectionStyle.SIMPLE);
		listGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);

		final ListGridField fileName = new ListGridField(FILE_NAME_GRID_ATTR, "File Name");
		final ListGridField createTime = new ListGridField(CREATE_TIME_GRID_ATTR, "Create Time");
		createTime.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (value == null) {
					return null;
				}
				return df.format((Date) value);
			}
		});

		listGrid.setFields(fileName, createTime);

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

	public void setPresentations(final List<PresentationDto> presentations) {
		List<ListGridRecord> records = createGridRecords(presentations);
		listGrid.setData(records.toArray(new ListGridRecord[] {}));
	}

	private List<ListGridRecord> createGridRecords(final List<PresentationDto> presentations) {
		List<ListGridRecord> records = Lists.newArrayList();
		for (PresentationDto dto : presentations) {
			ListGridRecord record = new ListGridRecord();
			record.setAttribute(DTO_GRID_ATTR, dto);
			record.setAttribute(FILE_NAME_GRID_ATTR, dto.getName());
			record.setAttribute(CREATE_TIME_GRID_ATTR, dto.getCreateTime());

			records.add(record);
		}

		return records;
	}
}
