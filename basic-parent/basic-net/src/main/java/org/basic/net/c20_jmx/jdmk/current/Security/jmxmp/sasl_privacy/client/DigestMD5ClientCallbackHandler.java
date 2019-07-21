package org.basic.net.c20_jmx.jdmk.current.Security.jmxmp.sasl_privacy.client;
/*
 * @(#)file      DigestMD5ClientCallbackHandler.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.2
 * @(#)lastedit  04/05/10
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import java.io.*;
import javax.security.auth.callback.*;
import javax.security.sasl.*;

public class DigestMD5ClientCallbackHandler implements CallbackHandler {

    public DigestMD5ClientCallbackHandler(String user, String password) {
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
	    } else if (callbacks[i] instanceof RealmCallback) {
		RealmCallback rcb = (RealmCallback) callbacks[i];
		rcb.setText(rcb.getDefaultText());
	    } else if (callbacks[i] instanceof RealmChoiceCallback) {
		RealmChoiceCallback rccb = (RealmChoiceCallback) callbacks[i];
		rccb.setSelectedIndex(rccb.getDefaultChoice());
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
