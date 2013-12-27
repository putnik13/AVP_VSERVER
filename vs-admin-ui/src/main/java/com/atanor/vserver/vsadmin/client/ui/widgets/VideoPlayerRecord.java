package com.atanor.vserver.vsadmin.client.ui.widgets;

import com.atanor.vserver.common.rpc.dto.RecordingDto;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.MouseOutEvent;
import com.smartgwt.client.widgets.events.MouseOutHandler;
import com.smartgwt.client.widgets.events.MouseOverEvent;
import com.smartgwt.client.widgets.events.MouseOverHandler;

public class VideoPlayerRecord extends Label {

	private static final String DEFAULT_ITEM_COLOR_HOVER = "#FFFFCC";
	private static final String DEFAULT_ITEM_COLOR = "#3F3F3F";
	private static final String DEFAULT_ITEM_BORDER_STYLE = "1px solid #c0c0c0";
	private static final String DEFAULT_ITEM_BORDER_STYLE_HOVER = "1px solid #FFFFCC";

	private Boolean isSelected = Boolean.FALSE;

	public VideoPlayerRecord(final RecordingDto recording) {
		super(recording.getName());
		
		setWidth100();
		setHeight(50);
		setBackgroundColor(DEFAULT_ITEM_COLOR);
		setBorder(DEFAULT_ITEM_BORDER_STYLE);
		setAlign(Alignment.CENTER);

		addMouseOverHandler(new MouseOverHandler() {

			@Override
			public void onMouseOver(MouseOverEvent event) {
				if (!isSelected()) {
					setBorder(DEFAULT_ITEM_BORDER_STYLE_HOVER);
					setBackgroundColor(DEFAULT_ITEM_COLOR_HOVER);
				}
			}
		});
		addMouseOutHandler(new MouseOutHandler() {

			@Override
			public void onMouseOut(MouseOutEvent event) {
				if (!isSelected()) {
					setBorder(DEFAULT_ITEM_BORDER_STYLE);
					setBackgroundColor(DEFAULT_ITEM_COLOR);
				}
			}
		});
	}

	@Override
	public void setSelected(final Boolean selected) {
		this.isSelected = selected;
	}

	@Override
	public Boolean isSelected() {
		return isSelected;
	}
	
	public void select(){
		setBorder(DEFAULT_ITEM_BORDER_STYLE);
		setBackgroundColor("#999999");
	}

	public void deselect(){
		setBackgroundColor(DEFAULT_ITEM_COLOR);
		setBorder(DEFAULT_ITEM_BORDER_STYLE);
	}
}
