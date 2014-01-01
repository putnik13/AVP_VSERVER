package com.atanor.vserver.vsclient.client.async;

import java.util.List;

import org.atmosphere.gwt20.client.Atmosphere;
import org.atmosphere.gwt20.client.AtmosphereMessageHandler;
import org.atmosphere.gwt20.client.AtmosphereRequestConfig;
import org.atmosphere.gwt20.client.AtmosphereResponse;

import com.atanor.vserver.common.entity.Notification;
import com.atanor.vserver.common.entity.Signal;
import com.atanor.vserver.common.entity.Snapshot;
import com.atanor.vserver.vsclient.client.Client;
import com.atanor.vserver.vsclient.client.events.SessionOverEvent;
import com.atanor.vserver.vsclient.client.events.SnapshotReceivedEvent;
import com.atanor.vserver.vsclient.client.json.JsonSerializer;
import com.google.gwt.core.client.GWT;
import com.smartgwt.client.util.SC;

public class AsyncConnector {

	public static void connect() {

		final JsonSerializer jsonSerializer = GWT.create(JsonSerializer.class);
		final AtmosphereRequestConfig jsonRequestConfig = AtmosphereRequestConfig.create(jsonSerializer);

		jsonRequestConfig.setUrl(GWT.getHostPageBaseURL() + "atmosphere/async");
		jsonRequestConfig.setContentType("application/json; charset=UTF-8");
		jsonRequestConfig.setTransport(AtmosphereRequestConfig.Transport.WEBSOCKET);
		jsonRequestConfig.setFallbackTransport(AtmosphereRequestConfig.Transport.STREAMING);
		jsonRequestConfig.setFlags(AtmosphereRequestConfig.Flags.enableProtocol);
		jsonRequestConfig.setFlags(AtmosphereRequestConfig.Flags.trackMessageLength);
		jsonRequestConfig.setMaxReconnectOnClose(5);

		jsonRequestConfig.setMessageHandler(new AtmosphereMessageHandler() {

			@Override
			public void onMessage(AtmosphereResponse response) {
				List<Object> messages = response.getMessages();
				for (Object message : messages) {
					handleMessage(message);
				}
			}
		});

		final Atmosphere asyncClient = Atmosphere.create();
		asyncClient.subscribe(jsonRequestConfig);
	}

	protected static void handleMessage(Object message) {

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

	private static void handleSignalMessage(final Signal message) {
		if (message == Signal.SESSION_START) {
			Client.getEventBus().fireEvent(new SessionOverEvent());
		}
		else if (message == Signal.SESSION_OVER) {
			Client.getEventBus().fireEvent(new SessionOverEvent());
		}
	}
}
