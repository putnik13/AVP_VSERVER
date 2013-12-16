package com.atanor.vserver.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "presentations")
@NamedQuery(name = "Presentation.GetAll", query = "SELECT p FROM Presentation p")
public class Presentation extends AbstractEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name", length = 32)
	private String name;

	@Column(name = "createtime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	@Lob
	@Column(name = "presentation_blob", length = 300000)
	private String presentationBlob;
	
	@Transient
	private Boolean outdated = Boolean.FALSE;
	
	public Presentation() {
	}

	public Presentation(Long id) {
		this.id = id;
	}

	public Presentation(final String name) {
		this.name = name;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(final Date createTime) {
		this.createTime = createTime;
	}

	public String getPresentationBlob() {
		return presentationBlob;
	}

	public void setPresentationBlob(final String presentationBlob) {
		this.presentationBlob = presentationBlob;
	}

	public Boolean isOutdated() {
		return outdated;
	}

	public void setOutdated(final Boolean outdated) {
		this.outdated = outdated;
	}
	
}
