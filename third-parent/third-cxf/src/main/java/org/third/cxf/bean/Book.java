package org.third.cxf.bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "book")
public class Book {
    private int bookId;
    private String bookName;

    public Book() {
    }

    public Book(int id, String name) {
        this.bookId = id;
        this.bookName = name;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String toString() {
        return "[bookId:" + bookId + "],[bookName:" + bookName + "]";
    }
}