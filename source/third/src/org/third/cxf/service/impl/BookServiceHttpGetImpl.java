/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package org.third.cxf.service.impl;

import java.util.logging.Logger;

import org.third.cxf.service.book.BookType;
import org.third.cxf.service.book.IBookService;

/**
 * This class was generated by Apache CXF 2.7.6 2014-03-06T16:33:54.053+08:00 Generated source version: 2.7.6
 * 
 */

@javax.jws.WebService(serviceName = "BookService", portName = "BookServicePort_HttpGet", targetNamespace = "http://service.cxf.third.org/Book/", wsdlLocation = "/org/third/cxf/service/wsdl/BookService.wsdl", endpointInterface = "org.third.cxf.service.book.IBookService")
public class BookServiceHttpGetImpl implements IBookService {

    private static final Logger LOG = Logger.getLogger(BookServiceHttpPostImpl.class.getName());

    /*
     * (non-Javadoc)
     * 
     * @see org.third.cxf.service.book.IBookService#getBookByName(java.lang.String bookName )*
     */
    public org.third.cxf.service.book.BookType getBookByName(java.lang.String bookName) {
        LOG.info("Executing operation getBookByName");
        System.out.println(bookName);
        try {
            org.third.cxf.service.book.BookType _return = new BookType();
            _return.setBookId(102303l);
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
