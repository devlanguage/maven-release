package org.basic.net.c20_jmx.jdmk.legacy.JdmkProxyMBeans;

//
// Generated by proxygen version 5.1 when compiling MBeanServerDelegate (Tue Apr 13 19:31:17 MEST 2004).
//

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanRegistrationException;
import javax.management.ReflectionException;
import javax.management.Attribute;
import javax.management.ObjectInstance;
import com.sun.jdmk.comm.CommunicationException;
import com.sun.jdmk.comm.RemoteMBeanServer;
import com.sun.jdmk.ProxyHandler;
import javax.management.ObjectName;
import javax.management.NotificationListener;
import javax.management.NotificationFilter;
import javax.management.ListenerNotFoundException;


/**
 * The implementation of the MBeanServerDelegateProxyMBean Proxy MBean interface.
 *
 * @see com.sun.jdmk.tools.ProxyGen 
 */
public class MBeanServerDelegateProxy implements MBeanServerDelegateProxyMBean, com.sun.jdmk.Proxy, com.sun.jdmk.NotificationBroadcasterProxy {


  //
  // Variables required by Proxy interface
  //
  protected ObjectInstance objectInstance = null;
  protected ProxyHandler server = null;


  /**
   *  constructor with ObjectInstance parameter
   */
  public MBeanServerDelegateProxy(ObjectInstance oi) {    
    objectInstance = oi ;
  }

  /**
   *  constructor with ObjectInstance parameter and ProxyHandler server
   */
  public MBeanServerDelegateProxy(ObjectInstance oi, ProxyHandler server) {    
    objectInstance = oi ;
    this.server = server ;
  }

  public java.lang.String getImplementationVendor()
    throws InstanceNotFoundException, AttributeNotFoundException,
    ReflectionException, MBeanException {

      return ((java.lang.String)server.getAttribute(objectInstance.getObjectName(), "ImplementationVendor"));
    }

  public java.lang.String getImplementationVersion()
    throws InstanceNotFoundException, AttributeNotFoundException,
    ReflectionException, MBeanException {

      return ((java.lang.String)server.getAttribute(objectInstance.getObjectName(), "ImplementationVersion"));
    }

  public java.lang.String getSpecificationVendor()
    throws InstanceNotFoundException, AttributeNotFoundException,
    ReflectionException, MBeanException {

      return ((java.lang.String)server.getAttribute(objectInstance.getObjectName(), "SpecificationVendor"));
    }

  public java.lang.String getSpecificationVersion()
    throws InstanceNotFoundException, AttributeNotFoundException,
    ReflectionException, MBeanException {

      return ((java.lang.String)server.getAttribute(objectInstance.getObjectName(), "SpecificationVersion"));
    }

  public java.lang.String getMBeanServerId()
    throws InstanceNotFoundException, AttributeNotFoundException,
    ReflectionException, MBeanException {

      return ((java.lang.String)server.getAttribute(objectInstance.getObjectName(), "MBeanServerId"));
    }

  public java.lang.String getSpecificationName()
    throws InstanceNotFoundException, AttributeNotFoundException,
    ReflectionException, MBeanException {

      return ((java.lang.String)server.getAttribute(objectInstance.getObjectName(), "SpecificationName"));
    }

  public java.lang.String getImplementationName()
    throws InstanceNotFoundException, AttributeNotFoundException,
    ReflectionException, MBeanException {

      return ((java.lang.String)server.getAttribute(objectInstance.getObjectName(), "ImplementationName"));
    }

  //
  // Implementation of NotificationBroadcasterProxy interface
  //
  public synchronized  void addNotificationListener(ObjectName name, NotificationListener listener, NotificationFilter filter, Object handback)
    throws InstanceNotFoundException {

    server.addNotificationListener(objectInstance.getObjectName(), listener, filter, handback);
  }

  public synchronized  void removeNotificationListener(ObjectName name, NotificationListener listener)
    throws InstanceNotFoundException, ListenerNotFoundException {

    server.removeNotificationListener(objectInstance.getObjectName(), listener);
  }

  //
  // Implementation of the Proxy interface
  //

  /**
   *  Returns the <a href="javax.management.ObjectInstance.html#_top_"> object instance</a> of the object
   */
  public ObjectInstance getMBeanObjectInstance()  {
    return (objectInstance);
  }

  public ProxyHandler getServer() {
    return server;
  }

  public void setServer(ProxyHandler server) {
    this.server = server;
  }


}
