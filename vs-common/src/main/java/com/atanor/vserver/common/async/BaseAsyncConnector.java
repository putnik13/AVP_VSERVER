package com.atanor.vserver.common.async;

import java.util.List;

import org.atmosphere.gwt20.client.Atmosphere;
import org.atmosphere.gwt20.client.AtmosphereMessageHandler;
import org.atmosphere.gwt20.client.AtmosphereRequest;
import org.atmosphere.gwt20.client.AtmosphereRequestConfig;
import org.atmosphere.gwt20.client.AtmosphereResponse;

import com.atanor.vserver.common.json.JsonSerializer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.SerializationException;

public abstract class BaseAsyncConnector {

	private AtmosphereRequest asyncRequest;

	protected void connectWebsocket() {

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
				final List<Object> messages = response.getMessages();
				for (Object message : messages) {
					handleMessage(message);
				}
			}
		});

		final Atmosphere asyncClient = Atmosphere.create();
		asyncRequest = asyncClient.subscribe(jsonRequestConfig);
	}

	protected abstract void handleMessage(Object message);

	public void pushMessage(final Object message) {
		if (asyncRequest != null) {
			try {
				asyncRequest.push(message);
			} catch (SerializationException e) {
				e.printStackTrace();
			}
		}
	}
	
}
