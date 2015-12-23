package org.basic.net.c20_jmx.mbean.simple;

//
// Generated by proxygen version 5.1 when compiling SimpleStandard (Tue Apr 13 19:21:57 MEST 2004).
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


/**
 * The implementation of the SimpleStandardProxyMBean Proxy MBean interface.
 *
 * @see com.sun.jdmk.tools.ProxyGen 
 */
public class SimpleStandardProxy implements SimpleStandardProxyMBean, com.sun.jdmk.Proxy {


  //
  // Variables required by Proxy interface
  //
  protected ObjectInstance objectInstance = null;
  protected ProxyHandler server = null;

  /**
   *  constructor with ObjectInstance parameter
   */
  public SimpleStandardProxy(ObjectInstance oi) {    
    objectInstance = oi ;
  }

  /**
   *  constructor with ObjectInstance parameter and ProxyHandler server
   */
  public SimpleStandardProxy(ObjectInstance oi, ProxyHandler server) {    
    objectInstance = oi ;
    this.server = server ;
  }

  public java.lang.String getState()
    throws InstanceNotFoundException, AttributeNotFoundException,
    ReflectionException, MBeanException {

      return ((java.lang.String)server.getAttribute(objectInstance.getObjectName(), "State"));
    }

  public java.lang.Integer getNbChanges()
    throws InstanceNotFoundException, AttributeNotFoundException,
    ReflectionException, MBeanException {

      return ((java.lang.Integer)server.getAttribute(objectInstance.getObjectName(), "NbChanges"));
    }

  public  void setState(java.lang.String value)
    throws InstanceNotFoundException, ReflectionException,
    AttributeNotFoundException,InvalidAttributeValueException,
    MBeanException {

      server.setAttribute(objectInstance.getObjectName(), new Attribute("State",value));
  }

  public void reset()
    throws InstanceNotFoundException, ReflectionException,
    MBeanException {

    Object result;
    result= server.invoke(objectInstance.getObjectName(), "reset", null, null);
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
