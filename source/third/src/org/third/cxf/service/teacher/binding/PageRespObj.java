/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 2:08:15 PM Aug 19, 2014
 *
 *****************************************************************************
 */
package org.third.cxf.service.teacher.binding;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created on Aug 19, 2014, 2:08:15 PM
 */
@XmlRootElement  
@XmlAccessorType(XmlAccessType.FIELD)  
@XmlType(propOrder = { "totalResults", "currentPage", "onePageSize",  
        "firstResult", "nextPage", "previousPage", "totalPage" })  
public class PageRespObj implements Serializable {  
  
    private static final long serialVersionUID = -2940983877096774934L;  
  
    private int currentPage; // 当前页  
  
    private int firstResult; // 当前页的起始记录  
  
    private long totalResults; // 总共记录数  
  
    private int onePageSize; // 每页的数量  
  
    private long totalPage; // 总共多少页  
  
    private int nextPage; // 下一页  
  
    private int previousPage; // 上一页  
  
    public PageRespObj() {  
    }  
  
    public int getCurrentPage() {  
        return currentPage;  
    }  
  
    public void setCurrentPage(int currentPage) {  
        this.currentPage = currentPage;  
    }  
  
    public int getFirstResult() {  
        return firstResult;  
    }  
  
    public void setFirstResult(int firstResult) {  
        this.firstResult = firstResult;  
    }  
  
    public long getTotalResults() {  
        return totalResults;  
    }  
  
    public void setTotalResults(long totalResults) {  
        this.totalResults = totalResults;  
    }  
  
    public int getOnePageSize() {  
        return onePageSize;  
    }  
  
    public void setOnePageSize(int onePageSize) {  
        this.onePageSize = onePageSize;  
    }  
  
    public long getTotalPage() {  
        return totalPage;  
    }  
  
    public void setTotalPage(long totalPage) {  
        this.totalPage = totalPage;  
    }  
  
    public int getNextPage() {  
        return nextPage;  
    }  
  
    public void setNextPage(int nextPage) {  
        this.nextPage = nextPage;  
    }  
  
    public int getPreviousPage() {  
        return previousPage;  
    }  
  
    public void setPreviousPage(int previousPage) {  
        this.previousPage = previousPage;  
    }  
  
}  