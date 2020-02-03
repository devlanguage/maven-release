package org.basic.db.domain;

@javax.persistence.MappedSuperclass
public abstract class AbstractNamedObject extends AbstractPersistObject {
	private static final long serialVersionUID = -8825468341393430875L;
	protected String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
