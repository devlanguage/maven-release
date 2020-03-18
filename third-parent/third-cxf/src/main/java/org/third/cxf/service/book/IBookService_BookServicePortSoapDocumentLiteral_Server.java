package org.third.cxf.service.book;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF 2.7.10
 * 2014-06-09T15:51:44.673+08:00
 * Generated source version: 2.7.10
 * 
 */
 
public class IBookService_BookServicePortSoapDocumentLiteral_Server{

    protected IBookService_BookServicePortSoapDocumentLiteral_Server() throws java.lang.Exception {
        System.out.println("Starting Server");
        Object implementor = new IBookServiceImpl1();
        String address = "http://localhost:9001/cxf";
        Endpoint.publish(address, implementor);
    }
    
    public static void main(String args[]) throws java.lang.Exception { 
        new IBookService_BookServicePortSoapDocumentLiteral_Server();
        System.out.println("Server ready..."); 
        
        Thread.sleep(5 * 60 * 1000); 
        System.out.println("Server exiting");
        System.exit(0);
    }
}