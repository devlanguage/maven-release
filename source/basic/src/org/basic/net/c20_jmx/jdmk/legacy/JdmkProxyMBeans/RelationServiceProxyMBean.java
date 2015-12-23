package org.basic.net.c20_jmx.jdmk.legacy.JdmkProxyMBeans;

//
// Generated by proxygen version 5.1 when compiling RelationService (Tue Apr 13 19:31:31 MEST 2004).
//

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanRegistrationException;
import javax.management.ReflectionException;
import java.util.List;
import javax.management.relation.RoleInfo;
import javax.management.relation.RelationType;
import javax.management.relation.RoleList;
import javax.management.ObjectName;
import javax.management.relation.Role;
import java.util.Map;
import javax.management.relation.RoleResult;


/**
 * The proxy MBean interface of the RelationService MBean.
 *
 * @see com.sun.jdmk.tools.ProxyGen 
 */
public interface RelationServiceProxyMBean {

  public boolean getPurgeFlag()
    throws InstanceNotFoundException, AttributeNotFoundException,
    ReflectionException, MBeanException;

  public java.util.List getAllRelationTypeNames()
    throws InstanceNotFoundException, AttributeNotFoundException,
    ReflectionException, MBeanException;

  public java.util.List getAllRelationIds()
    throws InstanceNotFoundException, AttributeNotFoundException,
    ReflectionException, MBeanException;

  public  void setPurgeFlag(boolean value)
    throws InstanceNotFoundException, ReflectionException,
    AttributeNotFoundException,InvalidAttributeValueException,
    MBeanException;

  public void createRelationType(java.lang.String p0, RoleInfo[] p1)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public void addRelationType(javax.management.relation.RelationType p0)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public java.util.List getRoleInfos(java.lang.String p0)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public javax.management.relation.RoleInfo getRoleInfo(java.lang.String p0, java.lang.String p1)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public void removeRelationType(java.lang.String p0)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public void createRelation(java.lang.String p0, java.lang.String p1, javax.management.relation.RoleList p2)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public void addRelation(javax.management.ObjectName p0)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public javax.management.ObjectName isRelationMBean(java.lang.String p0)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public java.lang.String isRelation(javax.management.ObjectName p0)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public java.lang.Boolean hasRelation(java.lang.String p0)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public java.lang.Integer checkRoleReading(java.lang.String p0, java.lang.String p1)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public java.lang.Integer checkRoleWriting(javax.management.relation.Role p0, java.lang.String p1, java.lang.Boolean p2)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public void sendRelationCreationNotification(java.lang.String p0)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public void sendRoleUpdateNotification(java.lang.String p0, javax.management.relation.Role p1, java.util.List p2)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public void sendRelationRemovalNotification(java.lang.String p0, java.util.List p1)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public void updateRoleMap(java.lang.String p0, javax.management.relation.Role p1, java.util.List p2)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public void removeRelation(java.lang.String p0)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public void purgeRelations()
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public java.util.Map findReferencingRelations(javax.management.ObjectName p0, java.lang.String p1, java.lang.String p2)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public java.util.Map findAssociatedMBeans(javax.management.ObjectName p0, java.lang.String p1, java.lang.String p2)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public java.util.List findRelationsOfType(java.lang.String p0)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public java.util.List getRole(java.lang.String p0, java.lang.String p1)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public javax.management.relation.RoleResult getRoles(java.lang.String p0, String[] p1)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public javax.management.relation.RoleResult getAllRoles(java.lang.String p0)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public java.lang.Integer getRoleCardinality(java.lang.String p0, java.lang.String p1)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public void setRole(java.lang.String p0, javax.management.relation.Role p1)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public javax.management.relation.RoleResult setRoles(java.lang.String p0, javax.management.relation.RoleList p1)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public java.util.Map getReferencedMBeans(java.lang.String p0)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;

  public java.lang.String getRelationTypeName(java.lang.String p0)
    throws InstanceNotFoundException, ReflectionException,
    MBeanException;


}
