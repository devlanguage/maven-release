package org.basic.net.c20_jmx.mbean.user;

import org.basic.net.c20_jmx.mbean.BaseMBean;

public interface UserManagerMBean extends BaseMBean {
    String MBEAN_NAME = UserManagerMBean.class.getName();

    public String getUserName();

    public void setUserName(String user);

    public String sayHi(String user);

}
