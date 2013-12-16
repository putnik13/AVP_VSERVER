package com.atanor.vserver.facades;

import com.atanor.vserver.facades.palantir.PalantirCommand;

public interface PalantirFacade {

	void send(PalantirCommand command);
}
