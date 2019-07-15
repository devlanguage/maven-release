package org.third.spring.basic.aware;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * Created on Apr 11, 2014, 9:30:01 AM
 */
public class ApplicationContextListener_impl<T extends ApplicationEvent> //
        implements ApplicationListener<ApplicationEvent> {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
     */
    public void onApplicationEvent(ApplicationEvent arg0) {
        System.out.println("ApplicationContextListener_impl:" + arg0);
    }

}
