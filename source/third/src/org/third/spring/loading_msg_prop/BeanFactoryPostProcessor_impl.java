package org.third.spring.loading_msg_prop;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class BeanFactoryPostProcessor_impl implements BeanFactoryPostProcessor {

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
            throws BeansException {

        System.out.println("BeanFactory PostProcessor" + " is called");
    }

}
