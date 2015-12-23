package org.basic.net.c20_jmx.mbean.hello;

/**
 * Simple definition of a dynamic MBean, named "SimpleDynamic".
 *
 * The "SimpleDynamic" dynamic MBean shows how to expose for management
 * attributes and operations, at runtime,  by implementing the  
 * "javax.management.DynamicMBean" interface.
 *
 * This MBean exposes for management two attributes and one operation:
 *      - the read/write "State" attribute,
 *      - the read only "NbChanges" attribute,
 *      - the "reset()" operation.
 * It does so by putting this information in an MBeanInfo object that
 * is returned by the getMBeanInfo() method of the DynamicMBean interface.
 *
 * It implements the access to its attributes through the getAttribute(),
 * getAttributes(), setAttribute(), and setAttributes() methods of the
 * DynamicMBean interface.
 *
 * It implements the invocation of its reset() operation through the
 * invoke() method of the DynamicMBean interface.
 * 
 * Note that as "SimpleDynamic" explicitly defines one constructor,
 * this constructor must be public and exposed for management through
 * the MBeanInfo object.
 */

import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.management.Attribute;
import javax.management.AttributeChangeNotification;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanException;
import javax.management.MBeanFeatureInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.NotificationBroadcasterSupport;
import javax.management.ReflectionException;
import javax.management.RuntimeOperationsException;

import org.basic.net.c20_jmx.mbean.BaseMBean;

class DynamicMBeanDescriptor {
    Object data;
    Object name;
    MBeanFeatureInfo mbeanInfo;

    DynamicMBeanDescriptor(Object name, MBeanFeatureInfo info, Object data) {
        this.name = name;
        this.data = data;
        this.mbeanInfo = info;

    }
}

public class HelloDynamicMBean extends NotificationBroadcasterSupport implements DynamicMBean, BaseMBean {

    /*
     * ----------------------------------------------------- CONSTRUCTORS
     * -----------------------------------------------------
     */

    public HelloDynamicMBean() {
        // Build the management information to be exposed by the dynamic MBean
        //
        buildDynamicMBeanInfo();
    }

    /*
     * ----------------------------------------------------- IMPLEMENTATION OF THE DynamicMBean INTERFACE
     * -----------------------------------------------------
     */
    /**
     * Allows the value of the specified attribute of the Dynamic MBean to be obtained.
     */
    public Object getAttribute(String attribute_name) throws AttributeNotFoundException, MBeanException, ReflectionException {

        // Check attribute_name is not null to avoid NullPointerException
        // later on
        //
        if (attribute_name == null) {
            throw new RuntimeOperationsException(new IllegalArgumentException("Attribute name cannot be null"),
                    "Cannot invoke a getter of " + dClassName + " with null attribute name");
        }
        // Check for a recognized attribute_name and call the corresponding
        // getter
        //
        Object attrValue = attrNameValueMap.get(attribute_name);
        if (null != attrValue) {
            return attrValue;
        }
        // if (attribute_name.equals("State")) {
        // return getState();
        // }
        // if (attribute_name.equals("NbChanges")) {
        // return getNbChanges();
        // }
        // if (attribute_name.equals("Age")) {
        // return 23434;
        // }
        // if (attribute_name.equals("Salary")) {
        // return 12.4;
        // }
        // If attribute_name has not been recognized throw an
        // AttributeNotFoundException
        //
        throw new AttributeNotFoundException("Cannot find " + attribute_name + " attribute in " + dClassName);
    }

    /**
     * Sets the value of the specified attribute of the Dynamic MBean.
     */
    public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException,
            ReflectionException {

        // Check attribute is not null to avoid NullPointerException later on
        //
        if (attribute == null) {
            throw new RuntimeOperationsException(new IllegalArgumentException("Attribute cannot be null"), "Cannot invoke a setter of "
                    + dClassName + " with null attribute");
        }
        String name = attribute.getName();
        Object value = attribute.getValue();
        if (name == null) {
            throw new RuntimeOperationsException(new IllegalArgumentException("Attribute name cannot be null"),
                    "Cannot invoke the setter of " + dClassName + " with null attribute name");
        }
        // Check for a recognized attribute name and call the corresponding
        // setter
        //
        if (null != name) {
            attrNameValueMap.put(name, value);
        } else {
            throw new AttributeNotFoundException("Attribute " + name + " not found in " + this.getClass().getName());

        }
        // if (name.equals("State")) {
        // // if null value, try and see if the setter returns any exception
        // if (value == null) {
        // try {
        // setState(null);
        // } catch (Exception e) {
        // throw new InvalidAttributeValueException("Cannot set attribute " + name + " to null");
        // }
        // }
        // // if non null value, make sure it is assignable to the attribute
        // else {
        // try {
        // if (Class.forName("java.lang.String").isAssignableFrom(value.getClass())) {
        // setState((String) value);
        // } else {
        // throw new InvalidAttributeValueException("Cannot set attribute " + name + " to a " +
        // value.getClass().getName()
        // + " object, String expected");
        // }
        // } catch (ClassNotFoundException e) {
        // e.printStackTrace();
        // }
        // }
        // }
        // // recognize an attempt to set "NbChanges" attribute (read-only):
        // else if (name.equals("NbChanges")) {
        // throw new AttributeNotFoundException("Cannot set attribute " + name + " because it is read-only");
        // }
        // // unrecognized attribute name:
        // else {
        // throw new AttributeNotFoundException("Attribute " + name + " not found in " + this.getClass().getName());
        // }
    }

    /**
     * Enables the to get the values of several attributes of the Dynamic MBean.
     */
    public AttributeList getAttributes(String[] attributeNames) {

        // Check attributeNames is not null to avoid NullPointerException
        // later on
        //
        if (attributeNames == null) {
            throw new RuntimeOperationsException(new IllegalArgumentException("attributeNames[] cannot be null"),
                    "Cannot invoke a getter of " + dClassName);
        }
        AttributeList resultList = new AttributeList();

        // If attributeNames is empty, return an empty result list
        //
        if (attributeNames.length == 0)
            return resultList;

        // Build the result attribute list
        //
        for (int i = 0; i < attributeNames.length; i++) {
            try {
                Object value = getAttribute((String) attributeNames[i]);
                resultList.add(new Attribute(attributeNames[i], value));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }

    /**
     * Sets the values of several attributes of the Dynamic MBean, and returns the list of attributes that have been
     * set.
     */
    public AttributeList setAttributes(AttributeList attributes) {

        // Check attributes is not null to avoid NullPointerException later on
        //
        if (attributes == null) {
            throw new RuntimeOperationsException(new IllegalArgumentException("AttributeList attributes cannot be null"),
                    "Cannot invoke a setter of " + dClassName);
        }
        AttributeList resultList = new AttributeList();

        // If attributeNames is empty, nothing more to do
        //
        if (attributes.isEmpty())
            return resultList;

        // For each attribute, try to set it and add to the result list if
        // successfull
        //
        for (Iterator i = attributes.iterator(); i.hasNext();) {
            Attribute attr = (Attribute) i.next();
            try {
                setAttribute(attr);
                String name = attr.getName();
                Object value = getAttribute(name);
                resultList.add(new Attribute(name, value));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }

    /**
     * Allows an operation to be invoked on the Dynamic MBean.
     */
    public Object invoke(String operationName, Object params[], String signature[]) throws MBeanException, ReflectionException {

        // Check operationName is not null to avoid NullPointerException
        // later on
        //
        if (operationName == null) {
            throw new RuntimeOperationsException(new IllegalArgumentException("Operation name cannot be null"),
                    "Cannot invoke a null operation in " + dClassName);
        }
        // Check for a recognized operation name and call the corresponding
        // operation
        //
        if (operationName.equals("resetd")) {
            reset();
            return "operation " + operationName + "(" + signature + ")";
        } else if (operationName.equals("addAttribute")) {
            MBeanAttributeInfo newMBeanAttr = new MBeanAttributeInfo((String) params[0], (String) params[1], "", true, true, false);
            dAttributes.put((String) params[0], newMBeanAttr);
            attrNameValueMap.put(params[0], params[2]);
            rebuildDynamicMBeanInfo();
            return "operation " + operationName + "(" + signature + ")";
        } else {
            // Unrecognized operation name
            //
            throw new ReflectionException(new NoSuchMethodException(operationName), "Cannot find the operation " + operationName + " in "
                    + dClassName);
        }
    }

    /**
     * This method provides the exposed attributes and operations of the Dynamic MBean. It provides this information
     * using an MBeanInfo object.
     */
    public MBeanInfo getMBeanInfo() {

        // Return the information we want to expose for management:
        // the dMBeanInfo private field has been built at instanciation time
        //
        return dMBeanInfo;
    }

    /*
     * ----------------------------------------------------- OTHER PUBLIC METHODS
     * -----------------------------------------------------
     */

    /**
     * Getter: get the "State" attribute of the "SimpleDynamic" dynamic MBean.
     */
    public String getState() {
        return state;
    }

    /**
     * Setter: set the "State" attribute of the "SimpleDynamic" dynamic MBean.
     */
    public void setState(String s) {
        state = s;
        nbChanges++;
    }

    /**
     * Getter: get the "NbChanges" attribute of the "SimpleDynamic" dynamic MBean.
     */
    public Integer getNbChanges() {
        return new Integer(nbChanges);
    }

    /**
     * Operation: reset to their initial values the "State" and "NbChanges" attributes of the "SimpleDynamic" dynamic
     * MBean.
     */
    public void reset() {
        AttributeChangeNotification acn = new AttributeChangeNotification(this, 0, 0, "NbChanges reset", "NbChanges", "Integer",
                new Integer(nbChanges), new Integer(0));
        state = "initial state";
        nbChanges = 0;
        nbResets++;
        sendNotification(acn);
    }

    /**
     * Return the "NbResets" property. This method is not a Getter in the JMX sense because it is not returned by the
     * getMBeanInfo() method.
     */
    public Integer getNbResets() {
        return new Integer(nbResets);
    }

    /*
     * ----------------------------------------------------- PRIVATE METHODS
     * -----------------------------------------------------
     */

    /**
     * Build the private dMBeanInfo field, which represents the management interface exposed by the MBean, that is, the
     * set of attributes, constructors, operations and notifications which are available for management.
     * 
     * A reference to the dMBeanInfo object is returned by the getMBeanInfo() method of the DynamicMBean interface. Note
     * that, once constructed, an MBeanInfo object is immutable.
     */
    private void buildDynamicMBeanInfo() {

        dAttributes.put(getAttributeName("State"), new MBeanAttributeInfo("State", "java.lang.String", "State string.", true, true, false));
        dAttributes.put(getAttributeName("NbChanges"), new MBeanAttributeInfo("NbChanges", "java.lang.Integer", "Number of times the "
                + "State string has been changed.", true, false, false));

        attrNameValueMap.put("State", "ACTIVE");
        attrNameValueMap.put("NbChanges", "1");

        Constructor[] constructors = this.getClass().getConstructors();
        dConstructors.put(getConstructName("MBeanConstructorInfo"), new MBeanConstructorInfo("Constructs a " + "SimpleDynamic object",
                constructors[0]));

        MBeanParameterInfo[] params = null;
        dOperations.put(getOpeartionName("reset"), new MBeanOperationInfo("reset", "reset State and NbChanges "
                + "attributes to their initial values", params, "void", MBeanOperationInfo.ACTION));
        dOperations.put(getOpeartionName("addAttribute"), new MBeanOperationInfo("addAttribute", "addd one attribute for MBean",
                new MBeanParameterInfo[] {//
                new MBeanParameterInfo("name", "java.lang.String", ""),//
                        new MBeanParameterInfo("type", "java.lang.String", ""),//
                        new MBeanParameterInfo("defaultValue", "java.lang.String", "") },//
                "boolean", MBeanOperationInfo.ACTION));

        dNotifications.put(getNotificationName("AttributeChangeNotification"), new MBeanNotificationInfo(
                new String[] { AttributeChangeNotification.ATTRIBUTE_CHANGE }, AttributeChangeNotification.class.getName(),
                "This notification is emitted when the reset() method is called."));

        dMBeanInfo = new MBeanInfo(dClassName, dDescription,//
                dAttributes.values().toArray(new MBeanAttributeInfo[] {}), dConstructors.values().toArray(new MBeanConstructorInfo[] {}),//
                dOperations.values().toArray(new MBeanOperationInfo[] {}), dNotifications.values().toArray(new MBeanNotificationInfo[] {}));
    }

    /**
     * @param string
     * @return
     */
    private String getAttributeName(String string) {
        return "100_ATTRIBUTE_" + string;
    }

    private String getOpeartionName(String string) {
        return "101_OPERATION_" + string;
    }

    private String getConstructName(String string) {
        return "102_CONSTRUCT_" + string;
    }

    private String getNotificationName(String string) {
        return "103_NOTIFICATION_" + string;
    }

    private void rebuildDynamicMBeanInfo() {
        dMBeanInfo = new MBeanInfo(dClassName, dDescription,//
                dAttributes.values().toArray(new MBeanAttributeInfo[] {}), dConstructors.values().toArray(new MBeanConstructorInfo[] {}),//
                dOperations.values().toArray(new MBeanOperationInfo[] {}), dNotifications.values().toArray(new MBeanNotificationInfo[] {}));
    }

    /*
     * ----------------------------------------------------- PRIVATE VARIABLES
     * -----------------------------------------------------
     */

    private String state = "initial state";
    private int nbChanges = 0;
    private int nbResets = 0;

    private String dClassName = this.getClass().getName();
    private String dDescription = "Simple implementation of a dynamic MBean.";

    private Map<Object, Object> attrNameValueMap = new ConcurrentHashMap<Object, Object>();
    private Map<Object, MBeanAttributeInfo> dAttributes = new ConcurrentHashMap<Object, MBeanAttributeInfo>();
    private Map<Object, MBeanConstructorInfo> dConstructors = new ConcurrentHashMap<Object, MBeanConstructorInfo>();
    private Map<Object, MBeanNotificationInfo> dNotifications = new ConcurrentHashMap<Object, MBeanNotificationInfo>();
    private Map<Object, MBeanOperationInfo> dOperations = new ConcurrentHashMap<Object, MBeanOperationInfo>();
    private MBeanInfo dMBeanInfo = null;

    /*
     * (non-Javadoc)
     * 
     * @see org.basic.jmx.mbean.BaseMBean#getMBanName()
     */
    @Override
    public String getMBeanName() {
        return this.getClass().getName();
    }
}
