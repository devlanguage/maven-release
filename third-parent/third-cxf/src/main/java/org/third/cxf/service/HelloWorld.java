package org.third.cxf.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface HelloWorld {
    @WebMethod
    String sayHi(@WebParam(name = "name") String name);
    
    
}
