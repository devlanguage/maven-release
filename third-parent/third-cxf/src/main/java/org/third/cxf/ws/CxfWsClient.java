package org.third.cxf.ws;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.third.cxf.service.BookService;

/**
 * Created on Mar 6, 2014, 2:52:29 PM
 */
public class CxfWsClient {
    public static void main(String[] args) {

//        org.apache.cxf.jaxws.JaxWsServerFactoryBean factoryBean=new JaxWsServerFactoryBean();
//        factoryBean.setAddress("http://localhost:9000/hello");
//        factoryBean.setServiceClass(IBookService.class);
//        factoryBean.setServiceBean(new BookService());
//        factoryBean.getInInterceptors().add(new LoggingInInterceptor());
//        factoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
//        factoryBean.create();
//        
        // 创建WebService客户端代理工厂
        org.apache.cxf.jaxws.JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        // 注册WebService接口j
        factory.setServiceClass(BookService.class);
        // 设置WebService地址
        factory.setAddress("http://localhost:9000/hello");
        BookService greetingService = (BookService) factory.create();
        System.out.println("invoke webservice...");
        System.out.println("message context is:" + greetingService.getBookByName("gary"));
    }
}
