package com.atanor.vserver.vsadmin.client.ui.widgets;

import java.util.List;

import com.atanor.vserver.common.rpc.dto.RecordingDto;
import com.google.common.collect.Lists;
import com.google.gwt.dom.client.VideoElement;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.WidgetCanvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.videojs.client.VideoPlayer;

public class VideoCanvas extends VLayout {

	private final List<VideoPlayerRecord> records;
	
	public VideoCanvas(final List<RecordingDto> recordings) {
		this.records = convert(recordings);
		
		setHeight100();
		setWidth100();

		final VideoPlayer player = new VideoPlayer(600, 400) {

			@Override
			protected void onLoad() {
				super.onLoad();
				play();
			}
		};
		player.addSource("http://video-js.zencoder.com/oceans-clip.mp4", VideoElement.TYPE_MP4);
		// player.addSource("file:///D:/tmp/recordings/from_serega.mp4",
		// VideoElement.TYPE_MP4);
		// player.addSource("http://localhost:8080/recordings/from_serega4.mp4",
		// VideoElement.TYPE_MP4);
		final WidgetCanvas playerCanvas = new WidgetCanvas(player);

		final VLayout recordingsLayout = new VLayout();
		recordingsLayout.setHeight(400);
		recordingsLayout.setWidth(250);
		recordingsLayout.setBackgroundColor("#1F1F1F");
		recordingsLayout.setOverflow(Overflow.AUTO);

		recordingsLayout.addMember(createRecording("RECORDING-20131224-004129.mp4"));
		recordingsLayout.addMember(createRecording("RECORDING-20131224-020229.mp4"));
		recordingsLayout.addMember(createRecording("RECORDING-20131225-001319.mp4"));
		recordingsLayout.addMember(createRecording("RECORDING-20131223-230700.mp4"));
		recordingsLayout.addMember(createRecording("RECORDING-20131224-004129.mp4"));
		recordingsLayout.addMember(createRecording("RECORDING-20131224-020229.mp4"));
		recordingsLayout.addMember(createRecording("RECORDING-20131225-001319.mp4"));
		recordingsLayout.addMember(createRecording("RECORDING-20131223-230700.mp4"));
		recordingsLayout.addMember(createRecording("RECORDING-20131224-004129.mp4"));
		recordingsLayout.addMember(createRecording("RECORDING-20131224-020229.mp4"));
		recordingsLayout.addMember(createRecording("RECORDING-20131225-001319.mp4"));
		recordingsLayout.addMember(createRecording("RECORDING-20131223-230700.mp4"));

		recordingsLayout.setPadding(2);
		recordingsLayout.setMembersMargin(2);

		final HLayout layout = new HLayout();
		layout.setWidth100();
		layout.addMembers(new LayoutSpacer(), playerCanvas, recordingsLayout, new LayoutSpacer());

		addMembers(new LayoutSpacer(), layout, new LayoutSpacer());
	}

	private Label createRecording(final String recordingName) {
		return new VideoPlayerRecord(recordingName);
	}
	
	private List<VideoPlayerRecord> convert(final List<RecordingDto> recordings){
		List<VideoPlayerRecord> records = Lists.newArrayList();
		for (RecordingDto recording : recordings) {
			records.add(convert(recording));
		}
		return records;
	}

	private VideoPlayerRecord convert(final RecordingDto recording) {
		return new VideoPlayerRecord(recording);
	}
}
