package org.basic.net.c20_jmx.jdmk.current.ModelMBean;
/*
 * @(#)file      ModelAgent.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.10
 * @(#)lastedit  04/05/05
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// Java imports
//
import java.io.IOException;
import java.lang.reflect.Constructor;

// JMX imports
//
import javax.management.Attribute;
import javax.management.Descriptor;
import javax.management.MalformedObjectNameException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.modelmbean.*;

public class ModelAgent {

    /*
     * ------------------------------------------
     *  CONSTRUCTORS
     * ------------------------------------------
     */

    public ModelAgent() {
        // CREATE the MBeanServer
        //
        echo("\n\tCREATE the MBeanServer.");
        server = MBeanServerFactory.createMBeanServer();
    }

    /*
     * ------------------------------------------
     *  PUBLIC METHODS
     * ------------------------------------------
     */

    public static void main(String[] args) {
        // START
        //
        echo(SEP_LINE);
        echo("\n>>> CREATE the agent...");
        ModelAgent agent = new ModelAgent();

        // DO THE DEMO
        //
        agent.doSimpleDemo();

        // END
        //
        echo(SEP_LINE);
        echo("\n>>> END of the ModelMBean example\n");
        echo("\n\tPress <Enter> to stop the agent...\n");
        waitForEnterPressed();
        System.exit(0);
    }

    /*
     * ------------------------------------------
     *  PRIVATE METHODS
     * ------------------------------------------
     */

    private void doSimpleDemo() {
        // Build the simple MBean ObjectName
        //
        ObjectName mbeanObjectName = null;
        String domain = server.getDefaultDomain();
        String mbeanName = "ModelSample";
        try {
            mbeanObjectName = new ObjectName(domain + ":type=" + mbeanName);
        } catch (MalformedObjectNameException e) {
            echo("\t!!! Could not create the MBean ObjectName !!!");
            e.printStackTrace();
            echo("\nEXITING...\n");
            System.exit(1);
        }
        // Create and register the MBean
        //
        echo(SEP_LINE);
        createMBean(mbeanObjectName, mbeanName);
        echo("\nPress <Enter> to continue...\n");
        waitForEnterPressed();

        // Get and display the management information exposed by the MBean
        //
        echo(SEP_LINE);
        echo("\nPrinting Descriptors from MBeanInfo");
        printMBeanInfo(mbeanObjectName, mbeanName);
        echo("\nPress <Enter> to continue...\n");
        waitForEnterPressed();

        // Create an event listener
        //
        echo(SEP_LINE);
        echo("\nCreate event listeners");
        NotificationListener attrListener =
            (NotificationListener) new TestBeanAttributeChangeListener();
        createEventListeners(mbeanObjectName, attrListener);
        echo("\nPress <Enter> to continue...\n");
        waitForEnterPressed();

        // Manage the MBean
        //
        echo(SEP_LINE);
        manageSimpleMBean(mbeanObjectName ,mbeanName);

        // Trying to perform illegal management operations...
        //
        echo(SEP_LINE);
        goTooFar(mbeanObjectName);
        echo("\nPress <Enter> to continue...\n");
        waitForEnterPressed();
    }

    private void createMBean(ObjectName mbeanObjectName, String mbeanName) {
        echo("\n>>> CREATE the " + mbeanName +
             " MBean within the MBeanServer:");
        String mbeanClassName =
            "javax.management.modelmbean.RequiredModelMBean";
        echo("\tOBJECT NAME = " + mbeanObjectName);

        // Set management interface in ModelMBean:
        // attributes, operations, notifications
        //
        buildDynamicMBeanInfo(mbeanObjectName, mbeanName);
        try {
            RequiredModelMBean modelmbean = new RequiredModelMBean(dMBeanInfo);
            // Set the managed resource for the ModelMBean instance
            //
            modelmbean.setManagedResource(new TestBean(), "objectReference");
            // Register the ModelMBean in the MBean Server
            //
            server.registerMBean(modelmbean, mbeanObjectName);
        } catch (Exception e) {
            echo("\t!!! ModelAgent: Could not create the " + mbeanName +
                 " MBean !!!");
            e.printStackTrace();
            echo("\nEXITING...\n");
            System.exit(1);
        }
        echo("\n\tModelMBean has been successfully created.\n");
    }

    private void createEventListeners(ObjectName mbeanObjectName,
                                      NotificationListener listener) {
        try {
            server.invoke(mbeanObjectName,
                          "addAttributeChangeNotificationListener",
                          new Object[] {listener,
                                        "State",
                                        null},
                          new String[] {"javax.management.NotificationListener",
                                        "java.lang.String",
                                        "java.lang.Object"});
            echo("\n\tEvent listener created successfully\n");
        } catch (Exception e) {
            echo("Error! Creating event listener with invoke failed " +
                 "with message:\n");
            echo(e.getMessage() + "\n");
            echo("\nEXITING...\n");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void manageSimpleMBean(ObjectName mbeanObjectName,
                                   String mbeanName) {
        echo("\n>>> MANAGING the " + mbeanName + " MBean ");
        echo("using its attributes and operations exposed for management");
        try {
            // Get attribute values
            //
            sleep(1000);
            echo("\n>> Printing attributes from ModelMBean \n");
            printSimpleAttributes(mbeanObjectName);

            sleep(1000);
            echo("\n>> Printing attributes from instance cache \n");
            printSimpleAttributes(mbeanObjectName);

            // Change State attribute
            //
            sleep(1000);
            echo("\n>>  Setting State attribute to value \"new state\"...");
            Attribute stateAttribute = new Attribute("State", "new state");
            server.setAttribute(mbeanObjectName, stateAttribute);

            // Get attribute values
            //
            sleep(1000);
            printSimpleAttributes(mbeanObjectName);

            echo("\n>> The NbChanges attribute is still \"0\" as its cached " +
                 "value is valid for 5 seconds (currencyTimeLimit=5s)");

            echo("\n>> Wait for 5 seconds and print new attributes values ...");
            echo("\nPress <Enter> to continue...\n");
            waitForEnterPressed();
            sleep(5000);
            printSimpleAttributes(mbeanObjectName);

            // Invoking reset operation
            //
            sleep(1000);
            echo("\n>>  Invoking reset operation...");
            server.invoke(mbeanObjectName, "reset", null, null);

            // Get attribute values
            //
            echo("\n>>  Printing reset attribute values");
            sleep(1000);
            printSimpleAttributes(mbeanObjectName);

            echo("\n>> The State and NbChanges attributes are still " +
                 "\"new state\" and \"1\" as their cached value is " +
                 "valid for 5 seconds (currencyTimeLimit=5s)");

            echo("\n>> Wait for 5 seconds and print new attributes values ...");
            echo("\nPress <Enter> to continue...\n");
            waitForEnterPressed();
            sleep(5000);
            printSimpleAttributes(mbeanObjectName);

            // Getting notifications list
            //
            echo("\n>> Printing Notifications Broadcasted");
            sleep(1000);
            MBeanNotificationInfo[] myNotifys =
                (MBeanNotificationInfo[]) server.invoke(mbeanObjectName,
                                                        "getNotificationInfo",
                                                        null,
                                                        null);
            echo("\n\tSupported notifications are:");
            for (int i=0; i<myNotifys.length; i++) {
                echo("\n\t\t" +
                     ((ModelMBeanNotificationInfo)myNotifys[i]).toString());
            }

            // Accessing and printing Protocol Map for NbChanges
            //
            echo("\n>>  Exercising Protocol Map for NbChanges");
            sleep(1000);
            ModelMBeanInfo myMMBI =
                (ModelMBeanInfo) server.invoke(mbeanObjectName,
                                               "getMBeanInfo",
                                               null,
                                               null);

            Descriptor myDesc = myMMBI.getDescriptor("NbChanges", "attribute");

            echo("\n\tRetrieving specific protocols:");
            Descriptor pm = (Descriptor) myDesc.getFieldValue("protocolMap");
            echo("\tProtocolMap lookup SNMP is " + pm.getFieldValue("SNMP"));
            echo("\tProtocolMap lookup CIM is " + pm.getFieldValue("CIM"));

            echo("\n\tDynamically updating Protocol Map:");
            pm.setField("CIM", "ManagedResource.LongVersion");
            pm.setField("CMIP", "SwitchData");

            echo("\n\tPrinting Protocol Map");
            String[] pmKeys = pm.getFieldNames();
            Object[] pmEntries = pm.getFieldValues(null);
            for (int i=0; i < pmKeys.length; i++) {
                echo("\tProtocol Map Name " + i + ": Name: " + pmKeys[i] +
                     ": Entry:" + ((String) pmEntries[i]).toString());
            }

            echo("\n>>  Testing operation caching");
            echo("\n>>  Invoking getNbResets");
            Integer numResets = (Integer)
                server.invoke(mbeanObjectName, "getNbResets", null, null);
            echo("\n\tReceived " + numResets + " from getNbResets first time");

            echo("\n>>  Invoking second reset operation...");
            server.invoke(mbeanObjectName, "reset", null, null);
            Integer numResets2 = (Integer)
                server.invoke(mbeanObjectName, "getNbResets", null, null);
            echo("\n\tReceived " + numResets2 + " from getNbResets second " +
                 "time (from operation cache)");

            echo("\n>>  Invoking get of attribute ONLY provided through " +
                 "ModelMBeanAttributeInfo (should be 99)...");
            Integer respHardValue = (Integer)
                server.getAttribute(mbeanObjectName, "HardValue");
            echo("\n\tReceived " + respHardValue +
                 " from getAttribute of HardValue");
        } catch (Exception e) {
            echo("\nmanageSimpleMBean failed with " + e.getMessage());
            e.printStackTrace();
            return;
        }
    }

    private void goTooFar(ObjectName mbeanObjectName) {
        // Try to set the NbChanges attribute
        //
        echo("\n>>> Trying to set the NbChanges attribute (read-only)!");
        echo("\n... We should get an AttributeNotFoundException:\n");
        sleep(1000);
        Attribute nbChangesAttribute =
            new Attribute("NbChanges", new Integer(1));
        try {
            server.setAttribute(mbeanObjectName, nbChangesAttribute);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Try to access the NbResets property
        //
        echo("\n\n>>> Trying to access the NbResets property " +
             "(not exposed for management)!");
        echo("\n... We should get an AttributeNotFoundException:\n");
        sleep(1000);
        try {
            Integer NbResets =
                (Integer) server.getAttribute(mbeanObjectName, "NbResets");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    private void printMBeanInfo(ObjectName mbeanObjectName, String mbeanName) {
        echo("\n>>> Getting the management information for the " + mbeanName +
             ":" + mbeanObjectName + " MBean");
        echo("    using the getMBeanInfo method of the MBeanServer");
        sleep(1000);
        ModelMBeanInfo info = null;
        try {
            info = (ModelMBeanInfo) server.getMBeanInfo(mbeanObjectName);
            if (info == null) {
                echo("\nModelMBeanInfo from JMX Agent is null!");
            }
        } catch (Exception e) {
            echo("\t!!! ModelAgent:printMBeanInfo: Could not get MBeanInfo " +
                 "object for " + mbeanName + " exception type " +
                 e.getClass().toString() + ":" + e.getMessage() + "!!!");
            e.printStackTrace();
            return;
        }
        echo("\nCLASSNAME: \t" + info.getClassName());
        echo("\nDESCRIPTION: \t" + info.getDescription());
        try {
            echo("\nMBEANDESCRIPTOR: \t" +
                 (info.getMBeanDescriptor()).toString());
        } catch (Exception e) {
            echo("\nMBEANDESCRIPTOR: \tNone");
        }

        echo("\nATTRIBUTES");
        MBeanAttributeInfo[] attrInfo = (info.getAttributes());
        if (attrInfo.length>0) {
            for (int i=0; i<attrInfo.length; i++) {
                echo("\n ** NAME: \t" + attrInfo[i].getName());
                echo("    DESCR: \t" + attrInfo[i].getDescription());
                echo("    TYPE: \t" + attrInfo[i].getType() +
                     "\tREAD: " + attrInfo[i].isReadable() +
                     "\tWRITE: " + attrInfo[i].isWritable());
                ModelMBeanAttributeInfo mmai =
                    (ModelMBeanAttributeInfo) attrInfo[i];
                echo("    DESCRIPTOR: \t" + mmai.getDescriptor().toString());
            }
        } else echo(" ** No attributes **");

        MBeanConstructorInfo[] constrInfo = info.getConstructors();
        echo("\nCONSTRUCTORS");
        if (constrInfo.length > 0) {
            for (int i=0; i<constrInfo.length; i++) {
                echo("\n ** NAME: \t" + constrInfo[i].getName());
                echo("    DESCR: \t" + constrInfo[i].getDescription());
                echo("    PARAM: \t" + constrInfo[i].getSignature().length +
                     " parameter(s)");
                ModelMBeanConstructorInfo mmci =
                    (ModelMBeanConstructorInfo) constrInfo[i];
                echo("    DESCRIPTOR: \t" + mmci.getDescriptor().toString());
            }
        } else echo(" ** No Constructors **");

        echo("\nOPERATIONS");
        MBeanOperationInfo[] opInfo = info.getOperations();
        if (opInfo.length>0) {
            for (int i=0; i<opInfo.length; i++) {
                echo("\n ** NAME: \t" + opInfo[i].getName());
                echo("    DESCR: \t" + opInfo[i].getDescription());
                echo("    PARAM: \t" + opInfo[i].getSignature().length +
                     " parameter(s)");
                ModelMBeanOperationInfo mmoi =
                    (ModelMBeanOperationInfo) opInfo[i];
                echo("    DESCRIPTOR: \t" + mmoi.getDescriptor().toString());
            }
        } else echo(" ** No operations ** ");

        echo("\nNOTIFICATIONS");
        MBeanNotificationInfo[] notifInfo = info.getNotifications();
        if (notifInfo.length>0) {
            for (int i=0; i<notifInfo.length; i++) {
                echo("\n ** NAME: \t" + notifInfo[i].getName());
                echo("    DESCR: \t" + notifInfo[i].getDescription());
                String notifTypes[] = notifInfo[i].getNotifTypes();
                for (int j = 0; j < notifTypes.length; j++) {
                    echo("    TYPE: \t" + notifTypes[j]);
                }
                ModelMBeanNotificationInfo mmni =
                    (ModelMBeanNotificationInfo) notifInfo[i];
                echo("    DESCRIPTOR: \t" + mmni.getDescriptor().toString());
            }
        } else echo(" ** No notifications **");

        echo("\nEnd of MBeanInfo print");
    }

    private void printSimpleAttributes(ObjectName mbeanObjectName) {
        try {
            echo("\n\tGetting attribute values:");
            String State =
                (String) server.getAttribute(mbeanObjectName, "State");
            Integer NbChanges =
                (Integer) server.getAttribute(mbeanObjectName, "NbChanges");
            echo("\n\t\tState     = \"" + State + "\"");
            echo("\t\tNbChanges = \"" + NbChanges.toString() + "\"");
        } catch (Exception e) {
            echo("\tModelAgent:printSimpleAttributes: !!! Could not " +
                 "read attributes !!!");
            e.printStackTrace();
            return;
        }
    }

    private static void echo(String msg) {
        System.out.println(msg);
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            return;
        }
    }

    private static void waitForEnterPressed() {
        try {
            boolean done = false;
            while (!done) {
                char ch = (char) System.in.read();
                if (ch < 0 || ch == '\n') {
                    done = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDynamicMBeanInfo(ObjectName inMBeanObjectName,
                                      String inMBeanName) {
        try {
            Class appBean = Class.forName(dClassName);
            dMBeanInfo = new ModelMBeanInfoSupport(dClassName,
                                                   dDescription,
                                                   dAttributes,
                                                   dConstructors,
                                                   dOperations,
                                                   dNotifications,
                                                   mmbDesc);
        } catch (Exception e) {
            echo("\nException in loadDynamicMBeanInfo : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Build the private dMBeanInfo field, which represents the management
     * interface exposed by the MBean; that is, the set of attributes,
     * constructors, operations and notifications which are available
     * for management.
     *
     * A reference to the dMBeanInfo object is returned by the getMBeanInfo()
     * method of the DynamicMBean interface. Note that, once constructed, an
     * MBeanInfo object is immutable.
     */
    private void buildDynamicMBeanInfo(ObjectName inMBeanObjectName,
                                       String inMBeanName) {
        try {
            Class appBean = Class.forName(dClassName);
            mmbDesc = new DescriptorSupport(
                          new String[] { "name=" + inMBeanObjectName,
                                         "descriptorType=mbean",
                                         "displayName=" + inMBeanName,
                                         "log=T",
                                         "logfile=jmxmain.log",
                                         "currencyTimeLimit=5"});

            Descriptor stateDesc = new DescriptorSupport();
            stateDesc.setField("name", "State");
            stateDesc.setField("descriptorType", "attribute");
            stateDesc.setField("displayName", "MyState");
            stateDesc.setField("getMethod", "getState");
            stateDesc.setField("setMethod", "setState");
            stateDesc.setField("currencyTimeLimit", "20");

            dAttributes[0] = new ModelMBeanAttributeInfo("State",
                                                         "java.lang.String",
                                                         "State: state string.",
                                                         true,
                                                         true,
                                                         false,
                                                         stateDesc);

            Descriptor nbChangesDesc = new DescriptorSupport();
            nbChangesDesc.setField("name", "NbChanges");
            nbChangesDesc.setField("descriptorType", "attribute");
            nbChangesDesc.setField("default", "0");
            nbChangesDesc.setField("displayName", "MyChangesCount");
            nbChangesDesc.setField("getMethod", "getNbChanges");
            nbChangesDesc.setField("setMethod", "setNbChanges");
            Descriptor nbChangesMap = new DescriptorSupport(new String[] {
                "SNMP=1.3.6.9.12.15.18.21.0",
                "CIM=ManagedResource.Version"});
            nbChangesDesc.setField("protocolMap", nbChangesMap);

            dAttributes[1] = new ModelMBeanAttributeInfo(
                    "NbChanges",
                    "java.lang.Integer",
                    "NbChanges: number of times the State string has been set",
                    true,
                    false,
                    false,
                    nbChangesDesc);

            Descriptor hardValueDesc = new DescriptorSupport();
            hardValueDesc.setField("name", "HardValue");
            hardValueDesc.setField("descriptorType", "attribute");
            hardValueDesc.setField("value", new Integer("99"));
            hardValueDesc.setField("displayName", "HardCodedValue");
            hardValueDesc.setField("currencyTimeLimit", "0");
	    /* A currencyTimeLimit of 0 means that the value
	       cached in the Descriptor is always valid.  So
	       when we call getAttribute on this attribute we
	       will read this value of 99 out of the Descriptor. */
	    
            dAttributes[2] = new ModelMBeanAttributeInfo(
                "HardValue",
                "java.lang.Integer",
                "HardValue: static value in ModelMBeanInfo and not in TestBean",
                true,
                false,
                false,
                hardValueDesc);

            Constructor[] constructors = appBean.getConstructors();

            Descriptor testBeanDesc = new DescriptorSupport();
            testBeanDesc.setField("name", "TestBean");
            testBeanDesc.setField("descriptorType", "operation");
            testBeanDesc.setField("role", "constructor");

            dConstructors[0] = new ModelMBeanConstructorInfo(
                                        "TestBean(): Constructs a TestBean App",
                                        constructors[0],
                                        testBeanDesc);

            MBeanParameterInfo[] params = null;

            Descriptor resetDesc = new DescriptorSupport();
            resetDesc.setField("name", "reset");
            resetDesc.setField("descriptorType", "operation");
            resetDesc.setField("class", "TestBean");
            resetDesc.setField("role", "operation");

            dOperations[0] = new ModelMBeanOperationInfo(
                                      "reset",
                                      "reset(): reset State and NbChanges",
                                      params,
                                      "void",
                                      MBeanOperationInfo.ACTION,
                                      resetDesc);

            Descriptor getNbResetsDesc = new DescriptorSupport(new String[] {
                "name=getNbResets",
                "class=TestBeanFriend",
                "descriptorType=operation",
                "role=operation"});

            TestBeanFriend tbf = new TestBeanFriend();
            getNbResetsDesc.setField("targetObject", tbf);
            getNbResetsDesc.setField("targetType", "objectReference");

            dOperations[1] = new ModelMBeanOperationInfo(
                                 "getNbResets",
                                 "getNbResets(): get number of resets done",
                                 params,
                                 "java.lang.Integer",
                                 MBeanOperationInfo.INFO,
                                 getNbResetsDesc);

            Descriptor getStateDesc = new DescriptorSupport(new String[] {
                "name=getState",
                "descriptorType=operation",
                "class=TestBean",
                "role=getter"} );

            dOperations[2] = new ModelMBeanOperationInfo(
                                      "getState",
                                      "get state attribute",
                                      params,
                                      "java.lang.String",
                                      MBeanOperationInfo.ACTION,
                                      getStateDesc);

            Descriptor setStateDesc = new DescriptorSupport(new String[] {
                "name=setState",
                "descriptorType=operation",
                "class=TestBean",
                "role=setter"});

            MBeanParameterInfo[] setStateParms = new MBeanParameterInfo[] {
                new MBeanParameterInfo("newState",
                                       "java.lang.String",
                                       "new State value") };

            dOperations[3] = new ModelMBeanOperationInfo(
                                      "setState",
                                      "set State attribute",
                                      setStateParms,
                                      "void",
                                      MBeanOperationInfo.ACTION,
                                      setStateDesc);

            Descriptor getNbChangesDesc = new DescriptorSupport(new String[] {
                "name=getNbChanges",
                "descriptorType=operation",
                "class=TestBean",
                "role=getter"});
        
            dOperations[4] = new ModelMBeanOperationInfo(
                                      "getNbChanges",
                                      "get NbChanges attribute",
                                      params,
                                      "java.lang.Integer",
                                      MBeanOperationInfo.INFO,
                                      getNbChangesDesc);

            Descriptor setNbChangesDesc = new DescriptorSupport(new String[] {
                "name=setNbChanges",
                "descriptorType=operation",
                "class=TestBean",
                "role=setter"});
        
            MBeanParameterInfo[] setNbChangesParms = new MBeanParameterInfo[] {
                new MBeanParameterInfo("newNbChanges",
                                       "java.lang.Integer",
                                       "new value for Number of Changes") };

            dOperations[5] = new ModelMBeanOperationInfo(
                                      "setNbChanges",
                                      "set NbChanges attribute",
                                      setNbChangesParms,
                                      "void",
                                      MBeanOperationInfo.ACTION,
                                      setNbChangesDesc);

            Descriptor addAttributeChangeNotificationListenerDesc
		= new DescriptorSupport(new String[] {
		    "name=addAttributeChangeNotificationListener",
		    "descriptorType=operation",
		    "class=javax.management.modelmbean.RequiredModelMBean",
		    "role=operation"});

            MBeanParameterInfo[] addAttributeChangeNotificationListenerParms
		= new MBeanParameterInfo[] {
		    new MBeanParameterInfo("inlistener",
					   "javax.management.NotificationListener",
					   "A listener"),
		    new MBeanParameterInfo("inAttributeName,",
					   "java.lang.String",
					   "attribute name"),
		    new MBeanParameterInfo("inhandback),",
					   "java.lang.Object",
					   "handback")};

            dOperations[6] = new ModelMBeanOperationInfo(
                                      "addAttributeChangeNotificationListener",
                                      "add a listener to receive AttributeChangeNotification",
                                      addAttributeChangeNotificationListenerParms,
                                      "void",
                                      MBeanOperationInfo.ACTION,
                                      addAttributeChangeNotificationListenerDesc);


            Descriptor getNotificationInfoDesc
		= new DescriptorSupport(new String[] {
		    "name=getNotificationInfo",
		    "descriptorType=operation",
		    "class=javax.management.modelmbean.RequiredModelMBean",
		    "role=operation"});

            dOperations[7] = new ModelMBeanOperationInfo(
                                      "getNotificationInfo",
                                      "get Notification Info",
                                      null,
                                      "[Ljavax.management.MBeanNotificationInfo",
                                      MBeanOperationInfo.ACTION,
                                      getNotificationInfoDesc);

            Descriptor getMBeanInfoDesc
		= new DescriptorSupport(new String[] {
		    "name=getMBeanInfo",
		    "descriptorType=operation",
		    "class=javax.management.modelmbean.RequiredModelMBean",
		    "role=operation"});

            dOperations[8] = new ModelMBeanOperationInfo(
                                      "getMBeanInfo",
                                      "get MBean Info",
                                      null,
                                      "javax.management.MBeanInfo",
                                      MBeanOperationInfo.ACTION,
                                      getMBeanInfoDesc);


            Descriptor genEventDesc = new DescriptorSupport(new String[] {
                "descriptorType=notification",
                "name=jmx.ModelMBean.General",
                "severity=4",
                "MessageId=MA001",
                "log=T",
                "logfile=jmx.log"});
            String[] genTypes = new String[1];
            genTypes[0] = "jmx.ModelMBean.General";

            dNotifications[0] = new ModelMBeanNotificationInfo(
                                    genTypes,
                                    "jmx.ModelMBean.General", // was Generic
                                    "Generic Event",
                                    genEventDesc); // test event

            dMBeanInfo = new ModelMBeanInfoSupport(dClassName,
                                                   dDescription,
                                                   dAttributes,
                                                   dConstructors,
                                                   dOperations,
                                                   dNotifications);

            dMBeanInfo.setMBeanDescriptor(mmbDesc);
        } catch (Exception e) {
            echo("\nException in buildDynamicMBeanInfo : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void printLocalMBeanInfo(MBeanInfo info) {
        echo("\nCLASSNAME: \t" + info.getClassName());
        echo("\nDESCRIPTION: \t" + info.getDescription());

        echo("\nATTRIBUTES");
        MBeanAttributeInfo[] attrInfo = info.getAttributes();
        if (attrInfo.length>0) {
            for (int i=0; i<attrInfo.length; i++) {
                echo(" ** NAME: \t" + attrInfo[i].getName());
                echo("    DESCR: \t" + attrInfo[i].getDescription());
                echo("    TYPE: \t" + attrInfo[i].getType() +
                     "\tREAD: " + attrInfo[i].isReadable() +
                     "\tWRITE: " + attrInfo[i].isWritable());
                ModelMBeanAttributeInfo mmai =
                    (ModelMBeanAttributeInfo) attrInfo[i];
                echo("    DESCRIPTOR: \t" + mmai.getDescriptor().toString());
            }
        } else echo(" ** No attributes **");

        echo("\nCONSTRUCTORS");
        MBeanConstructorInfo[] constrInfo = info.getConstructors();
        for (int i=0; i<constrInfo.length; i++) {
            echo(" ** NAME: \t" + constrInfo[i].getName());
            echo("    DESCR: \t" + constrInfo[i].getDescription());
            echo("    PARAM: \t" + constrInfo[i].getSignature().length +
                 " parameter(s)");
            ModelMBeanConstructorInfo mmci =
                (ModelMBeanConstructorInfo) constrInfo[i];
            echo("    DESCRIPTOR: \t" + mmci.getDescriptor().toString());
        }

        echo("\nOPERATIONS");
        MBeanOperationInfo[] opInfo = info.getOperations();
        if (opInfo.length>0) {
            for (int i=0; i<opInfo.length; i++) {
                echo(" ** NAME: \t" + opInfo[i].getName());
                echo("    DESCR: \t" + opInfo[i].getDescription());
                echo("    PARAM: \t" + opInfo[i].getSignature().length +
                     " parameter(s)");
                ModelMBeanOperationInfo mmoi =
                    (ModelMBeanOperationInfo) opInfo[i];
                echo("    DESCRIPTOR: \t" + mmoi.getDescriptor().toString());
            }
        } else echo(" ** No operations ** ");

        echo("\nNOTIFICATIONS");
        MBeanNotificationInfo[] notifInfo = info.getNotifications();
        if (notifInfo.length>0) {
            for (int i=0; i<notifInfo.length; i++) {
                echo(" ** NAME: \t" + notifInfo[i].getName());
                echo("    DESCR: \t" + notifInfo[i].getDescription());
                String notifTypes[] = notifInfo[i].getNotifTypes();
                for (int j = 0; j < notifTypes.length; j++) {
                    echo("    TYPE: \t" + notifTypes[j]);
                }
                ModelMBeanNotificationInfo mmni =
                    (ModelMBeanNotificationInfo) notifInfo[i];
                echo("    DESCRIPTOR: \t" + mmni.getDescriptor().toString());
            }
        } else echo(" ** No notifications **");
    }

    public void printLocalDescriptors(MBeanInfo mbi) throws MBeanException {
        echo(mbi.getDescription() + "Descriptors:\n");

        echo("Attribute Descriptors:\n");
        Descriptor[] dArray;
        dArray = ((ModelMBeanInfo) mbi).getDescriptors("attribute");
        for (int i = 0; i < dArray.length; i++) {
            echo("\n*Attribute****************************");
            String[] afields = ((Descriptor) dArray[i]).getFields();
            for (int j=0; j < afields.length; j++) {
                echo(afields[j] + "\n");
            }
        }

        echo("Operation Descriptors:\n");
        dArray = ((ModelMBeanInfo) mbi).getDescriptors("operation");
        for (int i=0; i < dArray.length; i++) {
            echo("\n*Operation****************************");
            String[] ofields = ((Descriptor) dArray[i]).getFields();
            for (int j=0; j < ofields.length; j++) {
                echo(ofields[j] + "\n");
            }
        }

        echo("Notification Descriptors:\n");
        dArray = ((ModelMBeanInfo) mbi).getDescriptors("notification");
        for (int i=0; i < dArray.length; i++) {
            echo("\n*Notification****************************");
            String[] nfields = ((Descriptor) dArray[i]).getFields();
            for (int j=0; j < nfields.length; j++) {
                echo(nfields[j] + "\n");
            }
        }
    }

    public void printModelMBeanDescriptors(ObjectName mbeanObjectName) {
        sleep(1000);
        Descriptor[] dArray = new DescriptorSupport[0];
        try {
            dArray = (Descriptor[]) server.invoke(mbeanObjectName,
                                                  "getDescriptors",
                                                  new Object[] {},
                                                  new String[] {});
            if (dArray == null) {
                echo("\nDescriptor list is null!");
            }
        } catch (Exception e) {
            echo("\t!!! Could not get descriptors for mbeanName ");
            e.printStackTrace();
            return;
        }

        echo("Descriptors: (");
        echo(dArray.length + ")\n");
        for (int i=0; i < dArray.length; i++) {
            echo("\n*Descriptor***********************");
            String[] dlfields =  ((Descriptor) dArray[i]).getFields();
            for (int j=0; j < dlfields.length; j++) {
                echo(dlfields[j] + "\n");
            }
        }
    }

    /*
     * ------------------------------------------
     *  PRIVATE VARIABLES
     * ------------------------------------------
     */

    private MBeanServer server = null;
    private static final String SEP_LINE =
        "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
        "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";

    private String dClassName = "TestBean";
    private String dDescription =
        "Simple implementation of a test app Java Bean.";

    private ModelMBeanAttributeInfo[] dAttributes =
        new ModelMBeanAttributeInfo[3];
    private ModelMBeanConstructorInfo[] dConstructors =
        new ModelMBeanConstructorInfo[1];
    private ModelMBeanOperationInfo[] dOperations =
        new ModelMBeanOperationInfo[9];
    private ModelMBeanNotificationInfo[] dNotifications =
        new ModelMBeanNotificationInfo[1];
    private Descriptor mmbDesc = null;
    private ModelMBeanInfo dMBeanInfo = null;
}
