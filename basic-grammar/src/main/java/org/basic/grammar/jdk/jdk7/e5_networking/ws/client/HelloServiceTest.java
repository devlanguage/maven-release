package org.basic.grammar.jdk.jdk7.e5_networking.ws.client;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.jws.HandlerChain;
import javax.xml.ws.AsyncHandler;
import javax.xml.ws.Response;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import junit.framework.TestCase;

import org.basic.common.bean.CommonLogger;
import org.basic.grammar.jdk.jdk7.e5_networking.ws.HelloConstant;
import org.basic.grammar.jdk.jdk7.e5_networking.ws.client.doc_encoded_bare.HelloService;
import org.basic.grammar.jdk.jdk7.e5_networking.ws.client.handlers.HelloAuthHandlerClient;

public class HelloServiceTest extends TestCase {
    public final static CommonLogger logger = CommonLogger.getLogger(HelloServiceTest.class);
    public static URL wsdl = null;
    static {
        try {
            wsdl = new URL(HelloConstant.HelloService_endPoint + "?wsdl");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    class AuthHandlerResolve implements HandlerResolver {
        @Override
        @SuppressWarnings("rawtypes")
        public List<Handler> getHandlerChain(PortInfo portInfo) {
            List<Handler> handlers = new ArrayList<>(10);
            handlers.add(new HelloAuthHandlerClient<SOAPMessageContext>());
            return handlers;
        }
    }

    AuthHandlerResolve authHandlerResolve = new AuthHandlerResolve();

    public void setHandlerResolver(Object obj) {
        try {
            java.lang.reflect.Method setHandlerResolver = obj.getClass().getMethod("setHandlerResolver",
                    new Class[] { HandlerResolver.class });
            try {
                setHandlerResolver.invoke(obj, authHandlerResolve);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
    }

    public final static String userName = "admin";
    public final static int age = 22;

    public void testAsyncCallWithResponse() {
        org.basic.grammar.jdk.jdk7.e5_networking.ws.client.async.HelloService helloService = new org.basic.grammar.jdk.jdk7.e5_networking.ws.client.async.HelloService(wsdl);
        setHandlerResolver(helloService);
        org.basic.grammar.jdk.jdk7.e5_networking.ws.client.async.HelloServicePort helloPort = helloService.getHelloServicePortPort();
        Response<org.basic.grammar.jdk.jdk7.e5_networking.ws.client.async.User> response = helloPort.sayHelloAsync(userName, age);
        try {
            logger.info("Client:" + response.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void testAsyncCallWithFuture() {
        class HelloAsyncHandler implements AsyncHandler<org.basic.grammar.jdk.jdk7.e5_networking.ws.client.async.User> {
            @Override
            public void handleResponse(Response<org.basic.grammar.jdk.jdk7.e5_networking.ws.client.async.User> res) {
                logger.info("handlReponse: " + res);
            }
        }
        org.basic.grammar.jdk.jdk7.e5_networking.ws.client.async.HelloService helloService = new org.basic.grammar.jdk.jdk7.e5_networking.ws.client.async.HelloService(wsdl);
        setHandlerResolver(helloService);
        org.basic.grammar.jdk.jdk7.e5_networking.ws.client.async.HelloServicePort helloPort = helloService.getHelloServicePortPort();
        logger.info("start to Calling sayHello");
        Future<?> response = helloPort.sayHelloAsync(userName, age, new HelloAsyncHandler());
        logger.info("end to Calling sayHello \n start to wait for result");
        try {
            Object result = response.get();
            logger.info("Result: " + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public void testHandlerWithAnnotation() {
        @WebServiceClient(targetNamespace = "http://www.jd6webservice.org/hello", wsdlLocation = HelloConstant.HelloService_endPoint
                + "?wsdl")
        @HandlerChain(file = "/org/basic/jdk6/ws/client/handler-chain.xml")
        class HelloServiceClientImpl extends org.basic.grammar.jdk.jdk7.e5_networking.ws.client.rpc_encoded_wrapped.HelloService {
            public HelloServiceClientImpl(URL wsdl) {
                super(wsdl);
            }
        }
        HelloServiceClientImpl helloService = new HelloServiceClientImpl(wsdl);
        org.basic.grammar.jdk.jdk7.e5_networking.ws.client.rpc_encoded_wrapped.HelloServicePort helloPort = helloService
                .getHelloServicePortPort();
        logger.info("Client:" + helloPort.sayHello("test01", 22));
    }

    public void testHandlerWithResolve() {
        org.basic.grammar.jdk.jdk7.e5_networking.ws.client.rpc_encoded_wrapped.HelloService helloService = new org.basic.grammar.jdk.jdk7.e5_networking.ws.client.rpc_encoded_wrapped.HelloService(
                wsdl);
        helloService.setHandlerResolver(new HandlerResolver() {

            @Override
            @SuppressWarnings("rawtypes")
            public List<Handler> getHandlerChain(PortInfo portInfo) {
                List<Handler> handlers = new ArrayList<>(10);
                handlers.add(new HelloAuthHandlerClient<SOAPMessageContext>());
                return handlers;
            }
        });
        org.basic.grammar.jdk.jdk7.e5_networking.ws.client.rpc_encoded_wrapped.HelloServicePort helloPort = helloService
                .getHelloServicePortPort();
        logger.info("Client:" + helloPort.sayHello("test01", 22));

    }

    public void testRpcEncodedWrapped() {
        org.basic.grammar.jdk.jdk7.e5_networking.ws.client.rpc_encoded_wrapped.HelloService helloService = new org.basic.grammar.jdk.jdk7.e5_networking.ws.client.rpc_encoded_wrapped.HelloService(
                wsdl);
        setHandlerResolver(helloService);
        org.basic.grammar.jdk.jdk7.e5_networking.ws.client.rpc_encoded_wrapped.HelloServicePort helloPort = helloService
                .getHelloServicePortPort();

        logger.info("Client:" + helloPort.listUser("test01"));
    }

    public void testRpcLiteralWrapped() {

    }

    public void testDocEncodedWrapped() {

    }

    public void testDocLiteralWrapped() {

    }

    public void testDocEncodedBare() {
      org.basic.grammar.jdk.jdk7.e5_networking.ws.client.doc_encoded_bare.HelloService helloService = new HelloService(wsdl);
      setHandlerResolver(helloService);
      org.basic.grammar.jdk.jdk7.e5_networking.ws.client.doc_encoded_bare.HelloServicePort helloPort = helloService
              .getHelloServicePortPort();

      logger.info("Client:" + helloPort.listUser("test01"));
      

    }

    public void testDocLiteralBare() {

    }

    public static void main(String[] args) throws MalformedURLException {
      HelloServiceTest t=new HelloServiceTest();
      t.testDocEncodedBare();
    }

}
