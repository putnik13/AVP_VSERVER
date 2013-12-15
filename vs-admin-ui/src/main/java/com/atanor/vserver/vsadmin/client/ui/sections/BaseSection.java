package com.atanor.vserver.vsadmin.client.ui.sections;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.widgets.layout.VLayout;

public abstract class BaseSection extends VLayout {

	public static final DateTimeFormat df = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");

	public static final Integer SNAPSHOT_WIDTH = 265;
	public static final Integer SNAPSHOT_HEIGHT = 200;
	public static final Integer TOOLBAR_ICON_SIZE = 30;
	public static final Integer TOOLBAR_ICON_SIZE_HOVER = 35;
	public static final Integer TOOLBAR_HEIGHT = 40;
	
	public BaseSection() {
		setWidth100();
		setHeight100();
	}
}
