package org.basic.net.c20_jmx.jdmk.current.Security.jmxmp.sasl_provider.sample;
/*
 * @(#)file      SampleClient.java
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
 * Implements the bogus SAMPLE SASL client mechanism.
 */
public class SampleClient implements SaslClient {

    private byte[] username;
    private boolean completed = false;

    public SampleClient(String authorizationID) throws SaslException {
        if (authorizationID != null) {
            try {
                username = ((String)authorizationID).getBytes("UTF8");
            } catch (UnsupportedEncodingException e) {
                throw new SaslException("Cannot convert " + authorizationID +
                                        " into UTF-8", e);
            }
        } else {
            username = new byte[0];
        }
    }

    public String getMechanismName() {
        return "SAMPLE";
    }

    public boolean hasInitialResponse() {
        return true;
    }

    public byte[] evaluateChallenge(byte[] challengeData) throws SaslException {
        if (completed) {
            throw new SaslException("Already completed");
        }
        completed = true;
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
