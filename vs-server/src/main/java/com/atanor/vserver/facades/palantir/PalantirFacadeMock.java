package com.atanor.vserver.facades.palantir;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atanor.vserver.facades.PalantirFacade;

public class PalantirFacadeMock implements PalantirFacade {

	private static final Logger LOG = LoggerFactory.getLogger(PalantirFacadeMock.class);

	@Override
	public void send(PalantirCommand command) {
		LOG.info("Send {} command to Palantir", command.name());
	}

}
