package com.atanor.vserver.vsadmin.client.ui.sections;

import java.util.Date;
import java.util.List;

import com.atanor.vserver.common.rpc.dto.RecordingDto;
import com.atanor.vserver.vsadmin.client.ui.UiUtils;
import com.atanor.vserver.vsadmin.client.ui.presenters.StreamControlPresenter;
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
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tile.TileGrid;
import com.smartgwt.client.widgets.tile.TileRecord;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

public class StreamControlSection extends BaseGridSection {

	private static final String DTO_GRID_ATTR = "dto";
	private static final String DURATION_GRID_ATTR = "duration";
	private static final String START_TIME_GRID_ATTR = "startTime";
	private static final String END_TIME_GRID_ATTR = "endTime";
	private static final String FILE_NAME_GRID_ATTR = "fileName";
	private static final String ENCODED_IMAGE_ATTR = "encodeImage";
	private static final String OUTDATED_FLAG_ATTR = "outdated";
	private static final String TILE_NAME = "tilename";
	private static final String TILE_PICTURE = "tilepicture";

	private StreamControlPresenter presenter;

	private final IButton startRecord;
	private final IButton stopRecord;
	private final Canvas snapshotBox;
	private final ListGrid listGrid;
	private final Img synchronizeImg;
	private final Img removeImg;
	private Img snapshot;
	private TileGrid tileGrid;

	public StreamControlSection() {
		setPadding(20);

		snapshotBox = createSnapshotBox();

		startRecord = new IButton("Start Recording");
		startRecord.setWidth(90);
		startRecord.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				presenter.startRecording();
			}
		});

		stopRecord = new IButton("Stop Recording");
		stopRecord.setWidth(90);
		stopRecord.setDisabled(true);
		stopRecord.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				presenter.stopRecording();
			}
		});

		final HLayout headerPane = new HLayout();
		headerPane.addMembers(snapshotBox, startRecord, stopRecord);
		headerPane.setMembersMargin(10);

		listGrid = new ListGrid();
		listGrid.setWidth100();
		listGrid.setHeight100();
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
		gridToolbar.setHeight(UiUtils.TOOLBAR_HEIGHT);
		gridToolbar.setAlign(Alignment.RIGHT);

		final DynamicForm form = new DynamicForm();
		final CheckboxItem viewCheckbox = new CheckboxItem();
		viewCheckbox.setTitle("Snapshots View");
		viewCheckbox.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if (isItemChecked(event.getItem())) {
					listGrid.sendToBack();
				} else {
					listGrid.bringToFront();
				}
			}
		});
		form.setFields(viewCheckbox);
		form.setLeft(0);

		synchronizeImg = createToolbarImage("synchronize.png", "Synchronize Recordings");
		synchronizeImg.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				synchronizeImg.disable();
				presenter.getSynchronizationInfo();
			}
		});

		removeImg = createToolbarImage("recycle.png", "Remove Recordings");
		removeImg.setDisabled(true);
		removeImg.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				presenter.removeRecordings(getSelectedRecordings());
			}
		});

		gridToolbar.addMembers(form, new LayoutSpacer(), wrape(synchronizeImg), wrape(removeImg));

		final Canvas recordingsView = new Canvas();
		recordingsView.setWidth100();
		recordingsView.setHeight100();

		recordingsView.addChild(createSnapshotsView());
		recordingsView.addChild(listGrid);

		final VLayout gridPane = new VLayout();
		gridPane.setWidth100();
		gridPane.setHeight100();
		gridPane.addMembers(gridToolbar, recordingsView);

		final HLayout spacer = new HLayout();
		spacer.setHeight(40);

		addMembers(headerPane, spacer, gridPane);
	}

	protected boolean isItemChecked(final FormItem item) {
		return item.getValue() != null && (Boolean) item.getValue();
	}

	public void setRecordings(final List<RecordingDto> recordings) {
		final List<ListGridRecord> records = createGridRecords(recordings);
		listGrid.setData(records.toArray(new ListGridRecord[] {}));

		final List<TileRecord> tiles = createTileRecords(recordings);
		tileGrid.setData(tiles.toArray(new TileRecord[] {}));
	}

	private List<ListGridRecord> createGridRecords(final List<RecordingDto> recordings) {
		final List<ListGridRecord> records = Lists.newArrayList();
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

	private List<TileRecord> createTileRecords(List<RecordingDto> recordings) {
		final List<TileRecord> records = Lists.newArrayList();
		for (RecordingDto dto : recordings) {
			TileRecord record = new TileRecord();
			record.setAttribute(DTO_GRID_ATTR, dto);
			record.setAttribute(TILE_NAME, dto.getName());
			
			final String source = "data:image/png;base64," + dto.getEncodedImage();
			record.setAttribute(TILE_PICTURE, source);

			records.add(record);
		}

		return records;
	}

	public void onSynchronizationComplete(final List<RecordingDto> recordings) {
		synchronizeImg.enable();
		final List<ListGridRecord> records = createGridRecords(recordings);
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

	public void setPresenter(final StreamControlPresenter presenter) {
		this.presenter = presenter;
	}

	private List<RecordingDto> getSelectedRecordings() {
		final List<RecordingDto> recordings = Lists.newArrayList();
		for (final ListGridRecord record : listGrid.getRecords()) {
			if (listGrid.isSelected(record)) {
				final RecordingDto dto = (RecordingDto) record.getAttributeAsObject(DTO_GRID_ATTR);
				dto.setEncodedImage(null);
				recordings.add(dto);
			}
		}
		return recordings;
	}

	public void onRecordingStarted() {
		startRecord.disable();
		stopRecord.enable();
	}

	public void onRecordingStopped() {
		cleanSnapshot();
		startRecord.enable();
		stopRecord.disable();
	}

	public void setSnapshot(final String encodedSnapshot) {
		cleanSnapshot();

		final String source = "data:image/png;base64," + encodedSnapshot;
		snapshot = new Img();
		snapshot.setSrc(source);
		snapshot.setWidth100();
		snapshot.setHeight100();

		snapshotBox.addChild(snapshot);
	}

	private void cleanSnapshot() {
		if (snapshot != null) {
			snapshot.destroy();
			snapshot = null;
		}
	}

	private Canvas createSnapshotsView() {
		tileGrid = new TileGrid();
		tileGrid.setWidth100();
		tileGrid.setHeight100();
		tileGrid.setTileWidth(194);
		tileGrid.setTileHeight(165);
		tileGrid.setBackgroundColor("white");
		
		final DetailViewerField pictureField = new DetailViewerField(TILE_PICTURE);
		pictureField.setType("image");
		pictureField.setImageWidth(186);
		pictureField.setImageHeight(120);

		final DetailViewerField nameField = new DetailViewerField(TILE_NAME);

		tileGrid.setFields(pictureField, nameField);

		return tileGrid;
	}

}
