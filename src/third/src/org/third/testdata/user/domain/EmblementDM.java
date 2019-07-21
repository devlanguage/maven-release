package org.third.testdata.user.domain;

import java.math.BigDecimal;


/**
 * 
 */
public class EmblementDM {

    private long emblementId;
    private String emblementName;
    private BigDecimal output;
    private String unit;
    private PersonDM person;

    /**
     * @return get method for the field emblementId
     */
    public long getEmblementId() {

        return this.emblementId;
    }

    /**
     * @param emblementId
     *            the emblementId to set
     */
    public void setEmblementId(long emblementId) {

        this.emblementId = emblementId;
    }

    /**
     * @return get method for the field emblementName
     */
    public String getEmblementName() {

        return this.emblementName;
    }

    /**
     * @param emblementName
     *            the emblementName to set
     */
    public void setEmblementName(String emblementName) {

        this.emblementName = emblementName;
    }

    /**
     * @return get method for the field output
     */
    public BigDecimal getOutput() {

        return this.output;
    }

    /**
     * @param output
     *            the output to set
     */
    public void setOutput(BigDecimal output) {

        this.output = output;
    }

    /**
     * @return get method for the field person
     */
    public PersonDM getPerson() {

        return this.person;
    }

    /**
     * @param person
     *            the person to set
     */
    public void setPerson(PersonDM person) {

        this.person = person;
    }

    /**
     * @return get method for the field unit
     */
    public String getUnit() {

        return this.unit;
    }

    /**
     * @param unit
     *            the unit to set
     */
    public void setUnit(String unit) {

        this.unit = unit;
    }

    @Override
    public String toString() {

        return new StringBuffer(this.getClass().getSimpleName()).append(":[emblementId=").append(
                this.emblementId).append(", emblementName=").append(this.emblementName).append(
                ",output=").append(this.output).append(" ").append(this.unit).toString();

    }
}
