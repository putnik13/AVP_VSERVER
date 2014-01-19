package com.atanor.vserver.vsclient.client.ui.widgets;

import org.sgx.raphael4gwt.raphael.Paper;
import org.sgx.raphael4gwt.raphael.PathCmd;
import org.sgx.raphael4gwt.raphael.Rect;
import org.sgx.raphael4gwt.raphael.base.Attrs;
import org.sgx.raphael4gwt.raphael.event.MouseEventListener;
import org.sgx.raphael4gwt.raphael.widget.PaperWidget;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.smartgwt.client.widgets.Canvas;

public class DrawingBox extends Canvas {

	private PathCmd pathCmd;
	private boolean mousePressed;
	private PaperWidget paperWidget;
	
	public DrawingBox() {
		setWidth100();
		setHeight100();

		int w = Window.getClientWidth();
		int h = Window.getClientHeight();
		
		paperWidget = new PaperWidget(w, h);
		paperWidget.setSize("100%", "100%");

		final Paper masterPaper = paperWidget.getPaper();
		Rect masterBackground = masterPaper.rect(0, 0, w, h);
		masterBackground.attr(Attrs.create().stroke("none").fill("white").opacity(0d));

		final Attrs drawAttrs = Attrs.create().strokeWidth(20).stroke("yellow").strokeLinecap("round");
		
		Event.addNativePreviewHandler(new NativePreviewHandler() {
			public void onPreviewNativeEvent(final NativePreviewEvent event) {

				switch (event.getTypeInt()) {
				case Event.ONMOUSEDOWN:
					// case Event.ONTOUCHSTART:
					mousePressed = true;
					break;
				case Event.ONMOUSEUP:
					// case Event.ONTOUCHEND:
					mousePressed = false;
					pathCmd = null;
					break;
				default:
					// not interested in other events
				}
			}
		});
		
		masterBackground.mouseMove(new MouseEventListener() {

			@Override
			public void notifyMouseEvent(NativeEvent e) {
				if (mousePressed) {
					final int x = e.getClientX();
					final int y = e.getClientY();
					if (!hasPathCmd()) {
						pathCmd = new PathCmd(x, y);
					}

					pathCmd.L(x, y);
					masterPaper.path(pathCmd.toPathString()).attr(drawAttrs);
					pathCmd = pathCmd.M(x, y);
				}
			}
		});
		
	}
	
	private boolean hasPathCmd() {
		return pathCmd != null;
	}

	@Override
	public void show() {
		super.show();
		addChild(paperWidget);
	}
	
}
