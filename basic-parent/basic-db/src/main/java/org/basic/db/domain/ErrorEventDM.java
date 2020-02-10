package org.basic.db.domain;

public class ErrorEventDM extends EventDM {

	private static final long serialVersionUID = -1047028067416979131L;
	private String errorName;

	/**
	 * @return get method for the field errorName
	 */
	public String getErrorName() {

		return this.errorName;
	}

	/**
	 * @param errorName the errorName to set
	 */
	public void setErrorName(String errorName) {

		this.errorName = errorName;
	}

	@Override
	public String toString() {

		return super.toString();
	}
}
