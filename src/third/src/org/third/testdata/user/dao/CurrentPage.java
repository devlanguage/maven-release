package org.third.testdata.user.dao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on Oct 27, 2014, 4:19:34 PM
 */
public class CurrentPage<E> {
    private int pageNumber;
    private int pagesAvailable;
    private List<E> pageItems = new ArrayList<E>();

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPagesAvailable(int pagesAvailable) {
        this.pagesAvailable = pagesAvailable;
    }

    public void setPageItems(List<E> pageItems) {
        this.pageItems = pageItems;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPagesAvailable() {
        return pagesAvailable;
    }

    public List<E> getPageItems() {
        return pageItems;
    }
}