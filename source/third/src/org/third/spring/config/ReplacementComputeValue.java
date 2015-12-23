package org.third.spring.config;

import java.lang.reflect.Method;

import org.springframework.beans.factory.support.MethodReplacer;

//class MyValueCalculator {
//
//    public String computeValue(String input) {
//
//        // some real code...
//        return null;
//    }
//}

// 实现org.springframework.beans.factory.support.MethodReplacer接口的类提供了新的方法定义。

/**
 * meant to be used to override the existing computeValue implementation in MyValueCalculator
 */
public class ReplacementComputeValue implements MethodReplacer {

    public Object reimplement(Object o, Method m, Object[] args) throws Throwable {

        // get the input value, work with it, and return a computed result
        String input = (String) args[0];
        // ...
        return null;// ...;
    }
}
