package org.basic.net.c20_jmx.jdmk.current.Queries;
/*
 * @(#)file      ClassMB.java
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
import javax.management.MBeanRegistration;
import javax.management.ObjectName;
import javax.management.InstanceAlreadyExistsException;

/**
 * MBean representing a java class. It has the following attributes:
 *      - Name  : the name of the class
 *      - Public: specifies if the class is public or not
 *
 * When registered into the MBean server, an instance of this class also
 * registers all its methods into the MBean Server (as instances of MethodMB).
 */

public class ClassMB implements ClassMBMBean, MBeanRegistration {

    /**
     * Builds this MBean with the given name: 
     * should be the full name of the class this MBean is meant to represent.
     */
    public ClassMB(String name) {
        this.name = name;
    }

    /**
     * Initializes this ClassMB MBean upon registration in the MBean server,
     * and also registers all its methods as MethodMB MBeans in the MBean
     * server.
     *
     * The name of the class this MBean represents is part of the name of
     * the object.
     */
    public ObjectName preRegister(MBeanServer server, ObjectName objName)
        throws Exception {

        try {
            //
            // Initialize this MBean
            //
	    if (objName == null) {
		objName = this.objectName();
	    }
            this.server = server; // Keep a reference to the MBean server

            name = (String) objName.getKeyProperty("name");
            System.out.println("\nRegistering class "+ name +" :");

            Class theClass = Class.forName(name);

            isPublic = Modifier.isPublic(theClass.getModifiers());

	    // Create and register a MethodMB MBean for each method of the
	    // class this MBean represents
            Method[] meths = theClass.getMethods();
            int imeth;
            for (imeth = 0; imeth < meths.length; imeth++) {
                MethodMB mtmb = new MethodMB(meths[imeth]);
                try {
                    System.out.println("\tRegistering Method " +
				       meths[imeth].getName() +
				       " for class " +
				       mtmb.getClassName());
                    server.registerMBean(mtmb, mtmb.objectName());
                } catch (InstanceAlreadyExistsException e) {
                    System.out.println("\tMethod " +
				       meths[imeth].getName() +
				       " is already registered for class " +
				       mtmb.getClassName());
                } catch (Exception e) {
                    System.out.println("\tCould not create " +
				       mtmb.objectName() +
				       " [" + e.getMessage() + "]");
                }
            }
        } catch(Throwable e) {
            e.printStackTrace();
        }
	return objName;
    }

    /**
     * Getter for the "Name" attribute:
     * it is the full name of the class that the ClassMB MBean represents
     *
     * @return the current value of the "Name" attribute.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the "Public" attribute:
     * it tells if the class that the ClassMB MBean represents is public or not.
     *
     * @return the current value of the "Public" attribute.
     */
    public Boolean getPublic() {
        return new Boolean(isPublic);
    }

    /**
     * Returns the domain name to use to register this MBean
     */
    public String domainname() {
        return "Classes.mine";
    }

    /**
     * Returns the object name to use to register this MBean
     */
    public ObjectName objectName() {
	try {
	    System.out.println("class=" + getClass().getName() +
			       ",name=" + getName().toString());
	    return new ObjectName(domainname() + ":class=" +
				  getClass().getName() +
				  ",name=" + getName());
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
    }

    /**
     * Private reference to the MBean server
     */
    MBeanServer server;

    /**
     * Name of the class this MBean is meant to represent
     */
    String name;

    /**
     * Is the class that this MBean represents public ?
     */
    boolean isPublic;

    public void postRegister (Boolean registrationDone) {
    }
    public void preDeregister() throws Exception {
    }
    public void postDeregister() {
    }
}
