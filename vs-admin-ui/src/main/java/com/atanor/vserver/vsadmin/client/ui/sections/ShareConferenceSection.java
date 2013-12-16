package com.atanor.vserver.vsadmin.client.ui.sections;

import java.util.Date;
import java.util.List;

import com.atanor.vserver.common.rpc.dto.PresentationDto;
import com.atanor.vserver.vsadmin.client.ui.UiUtils;
import com.atanor.vserver.vsadmin.client.ui.presenters.ShareConferencePresenter;
import com.google.common.collect.Lists;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ShareConferenceSection extends BaseGridSection {

	private static final String DTO_GRID_ATTR = "dto";
	private static final String CREATE_TIME_GRID_ATTR = "createTime";
	private static final String FILE_NAME_GRID_ATTR = "fileName";
	private static final String OUTDATED_FLAG_ATTR = "outdated";

	private ShareConferencePresenter presenter;

	private final IButton startPresentation;
	private final IButton stopPresentation;
	private final Canvas snapshotBox;
	private final ListGrid listGrid;
	private final Img synchronizeImg;
	private final Img removeImg;

	public ShareConferenceSection() {
		setPadding(20);

		snapshotBox = createSnapshotBox();

		startPresentation = new IButton("Start Presentation");
		startPresentation.setWidth(100);
		startPresentation.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				presenter.startPresentation();
			}
		});
		
		stopPresentation = new IButton("Stop Presentation");
		stopPresentation.setWidth(100);
		stopPresentation.setDisabled(true);
		stopPresentation.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				presenter.stopPresentation();
			}
		});

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
		listGrid.addSelectionChangedHandler(new SelectionChangedHandler() {

			@Override
			public void onSelectionChanged(SelectionEvent event) {
				if (isAnyRecordSelected(listGrid)) {
					removeImg.enable();
				} else {
					removeImg.disable();
				}

			}
		});

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

		final HLayout gridToolbar = new HLayout();
		gridToolbar.setHeight(UiUtils.TOOLBAR_HEIGHT);
		gridToolbar.setAlign(Alignment.RIGHT);

		synchronizeImg = createToolbarImage("synchronize.png", "Synchronize Presenations");
		synchronizeImg.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				synchronizeImg.disable();
				presenter.getSynchronizationInfo();
			}
		});

		removeImg = createToolbarImage("recycle.png", "Remove Presentations");
		removeImg.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				presenter.removePresentations(getSelectedPresentations());
			}
		});

		gridToolbar.addMembers(wrape(synchronizeImg), wrape(removeImg));

		final VLayout gridPane = new VLayout();
		gridPane.setWidth100();
		gridPane.setHeight100();
		gridPane.addMembers(gridToolbar, listGrid);

		final HLayout spacer = new HLayout();
		spacer.setHeight(40);

		addMembers(headerPane, spacer, gridPane);
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
			record.setAttribute(OUTDATED_FLAG_ATTR, dto.isOutdated());

			records.add(record);
		}

		return records;
	}

	public void setPresenter(final ShareConferencePresenter presenter) {
		this.presenter = presenter;
	}

	public void onSynchronizationComplete(final List<PresentationDto> presentations) {
		synchronizeImg.enable();
		final List<ListGridRecord> records = createGridRecords(presentations);
		listGrid.setData(records.toArray(new ListGridRecord[] {}));
		listGrid.selectRecords(recordsToSelect(records));
	}

	private Record[] recordsToSelect(final List<ListGridRecord> records) {
		final List<Record> toSelect = Lists.newArrayList();
		for (Record record : records) {
			if (record.getAttributeAsBoolean(OUTDATED_FLAG_ATTR)) {
				toSelect.add(record);
			}
		}
		return toSelect.toArray(new ListGridRecord[] {});
	}

	private List<PresentationDto> getSelectedPresentations() {
		final List<PresentationDto> recordings = Lists.newArrayList();
		for (final ListGridRecord record : listGrid.getRecords()) {
			if (listGrid.isSelected(record)) {
				final PresentationDto dto = (PresentationDto) record.getAttributeAsObject(DTO_GRID_ATTR);
				recordings.add(dto);
			}
		}
		return recordings;
	}

	public void onPresentationStarted() {
		startPresentation.disable();
		stopPresentation.enable();
	}

	public void onPresentationStopped() {
		startPresentation.enable();
		stopPresentation.disable();
	}

}
