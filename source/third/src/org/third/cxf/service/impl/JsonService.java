/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 2:42:00 PM Feb 28, 2014
 *
 *****************************************************************************
 */
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