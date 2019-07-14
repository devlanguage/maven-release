package org.basic.aop.decoupled;

import java.lang.reflect.Method;

import org.basic.aop.AopLevel;
import org.basic.aop.AopLogger;

public class LoggerOperation implements IOperation {
    public void end(Method method) {
        AopLogger.logging(AopLevel.DEBUG, method.getName() + " Method end .");
    }
    public void start(Method method) {
        AopLogger.logging(AopLevel.INFO, method.getName() + " Method Start!");
    }
}