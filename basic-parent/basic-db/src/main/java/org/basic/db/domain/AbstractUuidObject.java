package org.basic.db.domain;

import java.util.UUID;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

public abstract class AbstractUuidObject {
	protected String id;

	public AbstractUuidObject() {
		id = UUID.randomUUID().toString();
	}

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "idGenerator")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
