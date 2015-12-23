package org.basic.net.c20_jmx.jdmk.legacy.Notification;
/*
 * @(#)file      NotificationEmitterMBean.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.4
 * @(#)lastedit  04/01/19
 *
 * Copyright 2004 Sun Microsystems, Inc.  All rights reserved. Use is subject to license terms.
 */

public interface NotificationEmitterMBean {

	/**
	 * Send a Notification object with the specified times.
	 * The sequence number will be from zero to times-1.
	 *
	 * @param nb The number of notifications to send
	 */
	public void sendNotifications(Integer nb);
}
