/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 9:58:12 AM Sep 28, 2014
 *
 *****************************************************************************
 */
package org.third.cxf.rest;

import java.net.URL;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.JAXRSBindingFactory;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.basic.common.bean.CommonLogger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.third.cxf.service.book.BookService;
import org.third.cxf.service.book.IBookService;
import org.third.cxf.service.teacher.TeacherService;

public class CxfRestServer {
    static final String rs_address = "http://localhost:9000/rs/";
    static final String ws_address = "http://localhost:9000/ws/";
    
    static final Class[] SERVICE_LIST = new Class[] { //
    TeacherService.class //
    };
    public static final CommonLogger log = CommonLogger.getLog(CxfRestServer.class);
    public static final URL SPRING_CONFIG_FILE = CxfRestServer.class.getResource("cxf_rest_server.xml");

    // http://localhost:9000/rest/teacher?_type=json
    // http://localhost:9000/rest/teacher?_type=xml
    public static void main(String[] args) {
        // ApplicationContext ctx = new ClassPathXmlApplicationContext(SPRING_CONFIG_FILE.getPath());
        ApplicationContext ctx = new FileSystemXmlApplicationContext(SPRING_CONFIG_FILE.getPath());

        SpringBusFactory bf = new SpringBusFactory();
        Bus cxfBus;
        try {
            cxfBus = bf.createBus(SPRING_CONFIG_FILE);
        } catch (Exception e) {
            log.error("createBus with config file[" + SPRING_CONFIG_FILE + "] failed.", e);
            return;
        }
        SpringBusFactory.setThreadDefaultBus(cxfBus);

        // // org.apache.cxf.endpoint.AbstractEndpointFactory
        // org.apache.cxf.jaxws.JaxWsServerFactoryBean wsEndpointFactory = new JaxWsServerFactoryBean();
        // wsEndpointFactory.setAddress(ws_address);
        // wsEndpointFactory.setServiceClass(IBookService.class);
        // wsEndpointFactory.setServiceBean(new BookService());
        // wsEndpointFactory.getInInterceptors().add(new LoggingInInterceptor());
        // wsEndpointFactory.getOutInterceptors().add(new LoggingOutInterceptor());
        // wsEndpointFactory.create();

        // org.apache.cxf.endpoint.AbstractEndpointFactory
        org.apache.cxf.jaxrs.JAXRSServerFactoryBean rsEndpointFactory = new JAXRSServerFactoryBean();
        rsEndpointFactory.setAddress(rs_address);
        rsEndpointFactory.setResourceClasses(SERVICE_LIST);
        for (Class serviceImpl : SERVICE_LIST) {
            try {
                // Endpoint ep;
                // ep = Endpoint.publish(address + serviceImpl.getSimpleName(), serviceImpl.newInstance());
                //
                // // Add TNBI logging interceptor for this Endpoint
                // org.apache.cxf.jaxws.EndpointImpl epImpl = (org.apache.cxf.jaxws.EndpointImpl) ep;
                // epImpl.getServer().getEndpoint().getInInterceptors().add(new LoggingInInterceptor());
                // epImpl.getServer().getEndpoint().getOutInterceptors().add(new LoggingOutInterceptor());
                // log.info(address + serviceImpl + " published");
                 /* default lifecycle is per-request. */
//                rsEndpointFactory.setResourceProvider(serviceImpl, new SingletonResourceProvider(serviceImpl.newInstance())); /*                                                                                                                         */
//                 org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider: The default singleton resource provider which returns the same resource instance per every request
//                org.apache.cxf.jaxrs.blueprint.BlueprintResourceFactory
//                org.apache.cxf.jaxrs.lifecycle.PerRequestResourceProvider:The default per-request resource provider which creates a new resource instance per every request
//                org.apache.cxf.jaxrs.spring.SpringResourceProvider: The ResourceProvider implementation which delegates to ApplicationContext to manage the life-cycle of the resource
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        rsEndpointFactory.create();

        JAXRSBindingFactory restBindingFactory = new JAXRSBindingFactory();
        restBindingFactory.setBus(cxfBus);
    }
}
