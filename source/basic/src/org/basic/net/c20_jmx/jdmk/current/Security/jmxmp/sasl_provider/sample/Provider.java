package org.basic.net.c20_jmx.jdmk.current.Security.jmxmp.sasl_provider.sample;
/*
 * @(#)file      Provider.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.1
 * @(#)lastedit  04/02/05
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

public final class Provider extends java.security.Provider {
    public Provider() {
	super("SampleSasl", 1.0, "SAMPLE SASL MECHANISM PROVIDER");
	put("SaslClientFactory.SAMPLE", "ClientFactory");
	put("SaslServerFactory.SAMPLE", "ServerFactory");
    }
}
