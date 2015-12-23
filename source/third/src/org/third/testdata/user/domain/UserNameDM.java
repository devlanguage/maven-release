/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.hibernate3.core.hello.persistence.UserName.java is created on 2008-4-7
 */
package org.third.testdata.user.domain;

/**
 * 
 */
public class UserNameDM {

    private String firstName;
    private String lastName;

    /**
     * @return get method for the field firstName
     */
    public String getFirstName() {

        return this.firstName;
    }

    /**
     * @param firstName
     *            the firstName to set
     */
    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }

    /**
     * @return get method for the field lastName
     */
    public String getLastName() {

        return this.lastName;
    }

    /**
     * @param lastName
     *            the lastName to set
     */
    public void setLastName(String lastName) {

        this.lastName = lastName;
    }

    @Override
    public String toString() {

        return new StringBuffer(this.getClass().getSimpleName()).append(":[firstName=").append(
                this.firstName).append(", lastName=").append(this.lastName).append("]").toString();
    }
}
