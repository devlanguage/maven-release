package org.basic.db.domain;

public class AddressDM extends AbstractPersistObject {

	private String country;
	private String city;

	public AddressDM() {
		this.country = "China";
		this.city = "Shanghai";
	}

	@Override
	public String toString() {

		return "[Address:[country=" + this.country + ",city=" + this.city + "]]";
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
