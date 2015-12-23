package org.basic.jdk.jdk7.e5_networking.ws.server;

import java.util.ArrayList;

import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.xml.ws.Endpoint;

import org.basic.jdk.jdk7.e5_networking.ws.HelloConstant;

/**
 * <pre>
 * WebService:
 *   name: wsdl:portType
 *   serviceName: wsdl:service
 * 
 * </pre>
 */
@WebService(name = "HelloServicePort", serviceName = "HelloService", targetNamespace = HelloConstant.HelloService_targetNamespace)
/** ParameterStyle can only be WRAPPED with RPC Style Web service. */
// //@SOAPBinding(style = Style.RPC, use = Use.ENCODED, parameterStyle = ParameterStyle.BARE)  // INVALID
// //@SOAPBinding(style = Style.RPC, use = Use.LITERAL, parameterStyle = ParameterStyle.BARE)   //INVALID
//@SOAPBinding(style = Style.RPC, use = Use.ENCODED, parameterStyle = ParameterStyle.WRAPPED)
// @SOAPBinding(style = Style.RPC, use = Use.LITERAL, parameterStyle = ParameterStyle.WRAPPED)

 @SOAPBinding(style = Style.DOCUMENT, use = Use.ENCODED, parameterStyle = ParameterStyle.BARE) // ONLY One parameter valid
// @SOAPBinding(style = Style.DOCUMENT, use = Use.ENCODED, parameterStyle = ParameterStyle.WRAPPED)
// @SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL, parameterStyle = ParameterStyle.BARE)
// @SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL, parameterStyle = ParameterStyle.WRAPPED)
@HandlerChain(file = "handler-chain.xml")
public class HelloServiceImpl implements HelloService {

    /**
     * <pre>
     * WebMethod:
     *   action: soapAction
     *   operationName: public wsdl:opeartion name, same as methodName by default
     * WebResult:
     *   name: resultName in wsdl, "result" by default. 
     * WebParam:
     *   name: opeartion input arg name
     * </pre>
     */
//    @WebMethod(action = "sayHelloAction", operationName = "sayHello", exclude = false)
//    @WebResult(header = false, name = "sayHelloResult", partName = "sayHelloPart")
//    public User sayHello(@WebParam(header = false, name = "userName", partName = "sayHelloUserPart") String userName,
//            @WebParam(header = false, name = "userAge", partName = "sayHelloAgePart") int age) {
//        return new User("Hello:" + userName);
//    }

//    java.util.List is an interface, and JAXB can't handle interfaces.
    public User[] listUser(String userName) {
        ArrayList<User> users= new ArrayList<User>();
        users.add(new User("test02"));
        users.add(new User("test03"));                
        return  users.toArray(new User[users.size()]);
    }

    // ExecutorService executorService = Executors.newFixedThreadPool(10);
    // @WebMethod
    // @RequestWrapper(localName = "addNumberRequest")
    // @ResponseWrapper(localName = "addNumberRseponse")
    // public int addNumber(final int number1, final int number2) {
    //
    // System.out.println("Received Request!");
    // System.out.println("Sleeping for 5 seconds");
    // // Future<Integer> result = executorService.submit(new Callable<Integer>() {
    // //
    // // @Override
    // // public Integer call() throws Exception {
    // // try {
    // // Thread.sleep(5000);
    // // } catch (InterruptedException e) {
    // // throw new RuntimeException(e);
    // // }
    // // return number1 + number2;
    // // }
    // // });
    // // return result;
    // return number1 + number2;
    // }
    //
    // @Override
    // public Integer calculateSalary(int number1, int number2) {
    // int result = number1 + number2;
    // return null;
    // }

    public static void main(String[] args) {
        Endpoint.publish(HelloConstant.HelloService_endPoint, new HelloServiceImpl());
    }

}
