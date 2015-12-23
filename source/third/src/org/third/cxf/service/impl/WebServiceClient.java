/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 3:40:07 PM Jun 9, 2014
 *
 *****************************************************************************
 */
package org.third.cxf.service.impl;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;

import org.third.cxf.service.book.BookService;
import org.third.cxf.service.book.IBookService;

/**
 * Created on Jun 9, 2014, 3:40:07 PM
 */
public class WebServiceClient {
    private static final QName SERVICE_NAME = new QName("http://service.cxf.third.org/Book/", "BookService");

    public static void main(String[] args) {
        System.out.println("Starting Server");

        URL wsdlURL;
        try {
            wsdlURL = new URL("http://localhost:19002/cxf/BookServiceSoapDocumentLiteralImpl?wsdl");
            BookService ss = new BookService(wsdlURL, SERVICE_NAME);
            IBookService port = ss.getBookServicePortSoapRpcLiteral();

            System.out.println("Invoking getBookByName...");
            java.lang.String _getBookByName_bookName = "";
            org.third.cxf.service.book.BookType _getBookByName__return = port.getBookByName(_getBookByName_bookName);
            System.out.println("getBookByName.result=" + _getBookByName__return);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
