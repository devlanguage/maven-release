package org.basic.net.c20_jmx.jdmk.legacy.JdmkProxyMBeans;

//
// Generated by proxygen version 5.1 when compiling MLet (Tue Apr 13 19:32:36 MEST 2004).
//

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanRegistrationException;
import javax.management.ReflectionException;
import java.net.URL;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Set;


/**
 * The proxy MBean interface of the MLet MBean.
 *
 * @see com.sun.jdmk.tools.ProxyGen 
 */
public interface MLetProxyMBean {

  public URL[] getURLs()
    throws InstanceNotFoundException, AttributeNotFoundException,
    ReflectionException, MBeanException;

  public java.lang.String getLibraryDirectory()
    throws InstanceNotFoundException, AttributeNotFoundException,
    ReflectionException, MBeanException;

  public  void setLibraryDirectory(java.lang.String value)
    throws InstanceNotFoundException, ReflectionException,
    AttributeNotFoundException,InvalidAttributeValueException,
    MBeanException;

  public java.net.URL getResource(java.lang.String p0)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public java.io.InputStream getResourceAsStream(java.lang.String p0)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public java.util.Enumeration getResources(java.lang.String p0)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public void addURL(java.net.URL p0)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public void addURL(java.lang.String p0)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public java.util.Set getMBeansFromURL(java.net.URL p0)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public java.util.Set getMBeansFromURL(java.lang.String p0)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;


}
