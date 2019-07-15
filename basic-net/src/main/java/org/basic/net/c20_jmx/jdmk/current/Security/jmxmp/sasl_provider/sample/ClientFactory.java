package org.basic.net.c20_jmx.jdmk.current.Security.jmxmp.sasl_provider.sample;
/*
 * @(#)file      ClientFactory.java
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
 * Sample SASL client factory. Used with SampleClient.java.
 */
public class ClientFactory implements SaslClientFactory {

    /**
     * Simple-minded implementation that ignores props
     */
    public SaslClient createSaslClient(String[] mechs,
				       String authorizationId,
				       String protocol,
				       String serverName,
				       Map props,
				       CallbackHandler cbh)
	throws SaslException {
	for (int i = 0; i < mechs.length; i++) {
	    if (mechs[i].equals("SAMPLE")) {
		return new SampleClient(authorizationId);
	    }
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
