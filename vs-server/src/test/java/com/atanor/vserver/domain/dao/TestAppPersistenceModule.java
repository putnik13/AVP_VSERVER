package com.atanor.vserver.domain.dao;

import com.atanor.vserver.injector.AppPersistenceModule;
import com.google.inject.persist.jpa.JpaPersistModule;

public class TestAppPersistenceModule extends AppPersistenceModule {

	@Override
	protected void configure() {
		install(new JpaPersistModule("TEST-VSERVER-JPA"));
		bind(TestJPAInitializer.class).asEagerSingleton();
		super.configure();
	}

}
