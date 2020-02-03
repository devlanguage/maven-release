package org.basic.db.domain;

import java.util.List;
import java.util.Set;

public class CustomerDM extends AbstractPersistObject {
	private String firstName;
	private String lastName;
	private String sex = "F";
	private int age = 20;
	private String profession = "WORKER";
	private String position = "Manager";
	private String emblement = "WHEAT";

	private List<EmblementDM> emblements;

	// PersistentList
	// mapped to hibernate List, bag, idbag,
	// <bag> ===>
	private List<String> roles;

	// PersistentSet
	// <list> ===> java.util.ArrayList
	private List<MagazineDM> magazines;// = new HashSet<Magazine>();
	// one-to-many == > set
	private Set<EventDM> events;// = new HashSet<Event>();
	// join ===> set
	private Set<String> emailAddresses;// = new HashSet<String>();

	/**
	 * @return get method for the field emailAddresses
	 */
	public Set<String> getEmailAddresses() {

		return this.emailAddresses;
	}

	/**
	 * @param emailAddresses the emailAddresses to set
	 */
	public void setEmailAddresses(Set<String> emailAddresses) {

		this.emailAddresses = emailAddresses;
	}

	/**
	 * @return get method for the field events
	 */
	public Set<EventDM> getEvents() {

		return this.events;
	}

	/**
	 * @param events the events to set
	 */
	public void setEvents(Set<EventDM> events) {

		this.events = events;
	}

	/**
	 * @return get method for the field age
	 */
	public int getAge() {

		return this.age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {

		this.age = age;
	}

	/**
	 * @return get method for the field magazines
	 */
	public List<MagazineDM> getMagazines() {

		return this.magazines;
	}

	public void addMagazine(MagazineDM magazine) {

		this.getMagazines().add(magazine);
	}

	/**
	 * @param magazines the magazines to set
	 */
	public void setMagazines(List<MagazineDM> magazines) {

		this.magazines = magazines;
	}

	/**
	 * @return get method for the field roles
	 */
	public List<String> getRoles() {

		return this.roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<String> roles) {

		this.roles = roles;
	}

	public void addRole(String r) {

		this.getRoles().add(r);
	}

	/**
	 * @return get method for the field emblements
	 */
	public List<EmblementDM> getEmblements() {

		return this.emblements;
	}

	public void addEmblement(EmblementDM emblement) {

		this.getEmblements().add(emblement);
	}

	/**
	 * @param emblements the emblements to set
	 */
	public void setEmblements(List<EmblementDM> emblements) {

		this.emblements = emblements;
	}
}