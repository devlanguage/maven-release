package org.basic.io.xml.stax.digester.catalog;

public class Book {

    private String author;
    private String title;

    private Catalog catalog;

    /**
     * @return the catalog
     */
    public Catalog getCatalog() {

        return catalog;
    }

    /**
     * @param catalog
     *            the catalog to set
     */
    public void setCatalog(Catalog catalog) {

        this.catalog = catalog;
    }

    @Override
    public String toString() {

        return this.getClass().getSimpleName() + ":[author=" + this.author + ", title=" + title + "]";
    }

    public String getAuthor() {

        return author;
    }

    public void setAuthor(String author) {

        this.author = author;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

}
