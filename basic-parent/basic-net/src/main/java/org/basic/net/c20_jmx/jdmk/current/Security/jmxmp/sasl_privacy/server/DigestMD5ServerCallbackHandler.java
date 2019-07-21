package org.basic.net.c20_jmx.jdmk.current.Security.jmxmp.sasl_privacy.server;
/*
 * @(#)file      DigestMD5ServerCallbackHandler.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.2
 * @(#)lastedit  04/05/10
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import java.util.Map;
import java.util.Properties;
import java.io.*;
import javax.security.auth.callback.*;
import javax.security.sasl.*;

public final class DigestMD5ServerCallbackHandler implements CallbackHandler {
    private Properties pwDb, namesDb, proxyDb;

    /**
     * Contents of files are in the Properties file format.
     *
     * @param pwFile name of file containing name/password pairs
     * @param namesFile name of file containing name to canonicalized name
     * @param proxyFile name of file containing authname to list of authzids
     */
    public DigestMD5ServerCallbackHandler(String pwFile,
                                          String namesFile,
                                          String proxyFile) throws IOException {
        pwDb = new Properties();
        pwDb.load(new FileInputStream(pwFile));

        if (namesFile != null) {
            namesDb = new Properties();
            namesDb.load(new FileInputStream(namesFile));
        }
            
        if (proxyDb != null) {
            proxyDb = new Properties();
            proxyDb.load(new FileInputStream(proxyFile));
        }
    }

    public void handle(Callback[] callbacks)
        throws UnsupportedCallbackException {
        NameCallback ncb = null;
        PasswordCallback pcb = null;
        AuthorizeCallback acb = null;
        RealmCallback rcb = null;

        for (int i = 0; i < callbacks.length; i++) {
            if (callbacks[i] instanceof NameCallback) {
                ncb = (NameCallback) callbacks[i];
            } else if (callbacks[i] instanceof PasswordCallback) {
                pcb = (PasswordCallback) callbacks[i];
            } else if (callbacks[i] instanceof AuthorizeCallback) {
                acb = (AuthorizeCallback) callbacks[i];
            } else if (callbacks[i] instanceof RealmCallback) {
                rcb = (RealmCallback) callbacks[i];
            } else {
                throw new UnsupportedCallbackException(callbacks[i]);
            }
        }

        // Process retrieval of password; can get password iff
        // username is available in NameCallback
        if (pcb != null && ncb != null) {
            String username = ncb.getDefaultName();
            String pw = pwDb.getProperty(username);
            if (pw != null) {
                char[] pwchars = pw.toCharArray();
                pcb.setPassword(pwchars);
                // Clear pw
                for (int i = 0; i <pwchars.length; i++) {
                    pwchars[i] = 0;
                }
                // Set canonicalized username if any
                String canonAuthid =
                    (namesDb != null? namesDb.getProperty(username) : null);
                if (canonAuthid != null) {
                    ncb.setName(canonAuthid);
                }
            }
        }

        // Check for authorization
        if (acb != null) {
            String authid = acb.getAuthenticationID();
            String authzid = acb.getAuthorizationID();
            if (authid.equals(authzid)) {
                // Self is always authorized
                acb.setAuthorized(true);            
            } else {
                // Check db for allowed authzids
                String authzes = (proxyDb != null ? proxyDb.getProperty(authid)
                    : null);
                if (authzes != null && authzes.indexOf(authzid) >= 0) {
                    acb.setAuthorized(true);
                }
            }
                
            if (acb.isAuthorized()) {
                // Set canonicalized name
                String canonAuthzid = (namesDb != null ?
                    namesDb.getProperty(authzid) : null);
                if (canonAuthzid != null) {
                    acb.setAuthorizedID(canonAuthzid);
                }
            }
        }
    }
}
