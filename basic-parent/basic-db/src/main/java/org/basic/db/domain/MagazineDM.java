package org.basic.db.domain;

/**
 * Example persistent class. Notice that it looks exactly like any other class.
 * JPA makes writing persistent classes completely transparent.
 */
public class MagazineDM extends AbstractNamedObject {
	private static final long serialVersionUID = 3888556877838502461L;
	private String isbn;
	private String title;
	private float price;

	private PersonDM person;

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

	public MagazineDM(String title, String isbn) {

		this.title = title;
		this.isbn = isbn;
	}

	/**
	 * @return get method for the field isbn
	 */
	public String getIsbn() {

		return this.isbn;
	}

	/**
	 * @param isbn the isbn to set
	 */
	public void setIsbn(String isbn) {

		this.isbn = isbn;
	}

	/**
	 * @return get method for the field price
	 */
	public float getPrice() {

		return this.price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(float price) {

		this.price = price;
	}

	/**
	 * @return get method for the field title
	 */
	public String getTitle() {

		return this.title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {

		this.title = title;
	}
}
