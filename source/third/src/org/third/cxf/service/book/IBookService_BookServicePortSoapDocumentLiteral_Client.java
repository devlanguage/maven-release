package org.third.cxf.service.book;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.10
 * 2014-06-09T15:51:44.598+08:00
 * Generated source version: 2.7.10
 * 
 */
public final class IBookService_BookServicePortSoapDocumentLiteral_Client {

    private static final QName SERVICE_NAME = new QName("http://service.cxf.third.org/Book/", "BookService");

    private IBookService_BookServicePortSoapDocumentLiteral_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = BookService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        BookService ss = new BookService(wsdlURL, SERVICE_NAME);
        IBookService port = ss.getBookServicePortSoapDocumentLiteral();  
        
        {
        System.out.println("Invoking getBookByName...");
        java.lang.String _getBookByName_bookName = "";
        org.third.cxf.service.book.BookType _getBookByName__return = port.getBookByName(_getBookByName_bookName);
        System.out.println("getBookByName.result=" + _getBookByName__return);


        }

        System.exit(0);
    }

}
