package org.basic.db.domain;


/**
 * Example persistent class. Notice that it looks exactly like any other class. JPA makes writing
 * persistent classes completely transparent.
 */
public class MagazineDM {

    private long id;
    private String isbn;
    private String title;
    private float price;
    private int version;

    private PersonDM person;

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
     * @return get method for the field id
     */
    public long getId() {

        return this.id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(long id) {

        this.id = id;
    }

    protected MagazineDM() {

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
     * @param isbn
     *            the isbn to set
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
     * @param price
     *            the price to set
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
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {

        this.title = title;
    }

    /**
     * @return get method for the field version
     */
    public int getVersion() {

        return this.version;
    }

    /**
     * @param version
     *            the version to set
     */
    public void setVersion(int version) {

        this.version = version;
    }

    @Override
    public String toString() {

        return new StringBuffer(this.getClass().getSimpleName()).append(":[id=").append(this.id)
                .append(", title=").append(this.title).append(", price=").append(this.price)
                .append(", person=").append(this.person)
                .append("]").toString();
    }

}
