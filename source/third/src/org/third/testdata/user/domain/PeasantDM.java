/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.hibernate3.core.hello.persistence.Peasant.java is created on 2008-4-7
 */
package org.third.testdata.user.domain;


/**
 * 
 */
public class PeasantDM extends PersonDM {

    /**
     * What the peasant is planting. <br>
     * 
     * <pre>
     * valid Value:
     *   wheat, rice, broomcorn, oat, barley, soybean sprout
     * </pre>
     * 
     */
    private String emblement;

    /**
     * @return get method for the field emblement
     */
    public String getEmblement() {

        return this.emblement;
    }

    /**
     * @param emblement
     *            the emblement to set
     */
    public void setEmblement(String emblement) {

        this.emblement = emblement;
    }

}
