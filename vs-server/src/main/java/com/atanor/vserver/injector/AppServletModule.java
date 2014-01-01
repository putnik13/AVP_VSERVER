package com.atanor.vserver.injector;

import java.util.Map;

import org.atmosphere.cpr.AtmosphereServlet;

import com.atanor.vserver.async.SnapshotResource;
import com.atanor.vserver.servlet.ConfigServlet;
import com.atanor.vserver.servlet.PresentationServlet;
import com.atanor.vserver.servlet.RecordingServlet;
import com.atanor.vserver.servlet.SessionCloseServlet;
import com.atanor.vserver.servlet.SessionOpenServlet;
import com.google.common.collect.Maps;
import com.google.inject.Scopes;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.ServletModule;

public class AppServletModule extends ServletModule {

	private static final String BASE_ADMIN_URL = "/VsAdmin";
	private static final String BASE_CLIENT_URL = "/VsClient";

	@Override
	protected void configureServlets() {
		// PersistFilter provides a new instance of EntityManager for each
		// request to the servlet container (Open Session In View pattern)
		install(new JpaPersistModule("VSERVER-JPA"));
		filter("/*").through(PersistFilter.class);

		bind(SnapshotResource.class);
		bind(AtmosphereServlet.class).in(Scopes.SINGLETON);

		final Map<String, String> params = Maps.newHashMap();
		params.put("org.atmosphere.cpr.packages", "com.atanor.vserver.async");
		serve("/atmosphere/*").with(AtmosphereServlet.class, params);
		serve(BASE_CLIENT_URL + "/open").with(SessionOpenServlet.class);
		serve(BASE_CLIENT_URL + "/close").with(SessionCloseServlet.class);

		serve(BASE_ADMIN_URL + "/recording").with(RecordingServlet.class);
		serve(BASE_ADMIN_URL + "/presentation").with(PresentationServlet.class);
		serve(BASE_ADMIN_URL + "/config").with(ConfigServlet.class);
	}

}
