package org.basic.jdk.core.jvm;

/**
 * 
 */
public class User extends JvmBaseBean {

    private String name = "Anonymous";
    private Organization organization;

    public User() {

        this.organization = new Organization();
    }

    /**
     * @return get method for the field name
     */
    public String getName() {

        return this.name;
    }

    /**
     * @return get method for the field organization
     */
    public Organization getOrganization() {

        return this.organization;
    }

    /**
     * @param organization
     *            the organization to set
     */
    public void setOrganization(Organization organization) {

        this.organization = organization;
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

        return User.class.getClassLoader() + " loaded the class: User:[name=" + this.name
                + ", Organiztion=" + this.organization + "]";
    }

}
