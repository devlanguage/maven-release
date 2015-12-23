package org.third.spring.basic.aware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class BeanFactoryAware_impl implements BeanFactoryAware {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.beans.factory.BeanFactoryAware#setBeanFactory(org.springframework.beans.factory.BeanFactory)
     */
    public void setBeanFactory(BeanFactory arg0) throws BeansException {
        System.out.println("BeanFactoryAware_impl: setBeanFactory(" + arg0 + ")");

    }

}
