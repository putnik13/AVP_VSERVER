package com.atanor.vserver.vsclient.client.async;

import com.atanor.vserver.common.async.BaseAsyncConnector;
import com.atanor.vserver.common.async.events.SessionOverEvent;
import com.atanor.vserver.common.async.events.SessionStartEvent;
import com.atanor.vserver.common.async.events.SnapshotReceivedEvent;
import com.atanor.vserver.common.entity.Notification;
import com.atanor.vserver.common.entity.Signal;
import com.atanor.vserver.common.entity.Snapshot;
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

		if (message instanceof Snapshot) {
			final Snapshot snapshot = (Snapshot) message;
			Client.getEventBus().fireEvent(new SnapshotReceivedEvent(snapshot));
		} else if (message instanceof Notification) {
			final Notification notification = (Notification) message;
			SC.say("NOTIFICATION:" + notification.getMessage());
		} else if (message instanceof Signal) {
			handleSignalMessage((Signal) message);
		} else {
			SC.warn((String) message);
		}
	}

	private void handleSignalMessage(final Signal message) {
		if (message == Signal.SESSION_START) {
			Client.getEventBus().fireEvent(new SessionStartEvent());
		} else if (message == Signal.SESSION_OVER) {
			Client.getEventBus().fireEvent(new SessionOverEvent());
		}
	}

}
