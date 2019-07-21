package org.third.cxf.jms.jms_greeter.server;

import java.util.logging.Logger;

import org.third.cxf.jms.jms_greeter.JMSGreeterPortType;

@javax.jws.WebService(portName = "GreeterPort", //
serviceName = "JMSGreeterService",//
targetNamespace = "http://cxf.apache.org/jms_greeter",//
endpointInterface = "org.third.cxf.jms.jms_greeter.JMSGreeterPortType",//
//wsdlLocation = "file:./wsdl/jms_greeter.wsdl")
wsdlLocation =  "/org/third/cxf/jms/jms_greeter/server/jms_greeter.wsdl")
public class GreeterJMSImpl implements JMSGreeterPortType {

    private static final Logger LOG = Logger.getLogger(GreeterJMSImpl.class.getPackage().getName());

    public String greetMe(String me) {
        LOG.info("Executing operation greetMe");
        System.out.println("Executing operation greetMe");
        System.out.println("Message received: " + me + "\n");
        return "Hello " + me;
    }

    public String sayHi() {
        LOG.info("Executing operation sayHi");
        System.out.println("Executing operation sayHi" + "\n");
        return "Bonjour";
    }

    public void greetMeOneWay(String me) {
        LOG.info("Executing operation greetMeOneWay");
        System.out.println("Executing operation greetMeOneWay\n");
        System.out.println("Hello there " + me);
    }
}
