package org.basic.net.c20_jmx.mbean;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.PersistentMBean;
import javax.management.ReflectionException;
import javax.management.RuntimeOperationsException;
import javax.management.modelmbean.InvalidTargetObjectTypeException;
import javax.management.modelmbean.ModelMBeanInfo;
import javax.management.modelmbean.ModelMBeanNotificationBroadcaster;

public interface ModelMBean extends DynamicMBean, PersistentMBean,
        ModelMBeanNotificationBroadcaster {

    //From javax.management.DynamicMBean
    public Object getAttribute(String attribute) throws AttributeNotFoundException, MBeanException,
            ReflectionException;
    public AttributeList getAttributes(String[] attributes);
    public MBeanInfo getMBeanInfo();
    public Object invoke(String actionName, Object[] params, String[] signature)
            throws MBeanException, ReflectionException;

    public void setAttribute(Attribute attribute) throws AttributeNotFoundException,
            InvalidAttributeValueException, MBeanException, ReflectionException;
    public AttributeList setAttributes(AttributeList attributes);
    
    // From javax.management.PersistentMBean
    public void load() throws MBeanException, RuntimeOperationsException, InstanceNotFoundException;
    public void store() throws MBeanException, RuntimeOperationsException,
            InstanceNotFoundException;

    //Self-defied
    public void setModelMBeanInfo(ModelMBeanInfo inModelMBeanInfo) throws MBeanException,
            RuntimeOperationsException;
    public void setManagedResource(Object mr, String mr_type) throws MBeanException,
            RuntimeOperationsException, InstanceNotFoundException, InvalidTargetObjectTypeException;
}