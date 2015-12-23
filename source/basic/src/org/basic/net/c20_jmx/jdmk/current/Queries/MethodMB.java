package org.basic.net.c20_jmx.jdmk.current.Queries;
/*
 * @(#)file      MethodMB.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.5
 * @(#)lastedit  04/02/02
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// Java imports
//
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

// JMX imports
//
import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * MBean representing a java class method.
 *
 * The MethodMB MBean has the following attributes:
 *      - Name      : the name of the method that this MethodMB MBean represents
 *      - ClassName : the name of the class containing the method
 *      - Static    : tells if the method is static or not
 *      - Public    : tells if the method is public or not
 *      - NbParams  : the number of parameters of the method
 *      - Params    : an '_' separated string listing the method parameter types
 */

public class MethodMB implements MethodMBMBean {

    /**
     * Constructs this MethodMB MBean
     */
    public MethodMB(Method m) {
        method = m;
        paramClasses = m.getParameterTypes();
    }

    /**
     * Getter for the "Name" attribute:
     * the name of the method that this MethodMB MBean represents.
     *
     * @return the current value of the "Name" attribute.
     */
    public String getName() {
        return method.getName();
    }

    /**
     * Getter for the "Params" attribute:
     * an '_' separated string listing the method parameter types.
     *
     * @return the current value of the "Params" attribute.
     */
    public String getParams() {
        String params = "";
        if (paramClasses.length == 0)
            params = "NONE";
        else
            for (int i = 0; i < paramClasses.length; i++) {
                if (i > 0)
                    params += "_";
                params += paramClasses[i].getName();
            }
        return params;
    }

    /**
     * Getter for the "NbParams" attribute:
     * the number of parameters of the method.
     *
     * @return the current value of the "NbParams" attribute.
     */
    public Integer getNbParams() {
        return new Integer(paramClasses.length);
    }

    /**
     * Getter for the "ClassName" attribute: 
     * the name of the class containing the method.
     *
     * @return the current value of the "ClassName" attribute.
     */
    public String getClassName() {
        return method.getDeclaringClass().getName();
    }

    /**
     * Getter for the "Public" attribute:
     * tells if the method is public or not.
     *
     * @return the current value of the "Public" attribute.
     */
    public Boolean getPublic() {
        return new Boolean(Modifier.isPublic(method.getModifiers()));
    }

    /**
     * Getter for the "Static" attribute.:
     * tells if the method is static or not.
     *
     * @return the current value of the "Static" attribute.
     */
    public Boolean getStatic() {
        return new Boolean(Modifier.isStatic(method.getModifiers()));
    }


    /**
     * Returns the domain name to use to register this MBean
     */
    public String domainname() {
        return "Methods.myMethods";
    }

    /**
     * Returns the object name to use to register this MBean
     */
    public ObjectName objectName() {   
	try {
        return new ObjectName(domainname()+":" +
                              "class="+getClassName()+
                              ",method="+getName()+
                              ",params="+getParams());
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
    }

    /**
     * The Java method that this MBean represents
     */
    private Method method;

    /**
     * Classes corresponding to the parameters of
     * the method that this MBean represents
     */
    private Class[] paramClasses;
}
