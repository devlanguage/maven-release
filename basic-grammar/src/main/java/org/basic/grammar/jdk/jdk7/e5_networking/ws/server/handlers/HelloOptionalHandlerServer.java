package org.basic.grammar.jdk.jdk7.e5_networking.ws.server.handlers;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class HelloOptionalHandlerServer implements SOAPHandler<SOAPMessageContext> {

    @Override
    public boolean handleMessage(SOAPMessageContext context) {

        Object requestHeader = context.get(MessageContext.HTTP_REQUEST_HEADERS);
        Object requestMethod = context.get(MessageContext.HTTP_REQUEST_METHOD);
        Object servletContext = context.get(MessageContext.SERVLET_CONTEXT);
        Object servletRequest = context.get(MessageContext.SERVLET_REQUEST);
        Object pathInfo = context.get(MessageContext.PATH_INFO);
        Object queryString = context.get(MessageContext.QUERY_STRING);
        Object referenceParameter = context.get(MessageContext.REFERENCE_PARAMETERS);
        Object inboundAttachment = context.get(MessageContext.INBOUND_MESSAGE_ATTACHMENTS);
        
        

        Object responseCode = context.get(MessageContext.HTTP_RESPONSE_CODE);
        Object responseHeader = context.get(MessageContext.HTTP_RESPONSE_HEADERS);
        Object servletResponse = context.get(MessageContext.SERVLET_RESPONSE);
        Object outboundAttachment = context.get(MessageContext.OUTBOUND_MESSAGE_ATTACHMENTS);
        Object outboundMessageProperty = context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        
        
        return true;
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
