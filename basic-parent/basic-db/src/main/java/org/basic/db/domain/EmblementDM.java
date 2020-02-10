package org.basic.db.domain;

import java.math.BigDecimal;

public class EmblementDM extends AbstractNamedObject {
	private static final long serialVersionUID = -6043312015525232993L;
	private BigDecimal output;
	private String unit;
	private PersonDM person;

	/**
	 * @return get method for the field output
	 */
	public BigDecimal getOutput() {

		return this.output;
	}

	/**
	 * @param output the output to set
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
	 * @param person the person to set
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
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {

		this.unit = unit;
	}
}
