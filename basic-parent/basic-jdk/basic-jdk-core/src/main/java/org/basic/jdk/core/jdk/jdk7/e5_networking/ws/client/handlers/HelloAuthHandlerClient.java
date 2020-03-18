package org.basic.jdk.core.jdk.jdk7.e5_networking.ws.client.handlers;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.basic.common.bean.CommonLogger;

public class HelloAuthHandlerClient<T extends SOAPMessageContext> implements SOAPHandler<SOAPMessageContext> {
    CommonLogger logger = CommonLogger.getLogger(HelloAuthHandlerClient.class);

    public HelloAuthHandlerClient() {
        // System.out.println(this.getClass().getName() + " initialling");
    }

    public boolean handleMessage(SOAPMessageContext context) {
        // System.out.println("Client : handleMessage()......");

        Boolean isRequest = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        // if this is a request, true for outbound messages, false for inbound
        if (isRequest) {
            try {
                SOAPMessage soapMsg = context.getMessage();
                SOAPEnvelope soapEnv = soapMsg.getSOAPPart().getEnvelope();
                SOAPHeader soapHeader = soapEnv.getHeader();

                // if no header, add one
                if (soapHeader == null) {
                    soapHeader = soapEnv.addHeader();
                }

                // add an node named "auth"
                QName qname = new QName("http://handler.sws.com/", "auth");
                SOAPHeaderElement soapHeaderElement = soapHeader.addHeaderElement(qname);

                // set attribute value
                soapHeaderElement.setActor(SOAPConstants.URI_SOAP_ACTOR_NEXT);
                soapHeaderElement.addTextNode("admin&admin");
                soapMsg.saveChanges();

                // tracking
                 soapMsg.writeTo(System.out);

                logger.log(CommonLogger.DEBUG, soapMsg.toString());
            } catch (Exception e) {
                logger.log(CommonLogger.ERROR, e);
            }

        }
        return true;
    }

    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    public void close(MessageContext context) {

    }

    public Set<QName> getHeaders() {
        return null;
    }

}