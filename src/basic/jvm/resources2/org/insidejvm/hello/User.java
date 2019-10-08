package org.insidejvm.hello;

/**
 * 
 */
public class User {

    private String name = "Anonymous";

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

        return User.class.getClassLoader() + " loaded the class: User:[name=" + this.name + "]";
    }

}