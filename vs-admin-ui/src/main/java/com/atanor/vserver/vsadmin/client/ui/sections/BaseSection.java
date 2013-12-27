package com.atanor.vserver.vsadmin.client.ui.sections;

import com.atanor.vserver.vsadmin.client.ui.UiUtils;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.MouseOutEvent;
import com.smartgwt.client.widgets.events.MouseOutHandler;
import com.smartgwt.client.widgets.events.MouseOverEvent;
import com.smartgwt.client.widgets.events.MouseOverHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public abstract class BaseSection extends VLayout {

	public static final DateTimeFormat df = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");

	public BaseSection() {
		setWidth100();
		setHeight100();
	}
	
	protected Canvas createNavigateControl(final String imgSource, final String tooltip) {
		final Img img = new Img();
		img.setSrc(imgSource);
		img.setTooltip(tooltip);
		img.setWidth(UiUtils.NAVIGATE_ICON_CONTROL_SIZE);
		img.setHeight(UiUtils.NAVIGATE_ICON_CONTROL_SIZE);
		img.addMouseOverHandler(new MouseOverHandler() {

			@Override
			public void onMouseOver(MouseOverEvent event) {
				img.animateResize(UiUtils.NAVIGATE_ICON_CONTROL_SIZE_HOVER, UiUtils.NAVIGATE_ICON_CONTROL_SIZE_HOVER,
						null, 100);
			}
		});
		img.addMouseOutHandler(new MouseOutHandler() {

			@Override
			public void onMouseOut(MouseOutEvent event) {
				img.animateResize(UiUtils.NAVIGATE_ICON_CONTROL_SIZE, UiUtils.NAVIGATE_ICON_CONTROL_SIZE);
			}
		});
		
		return img;
	}
}
