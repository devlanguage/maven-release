package org.basic.net.c20_jmx.jdmk.legacy.JdmkProxyMBeans;

//
// Generated by proxygen version 5.1 when compiling RelationSupport (Tue Apr 13 19:32:23 MEST 2004).
//

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanRegistrationException;
import javax.management.ReflectionException;
import javax.management.relation.RoleResult;
import javax.management.relation.Role;
import java.util.Map;
import javax.management.ObjectName;
import java.util.List;
import javax.management.relation.RoleList;


/**
 * The proxy MBean interface of the RelationSupport MBean.
 *
 * @see com.sun.jdmk.tools.ProxyGen 
 */
public interface RelationSupportProxyMBean {

  public java.lang.Boolean isInRelationService()
    throws InstanceNotFoundException, AttributeNotFoundException,
    ReflectionException, MBeanException;

  public javax.management.relation.RoleResult getAllRoles()
    throws InstanceNotFoundException, AttributeNotFoundException,
    ReflectionException, MBeanException;

  public java.util.Map getReferencedMBeans()
    throws InstanceNotFoundException, AttributeNotFoundException,
    ReflectionException, MBeanException;

  public java.lang.String getRelationTypeName()
    throws InstanceNotFoundException, AttributeNotFoundException,
    ReflectionException, MBeanException;

  public javax.management.ObjectName getRelationServiceName()
    throws InstanceNotFoundException, AttributeNotFoundException,
    ReflectionException, MBeanException;

  public java.lang.String getRelationId()
    throws InstanceNotFoundException, AttributeNotFoundException,
    ReflectionException, MBeanException;

  public  void setRelationServiceManagementFlag(java.lang.Boolean value)
    throws InstanceNotFoundException, ReflectionException,
    AttributeNotFoundException,InvalidAttributeValueException,
    MBeanException;

  public  void setRole(javax.management.relation.Role value)
    throws InstanceNotFoundException, ReflectionException,
    AttributeNotFoundException,InvalidAttributeValueException,
    MBeanException;

  public java.util.List getRole(java.lang.String p0)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public javax.management.relation.RoleResult getRoles(String[] p0)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public javax.management.relation.RoleList retrieveAllRoles()
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public java.lang.Integer getRoleCardinality(java.lang.String p0)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public javax.management.relation.RoleResult setRoles(javax.management.relation.RoleList p0)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public void handleMBeanUnregistration(javax.management.ObjectName p0, java.lang.String p1)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;


}
