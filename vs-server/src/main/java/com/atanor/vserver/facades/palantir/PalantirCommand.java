package com.atanor.vserver.facades.palantir;

public enum PalantirCommand {

	START_RECORDING("Start"), STOP_RECORDING("Stop"), NO_FREE_SPACE("NoSpace"), NO_VIDEO_AVAILABLE("NoSource");

	private String value;

	private PalantirCommand(final String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
}
