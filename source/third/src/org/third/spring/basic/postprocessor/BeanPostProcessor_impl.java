package org.third.spring.basic.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import static org.basic.common.util.SystemUtil.*;

public class BeanPostProcessor_impl implements BeanPostProcessor {

    // simply return the instantiated bean as-is
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        printMessage(this.getClass().getSimpleName() + ": postProcessBeforeInitialization(" + bean + "," + beanName + ")");
        return bean; // we could potentially return any object reference here...
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        printMessage(this.getClass().getSimpleName() + ": postProcessAfterInitialization(" + bean + "," + beanName + ")");
        return bean;
    }
}