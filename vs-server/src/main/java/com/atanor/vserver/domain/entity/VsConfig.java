package com.atanor.vserver.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "vsconfiguration")
@NamedQuery(name = "VsConfig.GetAll", query = "SELECT c FROM VsConfig c")
public class VsConfig extends AbstractEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name", length = 32)
	private String name;

	@Column(name = "recording_media_options", length = 300)
	private String recordingMediaOptions;

	@Column(name = "recording_media_resource", length = 100)
	private String recordingMediaResource;
	
	@Column(name = "presentation_media_options", length = 300)
	private String presentationMediaOptions;

	@Column(name = "presentation_media_resource", length = 100)
	private String presentationMediaResource;
	
	@Column(name = "recordings_output", length = 100)
	private String recordingsOutput;

	@Column(name = "recording_snapshot_output", length = 100)
	private String recordingSnapshotOutput;
	
	@Column(name = "presentations_output", length = 100)
	private String presentationsOutput;

	@Column(name = "presentation_snapshot_output", length = 100)
	private String presentationSnapshotOutput;
	
	@Column(name = "player_install", length = 100)
	private String playerInstallPath;

	@Column(name = "palantir_url", length = 32)
	private String palantirUrl;

	@Column(name = "palantir_port", length = 32)
	private String palantirPort;

	@Column(name = "is_default", columnDefinition = "BOOLEAN")
	private Boolean isDefault;

	@Column(name = "add_logo", columnDefinition = "BOOLEAN")
	private Boolean addLogo;

	public VsConfig() {
	}
	
	public VsConfig(Long id) {
		this.id = id;
	}

	@Override
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

	public Boolean getAddLogo() {
		return addLogo;
	}

	public void setAddLogo(final Boolean addLogo) {
		this.addLogo = addLogo;
	}

	public Boolean isDefault() {
		return isDefault;
	}

	public void setDefault(final Boolean isDefault) {
		this.isDefault = isDefault;
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

	public void setPresentationMediaResource(final String presentationMediaResource) {
		this.presentationMediaResource = presentationMediaResource;
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
