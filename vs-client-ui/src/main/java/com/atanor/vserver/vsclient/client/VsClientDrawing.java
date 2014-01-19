package com.atanor.vserver.vsclient.client;

import org.sgx.raphael4gwt.raphael.Paper;
import org.sgx.raphael4gwt.raphael.PathCmd;
import org.sgx.raphael4gwt.raphael.Rect;
import org.sgx.raphael4gwt.raphael.base.Attrs;
import org.sgx.raphael4gwt.raphael.event.MouseEventListener;
import org.sgx.raphael4gwt.raphael.widget.PaperWidget;

import com.atanor.vserver.vsclient.client.ui.widgets.DrawingBox;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class VsClientDrawing implements EntryPoint {

	private PathCmd pathCmd;
	private boolean mousePressed;
	private Canvas mainCanvas;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		// we will use a simple panel in which to put our aphael paper:
		mainCanvas = new Canvas();
		mainCanvas.setWidth100();
		mainCanvas.setHeight100();
		mainCanvas.setBackgroundColor("grey");

		IButton button = new IButton("Show Drawing");
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				new DrawingBox().show();
			}

		});

		mainCanvas.addChild(button);
		RootPanel.get().add(mainCanvas);
	}

//	private void showDrawingPane() {
//		final Canvas canvas = new Canvas();
//		canvas.setWidth100();
//		canvas.setHeight100();
//		canvas.show();
//
//		int w = Window.getClientWidth();
//		int h = Window.getClientHeight();
//		final PaperWidget paperWidget = new PaperWidget(w, h);
//		paperWidget.setSize("100%", "100%");
//
//		final Paper masterPaper = paperWidget.getPaper();
//		Rect masterBackground = masterPaper.rect(0, 0, w, h);
//		masterBackground.attr(Attrs.create().stroke("none").fill("").opacity(0d));
//
//		final Attrs drawAttrs = Attrs.create().strokeWidth(20).stroke("yellow").strokeLinecap("round");
//
//		Event.addNativePreviewHandler(new NativePreviewHandler() {
//			public void onPreviewNativeEvent(final NativePreviewEvent event) {
//
//				switch (event.getTypeInt()) {
//				case Event.ONMOUSEDOWN:
//					// case Event.ONTOUCHSTART:
//					mousePressed = true;
//					break;
//				case Event.ONMOUSEUP:
//					// case Event.ONTOUCHEND:
//					mousePressed = false;
//					pathCmd = null;
//					break;
//				default:
//					// not interested in other events
//				}
//			}
//		});
//
//		masterBackground.mouseMove(new MouseEventListener() {
//
//			@Override
//			public void notifyMouseEvent(NativeEvent e) {
//				if (mousePressed) {
//					final int x = e.getClientX();
//					final int y = e.getClientY();
//					if (!hasPathCmd()) {
//						pathCmd = new PathCmd(x, y);
//					}
//
//					pathCmd.L(x, y);
//					masterPaper.path(pathCmd.toPathString()).attr(drawAttrs);
//					pathCmd = pathCmd.M(x, y);
//				}
//			}
//		});
//
//		canvas.addChild(paperWidget);
//	}
//
//	private boolean hasPathCmd() {
//		return pathCmd != null;
//	}

}
