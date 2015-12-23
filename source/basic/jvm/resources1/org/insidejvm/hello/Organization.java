/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.insidejvm.hello.Organization.java is created on 2008-2-26
 */
package org.insidejvm.hello;

/**
 * 
 */
public class Organization {

    private String name = "ISO";

    /**
     * @return get method for the field name
     */
    public String getName() {

        return this.name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {

        this.name = name;
    }

    @Override
    public String toString() {

        return Organization.class.getClassLoader() + " loaded the class: Organization:[name="
                + this.name + "]";
    }

}
