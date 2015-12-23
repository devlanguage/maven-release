package org.third.spring.config.bean_wire;

import java.lang.reflect.Method;

/**
 */
// no more Spring imports!
public class CmdRepService_Replacer //
        implements org.springframework.beans.factory.support.MethodReplacer {

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.support.MethodReplacer#reimplement(java.lang.Object,
     * java.lang.reflect.Method, java.lang.Object[])
     */
    // @Override
    public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
        System.out.println(this.getClass().getSimpleName() + ".reimplement() is called which reimplement the " + method);
        System.out.println("\tOriginal Object: " + obj);
        System.out.println("\tOriginal Method: " + method.getName());
        System.out.println("\tInput Parameter: " + args);
        return null;
    }

}