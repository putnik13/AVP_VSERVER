package com.atanor.vserver.common.rpc.dto;

import java.util.Date;

@SuppressWarnings("serial")
public class PresentationDto extends AbstractDto {

	private Long id;
	private String name;
	private Date createTime;
	private Boolean outdated;
	
	public PresentationDto() {
	}

	public PresentationDto(final Long id) {
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(final Date createTime) {
		this.createTime = createTime;
	}

	public Boolean isOutdated() {
		return outdated;
	}

	public void setOutdated(final Boolean outdated) {
		this.outdated = outdated;
	}

}
