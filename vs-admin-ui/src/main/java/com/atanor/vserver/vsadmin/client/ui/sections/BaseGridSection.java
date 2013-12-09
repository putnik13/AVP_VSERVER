package com.atanor.vserver.vsadmin.client.ui.sections;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.MouseOutEvent;
import com.smartgwt.client.widgets.events.MouseOutHandler;
import com.smartgwt.client.widgets.events.MouseOverEvent;
import com.smartgwt.client.widgets.events.MouseOverHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public abstract class BaseGridSection extends BaseSection {

	protected Canvas createSnapshotBox() {
		final Canvas canvas = new Canvas();
		canvas.setWidth(SNAPSHOT_WIDTH);
		canvas.setHeight(SNAPSHOT_HEIGHT);
		canvas.setShowEdges(true);
		canvas.setBackgroundColor("black");
		return canvas;
	}

	protected Img createToolbarImage(final String imgSource, final String tooltip) {
		final Img img = new Img();
		img.setSrc(imgSource);
		img.setTooltip(tooltip);
		img.setWidth(TOOLBAR_ICON_SIZE);
		img.setHeight(TOOLBAR_ICON_SIZE);
		img.addMouseOverHandler(new MouseOverHandler() {

			@Override
			public void onMouseOver(MouseOverEvent event) {
				img.animateResize(TOOLBAR_ICON_SIZE_HOVER, TOOLBAR_ICON_SIZE_HOVER, null, 100);
			}
		});
		img.addMouseOutHandler(new MouseOutHandler() {

			@Override
			public void onMouseOut(MouseOutEvent event) {
				img.animateResize(TOOLBAR_ICON_SIZE, TOOLBAR_ICON_SIZE);
			}
		});

		return img;
	}

	protected Canvas wrape(final Img image) {
		Canvas canvas = new Canvas();
		canvas.setWidth(TOOLBAR_ICON_SIZE_HOVER);
		canvas.setHeight(TOOLBAR_ICON_SIZE_HOVER);
		canvas.addChild(image);
		return canvas;
	}

	protected boolean isAnyRecordSelected(final ListGrid listGrid) {
		for (final ListGridRecord record : listGrid.getRecords()) {
			if (listGrid.isSelected(record)) {
				return true;
			}
		}
		return false;
	}

}
