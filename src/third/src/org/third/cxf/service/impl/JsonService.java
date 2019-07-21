package org.third.cxf.service.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import org.third.cxf.bean.Book;
import org.third.cxf.service.IJsonService;

public class JsonService implements IJsonService {

    @Override
    public Response addBook(Book book) {
        System.out.println("addBook is called...");
        return Response.ok().entity(book.toString()).build();
    }

    @Override
    public Response addBooks(List<Book> books) {
        System.out.println("addBooks is called...");
        return Response.ok().entity("ok").build();
    }

}