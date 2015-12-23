package org.third.spring.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class EmailBlackListListener implements ApplicationListener {

    public void onApplicationEvent(ApplicationEvent event) {

        if (event instanceof EmailBlackListEvent) {

        }

    }

}