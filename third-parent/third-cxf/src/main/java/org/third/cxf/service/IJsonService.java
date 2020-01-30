package org.third.cxf.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.third.cxf.bean.Book;

@Path("/json")
public interface IJsonService {
    /**
     * JSON提交 url:http://localhost:9000/rest/json/addBook Json format:{"book":{"bookId":123,"bookName":"newBook"}}
     * 
     * @param book
     * @return
     */
    @POST
    @Path("/addBook")
    @Consumes("application/json")
    public Response addBook(Book book);

    /**
     * Json提交2 url:http://localhost:9000/rest/json/addBooks Json
     * format:{"book":[{"bookId":123,"bookName":"newBook"},{"bookId":456,"bookName":"newBook2"}]}
     * 
     * @param books
     * @return
     */
    @POST
    @Path("/addBooks")
    @Consumes("application/json")
    public Response addBooks(List<Book> books);
}