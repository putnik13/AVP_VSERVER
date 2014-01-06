package com.atanor.vserver.common.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Snapshot implements Serializable {

	public enum TYPE {
		VIDEO, PRESENTATION
	}

	private TYPE type;
	private String width;
	private String height;
	private String encodedImage;
	private String fileName;
	
	public Snapshot() {
	}

	public Snapshot(final TYPE type, final String fileName, final String encodedImage, final String width,
			final String height) {
		this.type = type;
		this.fileName = fileName;
		this.encodedImage = encodedImage;
		this.width = width;
		this.height = height;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(final String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(final String height) {
		this.height = height;
	}

	public String getEncodedImage() {
		return encodedImage;
	}

	public void setEncodedImage(final String encodedImage) {
		this.encodedImage = encodedImage;
	}

	public void setType(final TYPE type) {
		this.type = type;
	}

	public TYPE getType() {
		return type;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(final String fileName) {
		this.fileName = fileName;
	}

}
