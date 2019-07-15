package org.basic.grammar.jvm;

/**
 * 
 */
public abstract class JvmBaseBean {

    protected String id;

    /**
     * @return get method for the field id
     */
    public String getId() {

        return this.id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {

        this.id = id;
    }
}
