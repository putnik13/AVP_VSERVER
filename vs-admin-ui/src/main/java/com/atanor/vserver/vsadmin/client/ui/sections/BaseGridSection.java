package com.atanor.vserver.vsadmin.client.ui.sections;

import com.atanor.vserver.vsadmin.client.ui.UiUtils;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public abstract class BaseGridSection extends BaseSection {

	protected Canvas createSnapshotBox() {
		final Canvas canvas = new Canvas();
		canvas.setWidth(UiUtils.SNAPSHOT_WIDTH);
		canvas.setHeight(UiUtils.SNAPSHOT_HEIGHT);
		canvas.setShowEdges(true);
		canvas.setBackgroundColor("black");
		return canvas;
	}

	protected Img createToolbarImage(final String imgSource, final String tooltip) {
		final Img img = new Img();
		img.setSrc(imgSource);
		img.setTooltip(tooltip);
		img.setWidth(UiUtils.TOOLBAR_ICON_SIZE);
		img.setHeight(UiUtils.TOOLBAR_ICON_SIZE);

		return img;
	}

	protected Canvas wrape(final Img image) {
		Canvas canvas = new Canvas();
		canvas.setWidth(UiUtils.TOOLBAR_ICON_SIZE_HOVER);
		canvas.setHeight(UiUtils.TOOLBAR_ICON_SIZE_HOVER);
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
