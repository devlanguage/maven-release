package org.basic.io.xml.stax.digester.catalog;

import java.util.HashSet;
import java.util.Set;

public class Catalog {

    private String library;
    private Set<Book> books;
    private Set<Magazine> magazines;

    @Override
    public String toString() {

        return this.getClass().getSimpleName() + ":[library=" + this.library + ", books=" + getBooks() + ", magazines="
                + this.getMagazines() + "]";
    }

    /**
     * @return the books
     */
    public synchronized Set<Book> getBooks() {

        if (books == null) {
            books = new HashSet<Book>();
        }
        return books;
    }

    /**
     * @param books
     *            the books to set
     */
    public void setBooks(Set<Book> books) {

        this.books = books;
    }

    public void addBook(Book book) {

        this.getBooks().add(book);

    }

    /**
     * @return the magazines
     */
    public synchronized Set<Magazine> getMagazines() {

        if (this.magazines == null) {
            this.magazines = new HashSet<Magazine>();
        }
        return magazines;
    }

    public void addMagazine(Magazine magazine) {

        this.getMagazines().add(magazine);
    }

    /**
     * @param magazines
     *            the magazines to set
     */
    public void setMagazines(Set<Magazine> magazines) {

        this.magazines = magazines;
    }

    /**
     * @return the library
     */
    public String getLibrary() {

        return library;
    }

    /**
     * @param library
     *            the library to set
     */
    public void setLibrary(String library) {

        this.library = library;
    }
}
