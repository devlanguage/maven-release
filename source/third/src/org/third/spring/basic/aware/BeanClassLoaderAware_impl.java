package org.third.spring.basic.aware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class BeanClassLoaderAware_impl implements BeanClassLoaderAware {

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.BeanClassLoaderAware#setBeanClassLoader(java.lang.ClassLoader)
     */
    public void setBeanClassLoader(ClassLoader arg0) {
        System.out.println("BeanClassLoaderAware_impl:setBeanClassLoader(" + arg0 + ")");

    }

}
