package com.atanor.vserver.vsclient.client.ui.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;

public class BackgroundCanvas extends Canvas {

	private final Label sessionInactiveLabel;
	private final Canvas pdfCanvas;
	private String pdfName;

	public BackgroundCanvas() {
		setWidth100();
		setHeight100();
		setOverflow(Overflow.HIDDEN);

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
		setLabelContent("Session is not started..");
		labelLayer.addMember(sessionInactiveLabel);

		labelLayout.addMembers(spacer, labelLayer, new LayoutSpacer());
		// ========================

		final Img pdfDownloadImg = new Img();
		pdfDownloadImg.setSrc("pdf-download.png");
		pdfDownloadImg.setWidth(100);
		pdfDownloadImg.setHeight(105);
		pdfDownloadImg.setLeft(15);
		pdfDownloadImg.setTop(15);
		pdfDownloadImg.setTooltip("Download Presentation");
		pdfDownloadImg.setCursor(Cursor.HAND);
		pdfDownloadImg.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (pdfName != null) {
					Window.open(GWT.getHostPageBaseURL() + "pdfDownload?docId=" + pdfName, "", "");
				}
			}
		});

		pdfCanvas = new Canvas();
		pdfCanvas.setWidth(130);
		pdfCanvas.setHeight(130);
		pdfCanvas.addChild(pdfDownloadImg);

		final Img backgroundImg = new Img();
		backgroundImg.setSrc("session_inactive.png");
		backgroundImg.setWidth100();
		backgroundImg.setHeight100();

		addChild(pdfCanvas);
		addChild(backgroundImg);
		addChild(labelLayout);
	}

	private void setLabelContent(final String content) {
		sessionInactiveLabel.setContents("<font size='5' color='white'>" + content + "</font>");
	}

	public void sessionStart() {
		pdfName = null;
		pdfCanvas.sendToBack();
	}

	public void sessionOver() {
		setLabelContent("Session is over..");
		pdfName = "hello.pdf";
		pdfCanvas.bringToFront();
	}
}
