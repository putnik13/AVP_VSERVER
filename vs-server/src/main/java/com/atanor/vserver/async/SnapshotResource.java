package com.atanor.vserver.async;

import java.io.BufferedReader;
import java.io.IOException;

import javax.inject.Singleton;

import org.atmosphere.client.TrackMessageSizeInterceptor;
import org.atmosphere.config.service.Disconnect;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.config.service.Post;
import org.atmosphere.config.service.Ready;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor;
import org.atmosphere.interceptor.SuspendTrackerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedService(path = "/atmosphere/async", interceptors = {
/**
 * Handle lifecycle for us
 */
AtmosphereResourceLifecycleInterceptor.class,
/**
 * Send to the client the size of the message to prevent serialization error.
 */
TrackMessageSizeInterceptor.class,
/**
 * Make sure our {@link AtmosphereResourceEventListener#onSuspend} is only
 * called once for transport that reconnect on every requests.
 */
SuspendTrackerInterceptor.class })
@Singleton
public class SnapshotResource {

	static final Logger logger = LoggerFactory.getLogger(SnapshotResource.class);

	@Ready
	public void onReady(final AtmosphereResource r) {
		logger.info("GET request received, supported transport: " + r.transport());

		AsyncConnector.addResource(r);
		AsyncConnector.broadcastNotification("Browser UUID: " + r.uuid() + " connected.");
	}

	@Disconnect
	public void disconnected(AtmosphereResourceEvent event) {
		if (event.isCancelled()) {
			logger.info("User:" + event.getResource().uuid() + " unexpectedly disconnected");
		} else if (event.isClosedByClient()) {
			logger.info("User:" + event.getResource().uuid() + " closed the connection");
		}
	}

	@Post
	public void post(AtmosphereResource r) {
		logger.info("POST received with transport + " + r.transport());

		final StringBuilder data = new StringBuilder();
		try {
			final BufferedReader requestReader = r.getRequest().getReader();
			char[] buf = new char[5120];
			int read = -1;
			while ((read = requestReader.read(buf)) > 0) {
				data.append(buf, 0, read);
			}
			logger.info("Received json message from client: " + data.toString());

			AsyncConnector.handleReceivedMessage(data.toString());

		} catch (IOException e) {
			logger.error("Error during processing json message from client", e);
		}

	}

}
