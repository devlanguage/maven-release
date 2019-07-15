package org.basic.grammar.jvm;

/**
 * 
 */
public class Organization extends JvmBaseBean {

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
