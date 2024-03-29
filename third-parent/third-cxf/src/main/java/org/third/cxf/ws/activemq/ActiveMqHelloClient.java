package org.third.cxf.ws.activemq;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.jms.spec.JMSSpecConstants;
import org.third.cxf.service.HelloWorld;

public final class ActiveMqHelloClient {
    private static final String JMS_ENDPOINT_URI = "jms:queue:test.cxf.jmstransport.queue?timeToLive=1000"
                               + "&jndiConnectionFactoryName=ConnectionFactory" + "&jndiInitialContextFactory"
                               + "=org.apache.activemq.jndi.ActiveMQInitialContextFactory";

    private static final QName SERVICE_QNAME =
        new QName("http://impl.service.demo/", "HelloWorldImplService");
    private static final QName PORT_QNAME =
        new QName("http://service.demo/", "HelloWorldPort");

    private ActiveMqHelloClient() {
        //
    }

    public static void main(String[] args) throws Exception {
        boolean jaxws = false;
        for (String arg : args) {
            if ("-jaxws".equals(arg)) {
                jaxws = true;
            }
        }
        HelloWorld client = null;
        if (jaxws) {
            client = createClientJaxWs();
        } else {
            client = createClientCxf();
        }
        String reply = client.sayHi("HI");
        System.out.println(reply);
        System.exit(0);
    }

    private static HelloWorld createClientJaxWs() {
        Service service = Service.create(SERVICE_QNAME);
        // Add a port to the Service
        service.addPort(PORT_QNAME, JMSSpecConstants.SOAP_JMS_SPECIFICATION_TRANSPORTID, JMS_ENDPOINT_URI);
        return service.getPort(HelloWorld.class);
    }

    private static HelloWorld createClientCxf() {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setTransportId(JMSSpecConstants.SOAP_JMS_SPECIFICATION_TRANSPORTID);
        factory.setAddress(JMS_ENDPOINT_URI);
        HelloWorld client = factory.create(HelloWorld.class);
        return client;
    }
}
