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

import javax.xml.ws.Endpoint;

/**
 * Created on Jun 9, 2014, 3:40:07 PM
 */
public class WebServiceTest {

    /**
     * 
     */
    public WebServiceTest() {
        // TODO Auto-generated constructor stub
    }
    public static void main(String[] args) {
        System.out.println("Starting Server");
        Object implementor = new BookServiceSoapDocumentLiteralImpl();
        String address = "http://localhost:9002/cxf";
        Endpoint.publish(address, implementor);
    }

}
