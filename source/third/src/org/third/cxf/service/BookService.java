/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 2:23:20 PM Feb 28, 2014
 *
 *****************************************************************************
 */
package org.third.cxf.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.third.cxf.bean.Book;

/**
 * basic examples
 * 
 * @author Administrator
 */
@Path("/book")
public interface BookService {
    /**
     * @PathParam 测试
     * @param id
     * @return
     */
    @GET
    @Path("/id/{id}")
    public Response getBook(@PathParam("id")
    int id);

    /**
     * @QueryParam 测试 url: http://localhost:9000/rest/book/page?from=1&to=10&order=sadf
     * @param from
     * @param to
     * @return
     */
    @GET
    @Path("/page")
    public Response getBookById(@QueryParam("from")
    int from, @QueryParam("to")
    int to, @QueryParam("order")
    List<String> order);

    /**
     * @MatrixParam 测试 url:http://localhost:9000/rest/book/matrix/2013;author=tom;country=china
     * @param year
     * @param author
     * @param country
     * @return
     */
    @GET
    @Path("/matrix/{year}")
    public Response getBookByMatrix(@PathParam("year")
    String year, @MatrixParam("author")
    String author, @MatrixParam("country")
    String country);

    @GET
    @Path("/{name}")
    @WebMethod
    public Book getBookByName(@PathParam("name")
    String name);

    @GET
    public Response getBooks(@Context()
    HttpServletRequest request, @Context()
    HttpServletResponse response);

    /**
     * @HeaderParam 测试 获取http头信息 url:http://localhost:9000/rest/book/getHeader
     * @param userAgent
     * @return
     */
    @GET
    @Path("/getHeader")
    public Response getHeader(@HeaderParam("user-agent")
    String userAgent);
}