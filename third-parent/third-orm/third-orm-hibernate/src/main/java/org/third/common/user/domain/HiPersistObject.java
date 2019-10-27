package org.third.common.user.domain;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.TemporalType;

@MappedSuperclass
public class HiPersistObject {
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(generator = "uuid")
	@org.hibernate.annotations.GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	@javax.persistence.Column
	private String name;
	@javax.persistence.Temporal(TemporalType.TIMESTAMP)
	private Date created;
	@javax.persistence.Temporal(TemporalType.TIMESTAMP)
	private Date lastModified;
	@javax.persistence.Version
	private int version;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}
