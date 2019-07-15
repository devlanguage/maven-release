package org.third.testdata.user.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class EventDM implements Comparable {

    /**
     * The id property holds a unique identifier value for a particular event. All persistent entity
     * classes (there are less important dependent classes as well) will need such an identifier
     * property if we want to use the full feature set of Hibernate. In fact, most applications
     * (esp. web applications) need to distinguish objects by identifier, so you should consider
     * this a feature rather than a limitation
     */
    private long id = 23l;

    private String title;
    private Date date;
    private Set<PersonDM> persons = new HashSet<PersonDM>();

    public EventDM() {

    }

    public long getId() {

        return id;
    }

    /**
     * we usually don't manipulate the identity of an object, hence the setter method should be //
     * private. Only Hibernate will assign identifiers when an object is saved. You can see that
     * Hibernate can access public, private, and protected accessor methods, as well as (public,
     * private, protected) fields directly. The choice is up to you and you can match it to fit your
     * application design
     * 
     * @param id
     */

    public void setId(long id) {

        this.id = id;
    }

    public Date getDate() {

        return date;
    }

    public void setDate(Date date) {

        this.date = date;
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
     * @param persons
     *            the persons to set
     */
    public void setPersons(Set<PersonDM> persons) {

        this.persons = persons;
    }

    public int compareTo(Object o) {

        EventDM other = (EventDM) o;
        return (int) (this.getId() - other.getId());
    }

    public String getErrorName() {

        return "";
    }

    @Override
    public String toString() {

        return new StringBuffer(this.getClass().getSimpleName()).append(":[id=").append(this.id)
                .append(", title=").append(this.title).append(", errorName=").append(this.getErrorName()).toString();
    }
}