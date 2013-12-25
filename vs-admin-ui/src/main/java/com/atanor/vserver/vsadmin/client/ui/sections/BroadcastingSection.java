package com.atanor.vserver.vsadmin.client.ui.sections;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.atanor.vserver.common.rpc.dto.RecordingDto;
import com.atanor.vserver.vsadmin.client.ui.UiUtils;
import com.atanor.vserver.vsadmin.client.ui.presenters.BroadcastingPresenter;
import com.atanor.vserver.vsadmin.client.ui.presenters.StreamControlPresenter;
import com.google.common.collect.Lists;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
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

public class BroadcastingSection extends BaseGridSection {
	
	private static final String DTO_GRID_ATTR = "dto";
	private static final String DURATION_GRID_ATTR = "duration";
	private static final String START_TIME_GRID_ATTR = "startTime";
	private static final String END_TIME_GRID_ATTR = "endTime";
	private static final String FILE_NAME_GRID_ATTR = "fileName";
	private static final String ENCODED_IMAGE_ATTR = "encodeImage";
	private static final String OUTDATED_FLAG_ATTR = "outdated";
	private static final String TILE_NAME = "tilename";
	private static final String TILE_PICTURE = "tilepicture";

	private BroadcastingPresenter presenter;

	private final IButton startStream;
	private final IButton previewStream;
	private final IButton stopStream;
	private final Canvas snapshotBox;
	private final SelectItem sourceSelector, sourceString;
	private final Img synchronizeImg;
	private Img snapshot;
	private TileGrid tileGrid;


	public BroadcastingSection() {
		setPadding(20);
		
		snapshotBox = createSnapshotBox();

		previewStream = new IButton("Preview Stream");
		previewStream.setWidth(90);
//		previewStream.addClickHandler(new ClickHandler() {
//
//			@Override
//			public void onClick(ClickEvent event) {
//				presenter.previewStreaming();
//			}
//		});

		stopStream = new IButton("Stop Recording");
		stopStream.setWidth(90);
		stopStream.setDisabled(true);
		stopStream.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				presenter.stopStreaming();
			}
		});

		
		startStream = new IButton("Start Streaming");
		startStream.setWidth(90);
		startStream.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				presenter.startStreaming();
			}
		});
		
		
//		final LinkedHashMap<String, String> sourceSelector = new LinkedHashMap<String, String>();
//		sourceSelector.put("streamOnline", "Online stream");
//		sourceSelector.put("fileToStream", "From File");
//
//		final RadioGroupItem input = new RadioGroupItem();
//		input.setDefaultValue("streamOnline");
//		input.setWidth(300);
//		input.setVAlign(VerticalAlignment.TOP);
//		input.setTitle("Input Source Selector");
//		input.setShowTitle(true);
//		input.setValueMap(sourceSelector);
//		input.addChangedHandler(new ChangedHandler() {
//			public void onChanged(ChangedEvent event) {
//				System.out.println(event.getSource().toString());
//			}
//		});

		final HLayout headerPane = new HLayout();
		headerPane.addMembers(snapshotBox, previewStream, startStream, stopStream);
		headerPane.setMembersMargin(10);
		
//		final DynamicForm controls = new DynamicForm();
//		controls.setFields(input);

		final HLayout spacer = new HLayout();
		spacer.setHeight(40);

		addMembers(headerPane, new LayoutSpacer(), spacer);
	

		final DynamicForm form = new DynamicForm();
		
		final CheckboxItem viewCheckbox = new CheckboxItem();
		viewCheckbox.setTitle("Stream From File");
		viewCheckbox.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if (!isItemChecked(event.getItem())) {
					tileGrid.sendToBack();
				} else {
					tileGrid.bringToFront();
				}
			}
		});
		
		
		sourceSelector = new SelectItem();  
		sourceSelector.setName("sourceSelector");  
		sourceSelector.setPickListWidth(210);  
		sourceSelector.setTitle("Select Input Stream Source");  
		sourceSelector.setCanEdit(false);
//		sourceSelector.setOptionDataSource(TILE_NAME);  
	  
		sourceSelector.addChangedHandler(new ChangedHandler() {  
	            public void onChanged(ChangedEvent event) {  
	                form.clearValue("sourceSelector");  
	            }  
	        });  
	  
		sourceString = new SelectItem() {  
	            @Override  
	            protected Criteria getPickListFilterCriteria() {  
	                String src = (String) sourceSelector.getValue();  
	                Criteria criteria = new Criteria("sourceSelector", src);  
	                return criteria;  
	            }  
	        };  
	        sourceString.setName("parameters");  
	        sourceString.setTitle("Parameters");  
	        sourceString.setPickListWidth(250);          
//	        sourceString.setOptionDataSource(itemSupplyDS);  
	        
	        form.setFields(viewCheckbox, sourceSelector, sourceString);
			form.setLeft(0);
	 
	

	final HLayout gridToolbar = new HLayout();
	gridToolbar.setHeight(UiUtils.TOOLBAR_HEIGHT);
	gridToolbar.setAlign(Alignment.RIGHT);


	synchronizeImg = createToolbarImage("synchronize.png", "Synchronize Recordings");
	synchronizeImg.addClickHandler(new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			synchronizeImg.disable();
			presenter.getSynchronizationInfo();
		}
	});

	
	final Canvas recordingsView = new Canvas();
	recordingsView.setWidth100();
	recordingsView.setHeight100();

	recordingsView.addChild(createSnapshotsView());
	
	final VLayout gridPane = new VLayout();
	gridPane.setWidth100();
	gridPane.setHeight100();
	gridPane.addMembers(gridToolbar, recordingsView);

	addMembers(headerPane, spacer, gridPane);
}

protected boolean isItemChecked(final FormItem item) {
	return item.getValue() != null && (Boolean) item.getValue();
}

public void setRecordings(final List<RecordingDto> recordings) {
	
	final List<TileRecord> tiles = createTileRecords(recordings);
	tileGrid.setData(tiles.toArray(new TileRecord[] {}));
	sourceSelector.setDataPath(TILE_NAME);
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

public void setPresenter(final BroadcastingPresenter presenter) {
	this.presenter = presenter;
}



public void onStreamStarted() {
	startStream.disable();
	stopStream.enable();
	previewStream.disable();
}

public void onPreviewStarted(){
	startStream.disable();
	stopStream.enable();
	previewStream.disable();
	
}

public void onStreamStopped() {
	cleanSnapshot();
	startStream.enable();
	stopStream.disable();
	previewStream.enable();
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
