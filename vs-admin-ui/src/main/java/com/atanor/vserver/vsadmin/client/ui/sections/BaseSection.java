package com.atanor.vserver.vsadmin.client.ui.sections;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.widgets.layout.VLayout;

public abstract class BaseSection extends VLayout {

	public static final DateTimeFormat df = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");

	public BaseSection() {
		setWidth100();
		setHeight100();
	}
}
