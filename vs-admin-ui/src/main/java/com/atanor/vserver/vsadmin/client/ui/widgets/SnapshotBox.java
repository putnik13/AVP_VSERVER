package com.atanor.vserver.vsadmin.client.ui.widgets;

import com.atanor.vserver.common.Constants;
import com.atanor.vserver.common.entity.Snapshot;
import com.smartgwt.client.widgets.AnimationCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;

public class SnapshotBox extends Canvas {

	private static final String BACKGROUND_SRC = "black.png";

	private Img topImg;
	private Img backgroundImg;
	private String source;

	public SnapshotBox() {
		setWidth(Constants.SNAPSHOT_WIDTH);
		setHeight(Constants.SNAPSHOT_HEIGHT);
		setShowEdges(true);
		setBackgroundColor("black");
		
		backgroundImg = new Img();
		backgroundImg.setWidth100();
		backgroundImg.setHeight100();

		topImg = new Img();
		topImg.setWidth100();
		topImg.setHeight100();
		topImg.setAnimateTime(Constants.SNAPSHOT_ANIMATION_TIME);

		addChild(backgroundImg);
		addChild(topImg);
	}

	public void addSnapshot(final Snapshot snapshot) {
		source = "data:image/png;base64," + snapshot.getEncodedImage();

		topImg.animateFade(0, new AnimationCallback() {

			@Override
			public void execute(boolean earlyFinish) {
				topImg.setSrc(source);
				topImg.setOpacity(100);
			}
		});

		backgroundImg.setSrc(source);
	}

	public void clean() {
		source = null;
		topImg.setSrc(BACKGROUND_SRC);
		backgroundImg.setSrc(BACKGROUND_SRC);
	}

}
