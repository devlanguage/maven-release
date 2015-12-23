package org.basic.net.c20_jmx.jdmk.current.Security.jmxmp.sasl_provider.sample;
/*
 * @(#)file      ServerFactory.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.1
 * @(#)lastedit  04/02/05
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import java.util.Map;
import javax.security.auth.callback.*;
import javax.security.sasl.*;

/**
 * Sample SASL server factory. Used with SampleServer.java.
 */
public class ServerFactory implements SaslServerFactory {

    /**
     * Simple-minded implementation that ignores props
     */
    public SaslServer createSaslServer(String mechs,
				       String protocol,
				       String serverName,
				       Map props,
				       CallbackHandler cbh)
	throws SaslException {
	if (mechs.equals("SAMPLE")) {
	    return new SampleServer();
	}
	return null;
    }

    /**
     * Simple-minded implementation that ignores props
     */
    public String[] getMechanismNames(Map props) {
	return new String[]{"SAMPLE"};
    }
}
