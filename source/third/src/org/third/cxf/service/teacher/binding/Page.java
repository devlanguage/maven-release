/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 2:12:13 PM Aug 19, 2014
 *
 *****************************************************************************
 */
package org.third.cxf.service.teacher.binding;

/**
 * Created on Aug 19, 2014, 2:12:13 PM
 */
public class Page {
    private int pageNo;
    private int pageSize;
    private int totalCount;
    private int totalPages;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    /**
     * @return
     */
    public int getFirst() {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @return
     */
    public int getNextPage() {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @return
     */
    public int getPrePage() {
        // TODO Auto-generated method stub
        return 0;
    }

}
