/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.hibernate3.core.hello.persistence.ErrorEvent.java is created on 2008-4-7
 */
package org.third.testdata.user.domain;

/**
 * 
 */
public class ErrorEventDM extends EventDM {

    private String errorName;

    /**
     * @return get method for the field errorName
     */
    public String getErrorName() {

        return this.errorName;
    }

    /**
     * @param errorName
     *            the errorName to set
     */
    public void setErrorName(String errorName) {

        this.errorName = errorName;
    }

    @Override
    public String toString() {

        return super.toString();
    }
}
