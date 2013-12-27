package com.atanor.vserver.vsadmin.client.ui.widgets;

import com.google.gwt.dom.client.VideoElement;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.WidgetCanvas;
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.DoubleClickHandler;
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
		//player.addSource("http://video-js.zencoder.com/oceans-clip.mp4", VideoElement.TYPE_MP4);
		//player.addSource("file:///D:/tmp/recordings/from_serega.mp4", VideoElement.TYPE_MP4);
		player.addSource("http://localhost:8080/recordings/from_serega4.mp4", VideoElement.TYPE_MP4);
		final WidgetCanvas playerCanvas = new WidgetCanvas(player);

		final VLayout recordingsLayout = new VLayout();
		recordingsLayout.setHeight(400);
		recordingsLayout.setWidth(250);
		recordingsLayout.setBackgroundColor("#1F1F1F");
		//recordingsLayout.setOverflow(Overflow.SCROLL);
		
		recordingsLayout.addMember(createRecording("RECORDING-20131224-004129.mp4"));
		recordingsLayout.addMember(createRecording("RECORDING-20131224-020229.mp4"));
		recordingsLayout.addMember(createRecording("RECORDING-20131225-001319.mp4"));
		recordingsLayout.addMember(createRecording("RECORDING-20131223-230700.mp4"));
		recordingsLayout.addMember(createRecording("RECORDING-20131224-004129.mp4"));
//		recordingsLayout.addMember(createRecording("RECORDING-20131224-020229.mp4"));
//		recordingsLayout.addMember(createRecording("RECORDING-20131225-001319.mp4"));
//		recordingsLayout.addMember(createRecording("RECORDING-20131223-230700.mp4"));
//		recordingsLayout.addMember(createRecording("RECORDING-20131224-004129.mp4"));
//		recordingsLayout.addMember(createRecording("RECORDING-20131224-020229.mp4"));
//		recordingsLayout.addMember(createRecording("RECORDING-20131225-001319.mp4"));
//		recordingsLayout.addMember(createRecording("RECORDING-20131223-230700.mp4"));
		
		recordingsLayout.setPadding(2);
		recordingsLayout.setMembersMargin(2);
		
		final HLayout layout = new HLayout();
		layout.setWidth100();
		layout.addMembers(new LayoutSpacer(), playerCanvas, recordingsLayout, new LayoutSpacer());

		addMembers(new LayoutSpacer(), layout, new LayoutSpacer());
	}

	private Label createRecording(final String recordingName){
		final Label label = new Label(recordingName);  
        label.setWidth100();
        label.setHeight(50);
        label.setBackgroundColor("#3F3F3F");  
        label.setBorder("1px solid #c0c0c0");
        label.setAlign(Alignment.CENTER);
        label.addDoubleClickHandler(new DoubleClickHandler() {
			
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				label.setSelected(true);
				//label.setBackgroundColor("#999999");
				label.setBackgroundColor("grey");
			}
		});
        return label;
	}
}
