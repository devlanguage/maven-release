package org.basic.net.c20_jmx.mbean.hello;

import org.basic.net.c20_jmx.mbean.BaseMBean;

public interface HelloWorldMBean extends BaseMBean {
    String MBEAN_NAME = HelloWorldMBean.class.getName();

    public void setGreeting(String greeting);

    public String getGreeting();

    public void printGreeting();

}
