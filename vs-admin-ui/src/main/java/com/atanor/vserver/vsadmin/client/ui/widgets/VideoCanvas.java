package com.atanor.vserver.vsadmin.client.ui.widgets;

import java.util.List;

import com.atanor.vserver.common.rpc.dto.RecordingDto;
import com.google.common.collect.Lists;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.VideoElement;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.WidgetCanvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.videojs.client.VideoPlayer;

public class VideoCanvas extends VLayout implements LoadVideoHandler {

	private static final int PLAYER_WIDTH = 600;
	private static final int PLAYER_HEIGHT = 400;
	private static final String RECORDINGS_URL = GWT.getHostPageBaseURL() + "recordings/";

	private final List<VideoPlayerRecord> records;
	private final HLayout playerLayout;
	private Canvas playerCanvas;

	public VideoCanvas(final List<RecordingDto> recordings) {
		this.records = convert(recordings);

		setHeight100();
		setWidth100();

		playerCanvas = createPlayerCanvas(recordings.get(0).getName());

		final VLayout recordingsLayout = new VLayout();
		recordingsLayout.setWidth(250);
		recordingsLayout.setHeight(PLAYER_HEIGHT);
		recordingsLayout.setBackgroundColor("#1F1F1F");
		recordingsLayout.setOverflow(Overflow.AUTO);

		recordingsLayout.addMembers(records.toArray(new VideoPlayerRecord[] {}));

		recordingsLayout.setPadding(2);
		recordingsLayout.setMembersMargin(2);

		playerLayout = new HLayout();
		playerLayout.setWidth100();
		playerLayout.addMembers(new LayoutSpacer(), playerCanvas, recordingsLayout, new LayoutSpacer());

		addMembers(new LayoutSpacer(), playerLayout, new LayoutSpacer());
	}

	private List<VideoPlayerRecord> convert(final List<RecordingDto> recordings) {
		final List<VideoPlayerRecord> records = Lists.newArrayList();
		for (final RecordingDto recording : recordings) {
			records.add(convert(recording));
		}
		return records;
	}

	private VideoPlayerRecord convert(final RecordingDto recording) {
		return new VideoPlayerRecord(recording, this);
	}

	@Override
	public void onLoadVideo(final String recording) {
		cleanSelected();
		cleanPlayerCanvas();
		playerCanvas = createPlayerCanvas(recording);
		playerLayout.addMember(playerCanvas, 1);
	}

	private void cleanSelected() {
		for (final VideoPlayerRecord record : records) {
			record.deselect();
		}
	}

	private void cleanPlayerCanvas() {
		if (playerCanvas != null) {
			playerCanvas.removeFromParent();
			playerCanvas.destroy();
		}
	}

	private Canvas createPlayerCanvas(final String recording) {
		final VideoPlayer player = new VideoPlayer(PLAYER_WIDTH, PLAYER_HEIGHT) {

			@Override
			protected void onLoad() {
				super.onLoad();
				play();
			}
		};
		//player.addSource(RECORDINGS_URL + recording, VideoElement.TYPE_MP4);
		player.addSource("http://video-js.zencoder.com/oceans-clip.mp4", VideoElement.TYPE_MP4);

		final Canvas canvas = new WidgetCanvas(player);
		canvas.setWidth(PLAYER_WIDTH);
		canvas.setHeight(PLAYER_HEIGHT);
		return canvas;
	}
}
