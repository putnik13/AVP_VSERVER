package com.atanor.vserver.vsadmin.client.ui.widgets;

import com.google.gwt.dom.client.VideoElement;
import com.smartgwt.client.widgets.WidgetCanvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.videojs.client.VideoPlayer;

public class VideoCanvas extends VLayout {

	public VideoCanvas() {
		setHeight100();
		setWidth100();

		final VideoPlayer player = new VideoPlayer(600, 400){

			@Override
			protected void onLoad() {
				super.onLoad();
				play();
			}
		};
		player.addSource("http://video-js.zencoder.com/oceans-clip.mp4", VideoElement.TYPE_MP4);
		final WidgetCanvas playerCanvas = new WidgetCanvas(player);

		final VLayout recordingsLayout = new VLayout();
		recordingsLayout.setHeight(400);
		recordingsLayout.setWidth(250);
		recordingsLayout.setBackgroundColor("red");

		final HLayout layout = new HLayout();
		layout.setWidth100();
		layout.addMembers(new LayoutSpacer(), playerCanvas, recordingsLayout, new LayoutSpacer());

		addMembers(new LayoutSpacer(), layout, new LayoutSpacer());
	}

}
