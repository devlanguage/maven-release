package org.basic.net.c16_corba.hello;

import org.basic.net.c16_corba.hello.hellostub.GoodDayPOA;
import org.basic.net.c16_corba.hello.hellostub.InternalException;

public class GoodDayImpl extends GoodDayPOA {
    private String location;

    public GoodDayImpl(String location) {
        this.location = location;
    }

    @Override
    public String helloSimple() {
        return "Hello World, from " + location;
    }

    @Override
    public String helloWide(String msg) {
        System.out.println("The message is: " + msg);
        return "Hello W�rld, from � 1 2 3 0 *&^%$#@!@";
    }

    @Override
    public String helloWideException(String msg) throws InternalException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void lock() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean ping(String hello) {
        // TODO Auto-generated method stub
        return false;
    }

}
