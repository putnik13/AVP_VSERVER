package com.atanor.vserver.injector;

import com.atanor.vserver.domain.dao.GenericDao;
import com.atanor.vserver.domain.dao.PresentationDao;
import com.atanor.vserver.domain.dao.RecordingDao;
import com.atanor.vserver.domain.dao.VsConfigDao;
import com.atanor.vserver.domain.entity.Presentation;
import com.atanor.vserver.domain.entity.Recording;
import com.atanor.vserver.domain.entity.VsConfig;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;

public class AppPersistenceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(new TypeLiteral<GenericDao<Recording, Long>>() {}).to(new TypeLiteral<RecordingDao>() {});
		bind(new TypeLiteral<GenericDao<Presentation, Long>>() {}).to(new TypeLiteral<PresentationDao>() {});
		bind(new TypeLiteral<GenericDao<VsConfig, Long>>() {}).to(new TypeLiteral<VsConfigDao>() {});
	}

}
