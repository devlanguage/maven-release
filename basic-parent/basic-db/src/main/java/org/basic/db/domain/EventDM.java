package org.basic.db.domain;

import java.util.HashSet;
import java.util.Set;

public class EventDM extends AbstractPersistObject {

	private String title;
	private Set<PersonDM> persons = null;

	public EventDM() {
		persons = new HashSet<PersonDM>();

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