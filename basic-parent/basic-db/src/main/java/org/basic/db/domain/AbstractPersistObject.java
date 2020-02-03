package org.basic.db.domain;

import java.util.Date;

import javax.persistence.TemporalType;

public abstract class AbstractPersistObject extends AbstractIdObject {
	private static final long serialVersionUID = 4924888794307966379L;
	protected int version;
	protected Date createdDate;
	protected Date modifiedDate;

	public AbstractPersistObject() {
	}

	@javax.persistence.Version
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@javax.persistence.Temporal(TemporalType.TIMESTAMP)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@javax.persistence.Temporal(TemporalType.TIMESTAMP)
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
}
