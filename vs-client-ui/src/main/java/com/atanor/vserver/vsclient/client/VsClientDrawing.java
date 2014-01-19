package com.atanor.vserver.vsclient.client;

import com.atanor.vserver.vsclient.client.ui.widgets.DrawingBox;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class VsClientDrawing implements EntryPoint {

	private Canvas canvas;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		// we will use a simple panel in which to put our aphael paper:
		canvas = new Canvas();
		canvas.setWidth100();
		canvas.setHeight100();
		canvas.setBackgroundColor("grey");

		IButton button = new IButton("Show Drawing");
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				new DrawingBox().show();
			}
		});

		canvas.addChild(button);
		RootPanel.get().add(canvas);
	}

}
