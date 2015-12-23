package org.basic.net.c20_jmx.jdmk.current.Security.jmxmp.simple.client;
/*
 * @(#)file      UserPasswordCallbackHandler.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.1
 * @(#)lastedit  04/02/05
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import java.io.*;
import javax.security.auth.callback.*;

public class UserPasswordCallbackHandler implements CallbackHandler {

    public UserPasswordCallbackHandler(String user, String password) {
	this.user = user;
	this.pwchars = password.toCharArray();
    }

    public void handle(Callback[] callbacks)
	throws IOException, UnsupportedCallbackException {
	for (int i = 0; i < callbacks.length; i++) {
	    if (callbacks[i] instanceof NameCallback) {
		NameCallback ncb = (NameCallback) callbacks[i];
		ncb.setName(user);
	    } else if (callbacks[i] instanceof PasswordCallback) {
		PasswordCallback pcb = (PasswordCallback) callbacks[i];
		pcb.setPassword(pwchars);
	    } else {
		throw new UnsupportedCallbackException(callbacks[i]);
	    }
	}
    }

    private void clearPassword() {
        if (pwchars != null) {
            for (int i = 0 ; i < pwchars.length ; i++)
                pwchars[i] = 0;
            pwchars = null;
        }
    }

    protected void finalize() {
        clearPassword();
    }

    private String user;
    private char[] pwchars;
}
