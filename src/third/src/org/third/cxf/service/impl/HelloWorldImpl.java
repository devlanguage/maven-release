package org.third.cxf.service.impl;

import javax.jws.WebService;

import org.third.cxf.service.HelloWorld;

@WebService
public class HelloWorldImpl implements HelloWorld {
    public String sayHi(String name) {
        return "Hello " + name;
    }
}
