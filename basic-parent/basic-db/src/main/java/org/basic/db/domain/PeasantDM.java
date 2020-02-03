package org.basic.db.domain;

public class PeasantDM extends PersonDM {

	private static final long serialVersionUID = -5923501944271252135L;
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
	 * @param emblement the emblement to set
	 */
	public void setEmblement(String emblement) {

		this.emblement = emblement;
	}

}
