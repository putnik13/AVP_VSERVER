package com.atanor.vserver.vsadmin.client.async;

import com.atanor.vserver.common.async.BaseAsyncConnector;
import com.atanor.vserver.common.entity.Snapshot;

public class AsyncConnector extends BaseAsyncConnector {

	public void connect() {
		connectWebsocket();
	}

	@Override
	protected void handleMessage(Object message) {

		if (message instanceof Snapshot) {
			final Snapshot snapshot = (Snapshot) message;
			//Client.getEventBus().fireEvent(new SnapshotReceivedEvent(snapshot));
		} 
	}

//	private void handleSignalMessage(final Signal message) {
//		if (message == Signal.SESSION_START) {
//			Client.getEventBus().fireEvent(new SessionOverEvent());
//		} else if (message == Signal.SESSION_OVER) {
//			Client.getEventBus().fireEvent(new SessionOverEvent());
//		}
//	}

}
