package org.basic.aop.decoupled;

import java.lang.reflect.Method;

public interface IOperation {
    /**  方法执行之前的操作 */
    void start(Method method);
    /** 方法执行之后的操作*/
    void end(Method method);
}