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
	
	@Column(name = "stream_media_options", length = 300)
	private String streamMediaOptions;

	@Column(name = "conf_media_options", length = 300)
	private String confMediaOptions;

	@Column(name = "recordings_output", length = 100)
	private String recordingsOutput;

	@Column(name = "presentations_output", length = 100)
	private String presentationsOutput;

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
	
	public String getStreamMediaOptions() {
		return streamMediaOptions;
	}

	public void setStreamMediaOptions(final String streamMediaOptions) {
		this.streamMediaOptions = streamMediaOptions;
	}

	public String getConfMediaOptions() {
		return confMediaOptions;
	}

	public void setConfMediaOptions(final String confMediaOptions) {
		this.confMediaOptions = confMediaOptions;
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

}
