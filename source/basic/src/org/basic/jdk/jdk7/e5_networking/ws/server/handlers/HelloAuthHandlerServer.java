package org.basic.jdk.jdk7.e5_networking.ws.server.handlers;

import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;

public class HelloAuthHandlerServer implements SOAPHandler<SOAPMessageContext> {

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        // HttpServletRequest request = (HttpServletRequest) context.get(AbstractHTTPDestination.HTTP_REQUEST);
        // System.out.println("客户端IP：" + request.getRemoteAddr());

        Boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (!outbound.booleanValue()) {
            SOAPMessage soapMessage = context.getMessage();

            try {
//                soapMessage.writeTo(System.out);
                SOAPEnvelope soapEnvelope = soapMessage.getSOAPPart().getEnvelope();
                SOAPHeader soapHeader = soapEnvelope.getHeader();

                if (soapHeader == null) {
                    generateSoapFault(soapMessage, "No Message Header...");
                }

                Iterator<?> it = soapHeader.extractHeaderElements(SOAPConstants.URI_SOAP_ACTOR_NEXT);
                if (it == null || !it.hasNext()) {
                    generateSoapFault(soapMessage, "No Header block for role next");
                }

                Node node = (Node) it.next();
                String value = node == null ? null : node.getValue();

                if (value == null){
                    generateSoapFault(soapMessage, "No authation info in header blocks");
                }

                String[] infos = value.split("&");
                return authValidate(infos[0], infos[1]);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return false;
    }

    private boolean authValidate(String userName, String password) {
        if (userName == null || password == null) {
            return false;
        }

        if ("admin".equals(userName) && "admin".equals(password)) {
            return true;
        }
        return false;
    }

    private void generateSoapFault(SOAPMessage soapMessage, String reasion) {
        try {
            SOAPBody soapBody = soapMessage.getSOAPBody();
            SOAPFault soapFault = soapBody.getFault();

            if (soapFault == null) {
                soapFault = soapBody.addFault();
            }

            soapFault.setFaultString(reasion);
            throw new SOAPFaultException(soapFault);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void close(MessageContext context) {
        // TODO Auto-generated method stub

    }

    @Override
    public Set<QName> getHeaders() {
        // TODO Auto-generated method stub
        return null;
    }

}
