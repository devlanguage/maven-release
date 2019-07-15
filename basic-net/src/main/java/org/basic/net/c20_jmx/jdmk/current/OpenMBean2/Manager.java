package org.basic.net.c20_jmx.jdmk.current.OpenMBean2;
/*
 * @(#)file      Manager.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.5
 * @(#)lastedit  04/02/20
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// Java imports
//
import java.io.*;
import java.util.*;

// JMX imports
//
import javax.management.*;
import javax.management.openmbean.*;
import javax.management.remote.*;

public class Manager {

    private static MBeanServerConnection mbsc = null;

    public static void main(String[] args) {
        // START
        //
        try {
            // Create a JMXMP connector client and
            // connect it to the JMXMP connector server
            //
            echo("\nCreate a JMXMP connector client and " +
		 "connect it to the JMXMP connector server");
            JMXServiceURL url = new JMXServiceURL("jmxmp", null, 5555);
	    JMXConnector conn = JMXConnectorFactory.connect(url, null);
	    mbsc = conn.getMBeanServerConnection();
            echo("done");

	    // PERFORM THE EXAMPLE:
            //
            echo("\n> Run manager actions:");
            if (!runExample()) {
                echo("*** Example completed with unexpected behavior!");
                conn.close(); // DISCONNECTION
                System.exit(1);
            }
            echo("\n> End of manager actions");

            // END
            //
            echo("\n> Close the connection to the server");
	    conn.close(); // DISCONNECTION
            echo("done");
            
	    echo("*** Example completed with expected behavior!");
            System.exit(0);
        } catch (Exception e) {
            echo("Error: an exception has been raised in the main method");
	    echo("*** Example completed with unexpected behavior!");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Launches the example's scenario.
     */
    private static boolean runExample() {
        try {
            // ----------------
            // build the MBean ObjectName instance
            //
            String mbeanName = "CarOpenMBean";
            ObjectName mbeanObjectName = null;
            String domain = "Example";

	    // MBEAN OBJECT NAME
            mbeanObjectName = new ObjectName(domain + ":type=" + mbeanName);

            // ----------------
            // create and register an MBean in the MBeanServer of the agent
            //
            String mbeanClassName = mbeanName;

            echo("\t>> Registration of a CarOpenMBean in the MBean Server: ");
	    // CREATE MBEAN
            ObjectInstance mbeanObjectInstance =
		mbsc.createMBean(mbeanClassName, mbeanObjectName);
            echo ("done");
            echo("\t>> Information concerning the CarOpenMBean: ");
            echo("\t\tMBean Class name      = " + mbeanClassName);
            echo("\t\tMBean OBJECT NAME     = " + mbeanObjectName);
            echo("\n\t\tCurrent MBean count in the agent = " +
		 mbsc.getMBeanCount());
            echo("\n");
            echo("You can now use your web browser, pointing to the MBean " +
		 "server, to add/modify data before continuing with the " +
		 "example.");
	    echo("For instance, you can add a car, then add a new available " +
		 "color for the given car.");
	    echo("These informations are displayed when return is pressed\n");
	    echo("\nPress <Enter> to continue...");
            waitForEnterPressed();

            // ----------------
            // get and display the management information exposed by the MBean
            // then add cars and invoke an operation exposed for management.
            //
            if (!test(mbeanObjectInstance)) {
                mbsc.unregisterMBean(mbeanObjectInstance.getObjectName()); 
                return false;
            }

            echo("\t>> Unregistering the " + mbeanName + " MBean: ");
	    // UNREGISTER MBEAN
            mbsc.unregisterMBean(mbeanObjectInstance.getObjectName());
            echo("done");
            return true;
        } catch (Exception e) {
            echo("Error: an exception has been raised in runExample method");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Print all attributes using the getAttribute method,
     * then add cars and invoke an operation exposed for management.
     */
    private static boolean test(ObjectInstance mbeanObjectInstance) {

        // get all attributes using the getAttribute method
        //
        try {  
            echo("\n\t>> Display attributes values \n");

            // get nbSoldCars attribute and check return value
            echo("\t\t Get nbSoldCars attribute: ");
            Object att = (Object)
		mbsc.getAttribute(mbeanObjectInstance.getObjectName(),
				  "nbSoldCars");
            echo("\"nbSoldCars\"= " + att.toString());

            // get nbAvailableCars attribute and check return value
            echo("\t\t Get nbAvailableCars attribute: ");
            att = (Object)
		mbsc.getAttribute(mbeanObjectInstance.getObjectName(),
				  "nbAvailableCars");
            echo("\"nbAvailableCars\"= " + att.toString());

            // get isStoreOpen attribute and check return value
            echo("\t\t Get isStoreOpen attribute: ");
            att = (Object)
		mbsc.getAttribute(mbeanObjectInstance.getObjectName(),
				  "isStoreOpen");
            echo("\"isStoreOpen\"= " + att.toString());

            // get setOfCars attribute and check return value
            echo("\t\t Get setOfCars attribute. The list of " +
		 "available cars is: \n");
            att = (Object)
		mbsc.getAttribute(mbeanObjectInstance.getObjectName(),
				  "setOfCars");
            TabularData setOfCars = (TabularData) att;
            Iterator carIterator; // iterator on set of cars
            for (carIterator = setOfCars.values().iterator();
		 carIterator.hasNext(); ) { // we iterate on all cars
		// for each car we check that the information is correct
                Object elem = carIterator.next();
		// get the car
                CompositeData myCar = (CompositeData) elem;
		// get an iterator on CompositeData car
                Iterator it = myCar.values().iterator();
		// get the listOfColors item for this car
                TabularData listOfAvailableColors = (TabularData) it.next();
		// get the maker item for this car
                String carMaker = (String) it.next();
		// get the model item for this car
                String carModel = (String) it.next();
		// get the price item for this car
                Float  carPrice = (Float) it.next();

                echo("\t\t\tMaker: " + carMaker);
                echo("\t\t\tModel: " + carModel);
                echo("\t\t\tPrice: " + carPrice + " euros");
                echo("\t\t\tAvailable colors in rgb format: (");
                // retrieve the list of available colors in RGB format
                for (Iterator colorIterator =
			 listOfAvailableColors.values().iterator();
		     colorIterator.hasNext(); ) {
		    //get the color
                    CompositeData color = (CompositeData) colorIterator.next();
                    it = color.values().iterator();
                    Integer blueColor = (Integer) it.next();
                    Integer greenColor = (Integer) it.next();
                    Integer redColor = (Integer) it.next();
                    echo("(" + redColor.toString() + "," +
			 greenColor.toString() + "," +
			 blueColor.toString() + ")");
                }
                echo(")\n");
            }
        } catch (Exception e) {
            echo("Error: an exception has been raised in runExample method");
            e.printStackTrace();
            return false;
        }

        //  we add a car using addCar operation
        //
        echo("\n\t>>invoke addCar operation \n");
        echo("\t\t add following car: \"Renault\", \"Safrane\"," +
	     "(234,208,123), 25000.0 Euros");
        echo("\nPress <Enter> to continue...");
        waitForEnterPressed();
        Object[] param = new Object[6];
        String[] signature2 = {
	    "java.lang.String",
	    "java.lang.String",
	    "java.lang.Integer",
	    "java.lang.Integer",
	    "java.lang.Integer",
	    "java.lang.Float"};
        param[0] = (Object) "Renault";
        param[1] = (Object) "safrane";
        param[2] = (Object) new Integer(234);
        param[3] = (Object) new Integer(208);
        param[4] = (Object) new Integer(123);
        param[5] = (Object) new Float(25000.0);
        try {
            mbsc.invoke(mbeanObjectInstance.getObjectName(),
			"addCar", param, signature2);
        } catch (Exception e) {
            echo("\t!!! Could not invoke the operation !!!");
            e.printStackTrace();
        }

        // again
        //
        echo("\t\t add following car: \"Peugeot\", \"206\"," +
	     "(110,67,145), 16000.0 Euros");
        echo("\nPress <Enter> to continue...");
        waitForEnterPressed();
        param[0] = (Object) "Peugeot";
        param[1] = (Object) "206";
        param[2] = (Object) new Integer(110);
        param[3] = (Object) new Integer(67);
        param[4] = (Object) new Integer(145);
        param[5] = (Object) new Float(16000.0);
        try {
            mbsc.invoke(mbeanObjectInstance.getObjectName(),
			"addCar", param, signature2);
        } catch (Exception e) {
            echo("\t!!! Could not invoke the operation !!!");
            e.printStackTrace();
        }

        echo("\n\t>>Now we redisplay the setOfCars attribute to check " +
	     "that car has been correctly added\n");
        echo("\nPress <Enter> to continue...");
        waitForEnterPressed();

        // now we redisplay the information
        //
        try {
            echo("\t\t Get setOfCars attribute. The list of " +
		 "available cars is: \n");
            Object att = (Object)
		mbsc.getAttribute(mbeanObjectInstance.getObjectName(),
				  "setOfCars");
            TabularData setOfCars = (TabularData) att;
            Iterator carIterator; // iterator on set of cars
            for (carIterator = setOfCars.values().iterator();
		 carIterator.hasNext(); ) { // we iterate on all cars
		// for each car we check that the information is correct
                Object elem = carIterator.next();
		// get the car
                CompositeData myCar = (CompositeData) elem;
		// get an iterator on CompositeData car
                Iterator it = myCar.values().iterator();
		// get the listOfColors item for this car
                TabularData listOfAvailableColors = (TabularData) it.next();
		// get the maker item for this car 
                String carMaker = (String) it.next();
		// get the model item for this car
                String carModel= (String) it.next();
		// get the price item for this car
		Float  carPrice=(Float) it.next();

                echo("\t\t\tMaker: " + carMaker);
                echo("\t\t\tModel: " + carModel);
                echo("\t\t\tPrice: " + carPrice + " euros");
                echo("\t\t\tAvailable colors in rgb format: (");
                // retrieve the list of available colors in RGB format
                for (Iterator colorIterator =
			 listOfAvailableColors.values().iterator();
		     colorIterator.hasNext(); ) {
		    //get the color
                    CompositeData color = (CompositeData) colorIterator.next();
                    it = color.values().iterator();
                    Integer blueColor = (Integer) it.next();
                    Integer greenColor = (Integer) it.next();
                    Integer redColor = (Integer) it.next();
                    echo("(" + redColor.toString() + "," +
			 greenColor.toString() + "," +
			 blueColor.toString() + ")");
                }
                echo(")\n");
            }
        } catch (Exception e) {
            echo ("Error: an exception has been raised in runExample method");
            e.printStackTrace();
            return false;
        }

        // now we invoke carCheaperThan operation
        //
        echo("\n\t>> Finally we invoke carCheaperThan operation to get " +
	     "all Cars Cheaper than 20000 euros\n");
        echo("\nPress <Enter> to continue...");
        waitForEnterPressed();
        // prepare param and signature to invoke the operation
        param = new Object[1];
        String[] signature = {"java.lang.Float"};
        param[0] = new Float((float) 20000);

        try {
            TabularData setOfCars = (TabularData)
		mbsc.invoke(mbeanObjectInstance.getObjectName(),
			    "carCheaperThan", param, signature);
            // display the setOfCars
            echo("\t\t The list of cars whose price is cheaper than " +
		 "20000 euros is: \n");
            Iterator carIterator; // iterator on set of cars
            for (carIterator = setOfCars.values().iterator();
		 carIterator.hasNext(); ) { // we iterate on all cars
		// for each car we check that the information is correct
                Object elem = carIterator.next();
		// get the car
                CompositeData myCar = (CompositeData) elem;
		// get an iterator on CompositeData car
                Iterator it = myCar.values().iterator();
		// get the listOfColors item for this car
                TabularData listOfAvailableColors = (TabularData) it.next();
		// get the maker item for this car 
                String carMaker = (String) it.next();
		// get the model item for this car
                String carModel= (String) it.next();
		// get the price item for this car
		Float  carPrice=(Float) it.next();

                echo("\t\t\tMaker: " + carMaker);
                echo("\t\t\tModel: " + carModel);
                echo("\t\t\tPrice: " + carPrice + " euros");
                echo("\t\t\tAvailable colors in rgb format: (");
                // retrieve the list of available colors in RGB format
                for (Iterator colorIterator =
			 listOfAvailableColors.values().iterator();
		     colorIterator.hasNext(); ) {
		    //get the color
                    CompositeData color = (CompositeData) colorIterator.next();
                    it = color.values().iterator();
                    Integer blueColor = (Integer) it.next();
                    Integer greenColor = (Integer) it.next();
                    Integer redColor = (Integer) it.next();
                    echo("(" + redColor.toString() + "," +
			 greenColor.toString() + "," +
			 blueColor.toString() + ")");
                }
                echo(")\n");
            }
        } catch (Exception e) {
            echo("\t!!! Could not invoke the operation !!!");
            e.printStackTrace();
        }
        return true;
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

    private static void echo(String msg) {
        System.out.println(msg);
    }
}
