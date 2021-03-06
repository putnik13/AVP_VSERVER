package com.atanor.vserver.injector;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class AppConfig extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new AppServletModule(), new AppCoreModule(), new AppPersistenceModule());
	}

}
