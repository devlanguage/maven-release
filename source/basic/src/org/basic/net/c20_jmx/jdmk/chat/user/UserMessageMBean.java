package org.basic.net.c20_jmx.jdmk.chat.user;

import java.util.Set;

import javax.management.ObjectInstance;

public interface UserMessageMBean extends BaseMBean {

    public void setContent(String content);

    public String getContent();
    
    public String sayHello(String hello);

    public Set<ObjectInstance> queryMBean(String expression);
}
