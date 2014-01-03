package com.atanor.vserver.common.rpc.dto;

@SuppressWarnings("serial")
public class VsConfigDto extends AbstractDto {

	private Long id;
	private String name;
	private String recordingMediaOptions;
	private String recordingMediaResource;
	private String presentationMediaOptions;
	private String presentationMediaResource;
	private String recordingsOutput;
	private String recordingSnapshotOutput;
	private String presentationsOutput;
	private String presentationSnapshotOutput;
	private String playerInstallPath;
	private String palantirUrl;
	private String palantirPort;
	private Boolean isDefault;
	private Boolean addLogo;

	public VsConfigDto() {
	}

	public VsConfigDto(final Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getRecordingMediaOptions() {
		return recordingMediaOptions;
	}

	public void setRecordingMediaOptions(final String recordingMediaOptions) {
		this.recordingMediaOptions = recordingMediaOptions;
	}

	public String getPresentationMediaOptions() {
		return presentationMediaOptions;
	}

	public void setPresentationMediaOptions(final String presentationMediaOptions) {
		this.presentationMediaOptions = presentationMediaOptions;
	}

	public String getRecordingsOutput() {
		return recordingsOutput;
	}

	public void setRecordingsOutput(final String recordingsOutput) {
		this.recordingsOutput = recordingsOutput;
	}

	public String getPresentationsOutput() {
		return presentationsOutput;
	}

	public void setPresentationsOutput(final String presentationsOutput) {
		this.presentationsOutput = presentationsOutput;
	}

	public String getPlayerInstallPath() {
		return playerInstallPath;
	}

	public void setPlayerInstallPath(final String playerInstallPath) {
		this.playerInstallPath = playerInstallPath;
	}

	public String getPalantirUrl() {
		return palantirUrl;
	}

	public void setPalantirUrl(final String palantirUrl) {
		this.palantirUrl = palantirUrl;
	}

	public String getPalantirPort() {
		return palantirPort;
	}

	public void setPalantirPort(final String palantirPort) {
		this.palantirPort = palantirPort;
	}

	public Boolean isDefault() {
		return isDefault;
	}

	public void setDefault(final Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Boolean getAddLogo() {
		return addLogo;
	}

	public void setAddLogo(final Boolean addLogo) {
		this.addLogo = addLogo;
	}

	public String getRecordingMediaResource() {
		return recordingMediaResource;
	}

	public void setRecordingMediaResource(final String recordingMediaResource) {
		this.recordingMediaResource = recordingMediaResource;
	}

	public String getPresentationMediaResource() {
		return presentationMediaResource;
	}

	public void setPresentationMediaResource(final String confMediaResource) {
		this.presentationMediaResource = confMediaResource;
	}

	public String getRecordingSnapshotOutput() {
		return recordingSnapshotOutput;
	}

	public void setRecordingSnapshotOutput(final String recordingSnapshotOutput) {
		this.recordingSnapshotOutput = recordingSnapshotOutput;
	}

	public String getPresentationSnapshotOutput() {
		return presentationSnapshotOutput;
	}

	public void setPresentationSnapshotOutput(final String presentationSnapshotOutput) {
		this.presentationSnapshotOutput = presentationSnapshotOutput;
	}

}
