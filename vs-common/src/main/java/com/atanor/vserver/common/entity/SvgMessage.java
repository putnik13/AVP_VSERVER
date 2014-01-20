package com.atanor.vserver.common.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SvgMessage implements Serializable {

	private String svgString;

	public SvgMessage() {
	}

	public SvgMessage(final String svgString) {
		this.svgString = svgString;
	}

	public void setSvgString(final String svgString) {
		this.svgString = svgString;
	}

	public String getSvgString() {
		return svgString;
	}

}
