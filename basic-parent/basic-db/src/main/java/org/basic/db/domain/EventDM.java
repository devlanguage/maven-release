package org.basic.db.domain;

import java.util.HashSet;
import java.util.Set;

@javax.persistence.Table(name = "dm_event")
@javax.persistence.Entity
public class EventDM extends AbstractNamedObject {

	private static final long serialVersionUID = 3173946389420940559L;
	private String title;
	private String source;
	private Set<PersonDM> persons = null;

	public EventDM() {
		persons = new HashSet<PersonDM>();

	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTitle() {

		return title;
	}

	public void setTitle(String title) {

		this.title = title;
	}

	/**
	 * @return get method for the field persons
	 */
	public Set<PersonDM> getPersons() {

		return this.persons;
	}

	/**
	 * @param persons the persons to set
	 */
	public void setPersons(Set<PersonDM> persons) {

		this.persons = persons;
	}
}