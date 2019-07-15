package org.basic.net.c20_jmx.jdmk.current.Monitor;
/*
 * @(#)file      AgentListener.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   4.6
 * @(#)lastedit  04/02/02
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// Java imports
//
import java.util.Vector;

// JMX imports
//
import javax.management.*;
import javax.management.monitor.*;

public class AgentListener implements NotificationListener {

    /*
     * ------------------------------------------
     *  CONSTRUCTORS
     * ------------------------------------------
     */
    
    public AgentListener() {
    }

    /*
     * ------------------------------------------
     *  PUBLIC METHODS
     * ------------------------------------------
     */
    
    public void handleNotification(Notification notification, Object handback) {

        MonitorNotification notif = (MonitorNotification) notification;

        // Get a handle on the CounterMonitor which emitted the notification.
        //
        Monitor monitor = (Monitor) notif.getSource();
	echo("\n\t>> Got notification from source = " + monitor);

        // Process the different types of notifications fired by the
	// CounterMonitor.
        //
        String type = notif.getType();
	echo("\t>> Notification type = " + type);

        try {
            if (type.equals(MonitorNotification.OBSERVED_OBJECT_ERROR)) {
                echo("\t>> " +
		     notif.getObservedObject().getClass().getName() +
		     " is not registered in the server");
                echo("\t>> Stopping the CounterMonitor...\n");
                monitor.stop();
            } else if (type.equals(
			   MonitorNotification.OBSERVED_ATTRIBUTE_ERROR)) {
		echo("\t>> " +
		     notif.getObservedAttribute() +
		     " attribute is not contained in " +
		     notif.getObservedObject().getClass().getName());
		echo("\t>> Stopping the CounterMonitor...\n");
		monitor.stop();
            } else if (type.equals(
			   MonitorNotification.OBSERVED_ATTRIBUTE_TYPE_ERROR)) {
                echo("\t>> The type of " +
		     notif.getObservedAttribute() +
		     " attribute is not correct");
                echo("\t>> Stopping the CounterMonitor...\n");
                monitor.stop();
            } else if (type.equals(
			    MonitorNotification.THRESHOLD_ERROR)) {
                echo("\t>> Threshold type is not <Integer>");
                echo("\t>> Stopping the CounterMonitor...\n");
                monitor.stop();
            } else if (type.equals(MonitorNotification.RUNTIME_ERROR)) {
                echo("\t>> Runtime error (?)");
                echo("\t>> Stopping the CounterMonitor...\n");
                monitor.stop();
            } else if (type.equals(
			   MonitorNotification.THRESHOLD_VALUE_EXCEEDED)) {
		echo("\t>> Observed Object = " + notif.getObservedObject());
                echo("\t>> Observed Attribute '" + notif.getObservedAttribute()+
		     " has reached the threshold " + notif.getTrigger() + "\n");
            } else {
                echo("\t>> Unknown event type (?)\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void echo(String msg) {
        System.out.println(msg);
    }
}
