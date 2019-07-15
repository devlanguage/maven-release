package org.third.cxf.service.book;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF 2.7.10
 * 2014-06-09T15:51:44.677+08:00
 * Generated source version: 2.7.10
 * 
 */
 
public class IBookService_BookServicePortHttpGet_Server{

    protected IBookService_BookServicePortHttpGet_Server() throws java.lang.Exception {
        System.out.println("Starting Server");
        Object implementor = new IBookServiceImpl3();
        String address = "http://localhost:9004/cxf";
        Endpoint.publish(address, implementor);
    }
    
    public static void main(String args[]) throws java.lang.Exception { 
        new IBookService_BookServicePortHttpGet_Server();
        System.out.println("Server ready..."); 
        
        Thread.sleep(5 * 60 * 1000); 
        System.out.println("Server exiting");
        System.exit(0);
    }
}
