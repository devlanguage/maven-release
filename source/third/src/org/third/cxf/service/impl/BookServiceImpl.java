package org.third.cxf.service.impl;

import java.util.Enumeration;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.third.cxf.bean.Book;
import org.third.cxf.service.BookService;

@WebService(endpointInterface = "org.third.cxf.service.IBookService", serviceName = "BookService")
public class BookServiceImpl implements BookService {

    @Override
    @WebMethod(exclude = true)
    public Response getBook(int id) {
        System.out.println("getBook is called...");
        return Response.ok().entity(id).build();
    }

    @Override
    @WebMethod(exclude = true)
    public Response getBookById(int from, int to, List<String> order) {
        System.out.println("getBookById is called...");
        return Response.ok().entity("from " + from + " to " + to + " order by " + order.toString()).build();
    }

    @Override
    @WebMethod(exclude = true)
    public Response getBookByMatrix(String year, String author, String country) {
        System.out.println("getBookByMatrix is called...");
        return Response.ok().entity("year:" + year + ",author:" + author + ",country:" + country).build();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.unei.service.IBookService#getBook()
     */
    @Override
    @WebMethod
    public Book getBookByName(String name) {
        Book b = new Book(23, "计算机测试");
        return b;
    }

    @Override
    @WebMethod(exclude = true)
    public Response getBooks(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("getBooks is called...");
        for (Enumeration<String> headerNameIter = request.getHeaderNames(); headerNameIter.hasMoreElements();) {
            String headerName = headerNameIter.nextElement();
            System.out.println(headerName + "=" + request.getHeader(headerName));
        }
        ResponseBuilder resp = Response.ok();
        try {
            resp = resp.entity("<html><body><h1>Response</h1></body></html>");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp.build();
    }

    @Override
    @WebMethod(exclude = true)
    public Response getHeader(String userAgent) {
        System.out.println("getHeader is called...");
        return Response.ok().entity(userAgent).build();
    }

}