package org.basic.db.domain;

@javax.persistence.MappedSuperclass
public abstract class AbstractNamedObject extends AbstractPersistObject {
	protected String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
