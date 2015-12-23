package org.third.spring.event;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class EmailBean_ApplicationContextAware implements ApplicationContextAware {

    private List blackList;
    private ApplicationContext ctx;

    public void setBlackList(List blackList) {

        this.blackList = blackList;
    }

    public void setApplicationContext(ApplicationContext ctx) {

        this.ctx = ctx;
    }

    public void sendEmail(String address, String text) {

        if (blackList.contains(address)) {
            EmailBlackListEvent_ApplicationEvent evt = new EmailBlackListEvent_ApplicationEvent(address, text);
            ctx.publishEvent(evt);
            return;
        }
        // send email...
    }
}