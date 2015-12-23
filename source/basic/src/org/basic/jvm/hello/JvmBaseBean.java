/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.insidejvm.hello.JvmBaseBean.java is created on 2008-2-26
 */
package org.basic.jvm.hello;

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
