package org.third.spring.event;

import org.springframework.context.ApplicationEvent;

public class EmailBlackListEvent_ApplicationEvent extends ApplicationEvent {

    public EmailBlackListEvent_ApplicationEvent(Object source, String text) {

        super(source);
    }
}
