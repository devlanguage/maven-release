package org.basic.jdk.core.jdk.jdk7.e5_networking.ws.client.sync;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Action;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "HelloServicePort", targetNamespace = "http://www.wstest.org/hello")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface HelloServicePort {


    /**
     * 
     * @param sayHelloUserPart
     * @param sayHelloAgePart
     * @return
     *     returns java.lang.String
     */
    @WebMethod(action = "sayHelloAction")
    @WebResult(name = "sayHelloPart", partName = "sayHelloPart")
    @Action(input = "sayHelloAction", output = "http://www.wstest.org/hello/HelloServicePort/sayHelloResponse")
    public String sayHello(
        @WebParam(name = "sayHelloUserPart", partName = "sayHelloUserPart")
        String sayHelloUserPart,
        @WebParam(name = "sayHelloAgePart", partName = "sayHelloAgePart")
        int sayHelloAgePart);

}
