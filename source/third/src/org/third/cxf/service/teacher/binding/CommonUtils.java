package org.third.cxf.service.teacher.binding;

/**
 * Created on Aug 19, 2014, 2:09:25 PM
 */
public class CommonUtils {
    /**
     * 
     * @param page
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static PageRespObj setPageInfoFromPage(Page page) {
        int currentPage = 0;
        int onePageSize = 0;
        long totalResults = 0;
        int firstResult = 0;
        int nextPage = 0;
        int previousPage = 0;
        long totalPage = 0;

        if (page != null) {
            currentPage = page.getPageNo();
            onePageSize = page.getPageSize();
            totalResults = page.getTotalCount();
            firstResult = page.getFirst();
            nextPage = page.getNextPage();
            previousPage = page.getPrePage();
            totalPage = page.getTotalPages();

            PageRespObj obj = new PageRespObj();
            obj.setCurrentPage(currentPage);
            obj.setOnePageSize(onePageSize);
            obj.setTotalResults(totalResults);
            obj.setFirstResult(firstResult);
            obj.setNextPage(nextPage);
            obj.setPreviousPage(previousPage);
            obj.setTotalPage(totalPage);

            return obj;
        } else {
            return null;
        }
    }
}