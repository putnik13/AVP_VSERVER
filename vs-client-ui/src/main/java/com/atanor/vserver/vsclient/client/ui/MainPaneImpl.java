package com.atanor.vserver.vsclient.client.ui;

import com.atanor.vserver.common.entity.Snapshot;
import com.atanor.vserver.vsclient.client.presenter.MainPanePresenter;
import com.google.common.primitives.Ints;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.AnimationCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ResizedEvent;
import com.smartgwt.client.widgets.events.ResizedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;

public class MainPaneImpl extends HLayout implements MainPane {

	private static final int ANIMATION_TIME = 2000;

	private MainPanePresenter presenter;
	
	private final Canvas snapshotLayer;
	private final Canvas sessionInactiveLayer;
	private Label sessionInactiveLabel;
	
	private Img topImg;
	private Img backgroundImg;

	private String currentImgWidth;
	private String currentImgHeight;
	
	public MainPaneImpl() {
		setWidth100();
		setHeight100();

		snapshotLayer = new Canvas();
		snapshotLayer.setWidth100();
		snapshotLayer.setHeight100();
		snapshotLayer.setShowEdges(false);
		snapshotLayer.setOverflow(Overflow.HIDDEN);
		snapshotLayer.setBackgroundColor("lightgrey");
		snapshotLayer.addResizedHandler(new ResizedHandler() {

			@Override
			public void onResized(ResizedEvent event) {
				currentImgWidth = null;
				currentImgHeight = null;
			}
		});

		topImg = new Img();
		topImg.setAnimateTime(ANIMATION_TIME);

		backgroundImg = new Img();

		sessionInactiveLayer = createSessionInactiveLayer();

		snapshotLayer.addChild(backgroundImg);
		snapshotLayer.addChild(topImg);

		addChild(snapshotLayer);
		addChild(sessionInactiveLayer);
	}

	@Override
	public void setPresenter(final MainPanePresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void addSnapshot(final Snapshot snapshot) {

		final String source = "data:image/png;base64," + snapshot.getEncodedImage();

		if (isSnapshotSizeChanged(snapshot.getWidth(), snapshot.getHeight())) {
			this.currentImgWidth = snapshot.getWidth();
			this.currentImgHeight = snapshot.getHeight();
			adjustImage(topImg);
			adjustImage(backgroundImg);

			sessionInactiveLayer.sendToBack();
		}

		if (topImg.getSrc() == null) {
			topImg.setSrc(source);
		} else {
			topImg.animateFade(0, new AnimationCallback() {

				@Override
				public void execute(boolean earlyFinish) {
					topImg.setSrc(source);
					topImg.setOpacity(100);
				}
			});

			backgroundImg.setSrc(source);
		}
	}

	private boolean isSnapshotSizeChanged(final String width, final String height) {
		return currentImgWidth == null || currentImgHeight == null || !currentImgWidth.equals(width)
				|| !currentImgHeight.equals(height);
	}

	private void adjustImage(final Img img) {
		final Long imageWidth = Long.valueOf(currentImgWidth);
		final Long imageHeight = Long.valueOf(currentImgHeight);

		adjustSize(img, imageWidth, imageHeight);
		adjustPosition(img);
	}

	private void adjustSize(final Img image, final Long originWidth, final Long originHeight) {

		Double vPadding = 1.0d;
		Long imageHeight = null;
		Long imageWidth = null;

		do {
			imageHeight = Math.round(getElement().getClientHeight() * vPadding);
			final Double scaleFactor = imageHeight.doubleValue() / originHeight.doubleValue();
			imageWidth = Math.round(scaleFactor * originWidth.doubleValue());

			vPadding -= 0.01d;
		} while (imageWidth > getElement().getClientWidth());

		image.setWidth(Ints.checkedCast(imageWidth));
		image.setHeight(Ints.checkedCast(imageHeight));
	}

	private void adjustPosition(final Img image) {
		final Long leftOffset = Math.round((getElement().getClientWidth() - image.getWidth()) / 2d);
		image.setLeft(Ints.checkedCast(leftOffset));
		final Long topOffset = Math.round((getElement().getClientHeight() - image.getHeight()) / 2d);
		image.setTop(Ints.checkedCast(topOffset));
	}

	private Canvas createSessionInactiveLayer() {
		final Canvas layer = new Canvas();
		layer.setWidth100();
		layer.setHeight100();
		layer.setOverflow(Overflow.HIDDEN);
		
		// ========================
		final VLayout labelLayout = new VLayout();
		labelLayout.setWidth100();
		labelLayout.setHeight100();

		final HLayout spacer = new HLayout();
		spacer.setHeight(200);

		final HLayout labelLayer = new HLayout();
		labelLayer.setWidth100();
		labelLayer.setHeight(100);
		labelLayer.setAlign(Alignment.CENTER);
		
		sessionInactiveLabel = new Label();
		sessionInactiveLabel.setWidth(300);
		setSessionInactiveLabelContent("Session is not started..");
		labelLayer.addMember(sessionInactiveLabel);
		
		labelLayout.addMembers(spacer, labelLayer, new LayoutSpacer());
		// ========================

		final Img img = new Img();
		img.setSrc("session_inactive.png");
		img.setWidth100();
		img.setHeight100();

		layer.addChild(img);
		layer.addChild(labelLayout);
		
		return layer;
	}
	
	private void setSessionInactiveLabelContent(final String content) {
		sessionInactiveLabel.setContents("<font size='5' color='white'>" + content + "</font>");
	}

	@Override
	public void onSessionStart() {
		snapshotLayer.bringToFront();
	}
	
	@Override
	public void onSessionOver() {
		setSessionInactiveLabelContent("Session is over..");
		sessionInactiveLayer.bringToFront();
	}
}
