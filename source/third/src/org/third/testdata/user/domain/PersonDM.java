/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.hibernate3.core.hello.persistence.Person.java is created on 2008-3-26
 */
package org.third.testdata.user.domain;

import java.util.List;
import java.util.Set;


public class PersonDM {

    private Long id;
    private int age;
    private UserNameDM userName;

    // /Address Information, ----table: h3_address
    private String city;
    private String country;
    private String town;

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

    public PersonDM() {

    }

    // // Accessor methods for all properties, private setter for 'id'
    //
    // public void removeEvent(Event event) {
    //
    // this.getEvents().remove(event);
    // event.getPersons().remove(this);
    // }
    //
    // public void addEvent(Event event) {
    //
    // this.getEvents().add(event);
    // event.getPersons().add(this);
    // }

    /**
     * @return get method for the field emailAddresses
     */
    public Set<String> getEmailAddresses() {

        return this.emailAddresses;
    }

    /**
     * @param emailAddresses
     *            the emailAddresses to set
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
     * @param events
     *            the events to set
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
     * @param age
     *            the age to set
     */
    public void setAge(int age) {

        this.age = age;
    }

    /**
     * @return get method for the field id
     */
    public Long getId() {

        return this.id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {

        this.id = id;
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
     * @param magazines
     *            the magazines to set
     */
    public void setMagazines(List<MagazineDM> magazines) {

        this.magazines = magazines;
    }

    /**
     * @return get method for the field city
     */
    public String getCity() {

        return this.city;
    }

    /**
     * @param city
     *            the city to set
     */
    public void setCity(String city) {

        this.city = city;
    }

    /**
     * @return get method for the field country
     */
    public String getCountry() {

        return this.country;
    }

    /**
     * @param country
     *            the country to set
     */
    public void setCountry(String country) {

        this.country = country;
    }

    /**
     * @return get method for the field town
     */
    public String getTown() {

        return this.town;
    }

    /**
     * @param town
     *            the town to set
     */
    public void setTown(String town) {

        this.town = town;
    }

    /**
     * @return get method for the field userName
     */
    public UserNameDM getUserName() {

        return this.userName;
    }

    /**
     * @param userName
     *            the userName to set
     */
    public void setUserName(UserNameDM userName) {

        this.userName = userName;
    }

    /**
     * @return get method for the field roles
     */
    public List<String> getRoles() {

        return this.roles;
    }

    /**
     * @param roles
     *            the roles to set
     */
    public void setRoles(List<String> roles) {

        this.roles = roles;
    }

    @Override
    public String toString() {

        return new StringBuffer(this.getClass().getSimpleName()).append(":[PersonID=").append(
                this.id).append(",Name=").append(this.userName).append(", roleSize=").append(
                this.roles).append(", eventSize=").append(this.events)
                .append(", emailAddressSize=").append(this.emailAddresses).append("]").toString();
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
     * @param emblements
     *            the emblements to set
     */
    public void setEmblements(List<EmblementDM> emblements) {

        this.emblements = emblements;
    }
}