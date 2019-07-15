package org.third.cxf.ws;

import javax.xml.ws.Endpoint;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.third.cxf.service.impl.BookServiceHttpGetImpl;
import org.third.cxf.service.impl.BookServiceHttpPostImpl;
import org.third.cxf.service.impl.BookServiceSoapDocumentLiteralImpl;
import org.third.cxf.service.impl.BookServiceSoapRpcLiteralImpl;

/**
 * Created on Mar 6, 2014, 2:52:29 PM
 */
public class CxfWsServer {
    static final String address = "http://localhost:9000/hello/";
    static final Class[] SERVICE_LIST = new Class[] { //
    BookServiceHttpGetImpl.class, BookServiceHttpPostImpl.class,//
            BookServiceSoapDocumentLiteralImpl.class, BookServiceSoapRpcLiteralImpl.class //
    };

    public static void main(String[] args) {

        // JaxWsServerFactoryBean factoryBean = new JaxWsServerFactoryBean();
        // factoryBean.setAddress("http://localhost:9000/hello");
        // factoryBean.setServiceClass(IBookService.class);
        // factoryBean.setServiceBean(new BookService());
        // factoryBean.getInInterceptors().add(new LoggingInInterceptor());
        // factoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
        // factoryBean.create();

        for (Class serviceImpl : SERVICE_LIST) {
            Endpoint ep;
            try {
                ep = Endpoint.publish(address + serviceImpl.getSimpleName(), serviceImpl.newInstance());

                // Add TNBI logging interceptor for this Endpoint
                org.apache.cxf.jaxws.EndpointImpl epImpl = (org.apache.cxf.jaxws.EndpointImpl) ep;
                epImpl.getServer().getEndpoint().getInInterceptors().add(new LoggingInInterceptor());
                epImpl.getServer().getEndpoint().getOutInterceptors().add(new LoggingOutInterceptor());
            } catch (InstantiationException | IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
}
