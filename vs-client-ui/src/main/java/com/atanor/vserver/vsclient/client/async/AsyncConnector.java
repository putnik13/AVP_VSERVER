package com.atanor.vserver.vsclient.client.async;

import com.atanor.vserver.common.async.BaseAsyncConnector;
import com.atanor.vserver.common.async.events.PresentationOverEvent;
import com.atanor.vserver.common.async.events.PresentationSnapshotReceivedEvent;
import com.atanor.vserver.common.async.events.PresentationStartEvent;
import com.atanor.vserver.common.entity.Notification;
import com.atanor.vserver.common.entity.Signal;
import com.atanor.vserver.common.entity.SignalMessage;
import com.atanor.vserver.common.entity.Snapshot;
import com.atanor.vserver.common.entity.Snapshot.TYPE;
import com.atanor.vserver.vsclient.client.Client;
import com.smartgwt.client.util.SC;

public class AsyncConnector extends BaseAsyncConnector {

	/**
	 * SingletonHolder is loaded on the first execution of
	 * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
	 * not before.
	 */
	private static class SingletonHolder {
		public static final AsyncConnector INSTANCE = new AsyncConnector();
	}

	public static AsyncConnector getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public static void connect() {
		getInstance().connectWebsocket();
	}

	@Override
	protected void handleMessage(Object message) {

		if ((message instanceof Snapshot) && isPresentationSnapshot((Snapshot) message)) {
			Client.getEventBus().fireEvent(new PresentationSnapshotReceivedEvent((Snapshot) message));
		} else if (message instanceof Notification) {
			final Notification notification = (Notification) message;
			SC.say("NOTIFICATION:" + notification.getMessage());
		} else if (message instanceof SignalMessage) {
			handleSignalMessage((SignalMessage) message);
		} else {
			SC.warn((String) message);
		}
	}

	private boolean isPresentationSnapshot(final Snapshot snapshot) {
		return TYPE.PRESENTATION == snapshot.getType();
	}

	private void handleSignalMessage(final SignalMessage message) {
		if (message.getSignal() == Signal.SESSION_START) {
			Client.getEventBus().fireEvent(new PresentationStartEvent());
		} else if (message.getSignal() == Signal.SESSION_OVER) {
			final String[] params = message.getParams();
			if (params != null && params.length > 0) {
				final String pdfFileName = message.getParams()[0];
				Client.getEventBus().fireEvent(new PresentationOverEvent(pdfFileName));
			}
		}
	}

}
