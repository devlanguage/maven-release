package org.basic.common.bean;

public enum FileType {
	XML("XML"), PROPERTIES("PROPERTIES");

	private String name;

	private FileType(String name) {

		this.name = name;
	}

	/**
	 * @return get method for the field name
	 */
	public String getName() {

		return this.name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {

		this.name = name;
	}

}
