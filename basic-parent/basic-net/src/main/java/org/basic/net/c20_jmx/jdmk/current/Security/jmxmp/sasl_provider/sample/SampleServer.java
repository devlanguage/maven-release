package org.basic.net.c20_jmx.jdmk.current.Security.jmxmp.sasl_provider.sample;
/*
 * @(#)file      SampleServer.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.1
 * @(#)lastedit  04/02/05
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import java.io.*;
import javax.security.sasl.*;

/**
 * Implements the bogus SAMPLE SASL server mechanism.
 */
public class SampleServer implements SaslServer {

    private String username;
    private boolean completed = false;

    public String getMechanismName() {
        return "SAMPLE";
    }

    public byte[] evaluateResponse(byte[] responseData) throws SaslException {
        if (completed) {
            throw new SaslException("Already completed");
        }
        completed = true;
        username = new String(responseData);
        return null;
    }

    public String getAuthorizationID() {
        return username;
    }

    public boolean isComplete() {
        return completed;
    }

    public byte[] unwrap(byte[] incoming, int offset, int len) 
        throws SaslException {
        throw new SaslException("No negotiated security layer");
    }

    public byte[] wrap(byte[] outgoing, int offset, int len)
        throws SaslException {
        throw new SaslException("No negotiated security layer");
    }

    public Object getNegotiatedProperty(String propName) {
        if (propName.equals(Sasl.QOP)) {
            return "auth";
        }
        return null;
    }

    public void dispose() throws SaslException {
    }
}
