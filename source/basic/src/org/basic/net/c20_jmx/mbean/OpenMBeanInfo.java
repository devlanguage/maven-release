package org.basic.net.c20_jmx.mbean;

//
import javax.management.*;

public interface OpenMBeanInfo {

    public String getClassName();
    public String getDescription();

    public MBeanAttributeInfo[] getAttributes();
    public MBeanOperationInfo[] getOperations();
    public MBeanConstructorInfo[] getConstructors();
    public MBeanNotificationInfo[] getNotifications();

    public boolean equals(Object obj);
    public int hashCode();
    public String toString();
}
