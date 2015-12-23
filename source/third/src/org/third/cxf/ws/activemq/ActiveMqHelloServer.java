/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 10:45:51 AM Oct 24, 2014
 *
 *****************************************************************************
 */
package org.third.cxf.ws.activemq;

import javax.xml.ws.Endpoint;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.transport.jms.spec.JMSSpecConstants;
import org.third.cxf.service.HelloWorld;
import org.third.cxf.service.impl.HelloWorldImpl;

/**
 * Created on Oct 24, 2014, 10:45:51 AM
 */
public class ActiveMqHelloServer {
    private static final String JMS_ENDPOINT_URI = "jms:queue:test.cxf.jmstransport.queue?timeToLive=1000"
            + "&jndiConnectionFactoryName=ConnectionFactory" + "&jndiInitialContextFactory"
            + "=org.apache.activemq.jndi.ActiveMQInitialContextFactory";

    public static void main(String args[]) throws Exception {

        boolean jaxws = false;

        for (String arg : args) {
            if ("-jaxws".equals(arg)) {
                jaxws = true;
            }
        }

        if (jaxws) {
            launchJaxwsApi();
        } else {
            launchCxfApi();
        }

        System.out.println("Server ready... Press any key to exit");
        System.in.read();
        System.out.println("Server exiting");
        System.exit(0);
    }

    private static void launchCxfApi() {
        Object implementor = new HelloWorldImpl();
        JaxWsServerFactoryBean svrFactory = new JaxWsServerFactoryBean();
        svrFactory.setServiceClass(HelloWorld.class);
        svrFactory.setTransportId(JMSSpecConstants.SOAP_JMS_SPECIFICATION_TRANSPORTID);
        svrFactory.setAddress(JMS_ENDPOINT_URI);
        svrFactory.setServiceBean(implementor);
        svrFactory.create();
    }

    private static void launchJaxwsApi() {
        Endpoint.publish(JMS_ENDPOINT_URI, new HelloWorldImpl());
    }

}
