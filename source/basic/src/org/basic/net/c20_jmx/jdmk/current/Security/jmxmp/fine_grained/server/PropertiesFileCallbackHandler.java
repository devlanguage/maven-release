package org.basic.net.c20_jmx.jdmk.current.Security.jmxmp.fine_grained.server;
/*
 * @(#)file      PropertiesFileCallbackHandler.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.1
 * @(#)lastedit  04/02/05
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.sasl.AuthorizeCallback;
import javax.security.sasl.SaslException;
import com.sun.jdmk.security.sasl.AuthenticateCallback;

public final class PropertiesFileCallbackHandler implements CallbackHandler {

    /**
     * Contents of files are in the Properties file format.
     *
     * @param pwFile name of file containing name/password pairs
     */
    public PropertiesFileCallbackHandler(String pwFile) throws IOException {
	pwDb = new Properties();
	pwDb.load(new FileInputStream(pwFile));
    }

    public void handle(Callback[] callbacks)
	throws IOException, UnsupportedCallbackException {
        // Retrieve callbacks
        //
        for (int i = 0; i < callbacks.length; i++) {
            if (callbacks[i] instanceof AuthenticateCallback) {
		AuthenticateCallback authnCb =
		    (AuthenticateCallback) callbacks[i];
                String authenticationID = authnCb.getAuthenticationID();
                char[] passwd = authnCb.getPassword();
		String password = new String(passwd);
		// Clear char array password
		//
		for (int j = 0; j < passwd.length; j++)
		    passwd[j] = ' ';
		passwd = null;
		// Set authentication flag to false
		//
		authnCb.setAuthenticated(false);
		// Verification of username and password
		//
		if (authenticationID != null && password != null) {
		    // Process retrieval of password; can get password iff
		    // authenticationID is available in AuthenticateCallback
		    //
		    String pw = pwDb.getProperty(authenticationID);
		    if (pw == null || !pw.equals(password)) {
			throw new SaslException("Authentication failed! " +
						"Invalid username/password!");
		    }
		    // Set authentication flag to true
		    //
		    authnCb.setAuthenticated(true);
		} else {
		    throw new SaslException("Authentication failed! " +
					    "Username or password is null!");
		}
	    } else if (callbacks[i] instanceof AuthorizeCallback) {
		AuthorizeCallback authzCb = (AuthorizeCallback) callbacks[i];
		authzCb.setAuthorizedID(authzCb.getAuthorizationID());
		authzCb.setAuthorized(true);
	    } else {
                throw new UnsupportedCallbackException(callbacks[i]);
            }
	}
    }

    private Properties pwDb;
}
