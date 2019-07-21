package org.basic.security.jaas.login;

import java.io.IOException;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

public class SimpleLoginModule implements LoginModule {

    private String userName;
    private char[] password;

    private Subject subject;
    private CallbackHandler callbackHandler;
    private SimplePrincipal principal;
    private boolean isAuthenticated;

    private Map sharedState;
    private Map options;
    private String debug;

    public boolean abort() throws LoginException {
        System.out.println("abort()");
        return false;
    }

    public boolean commit() throws LoginException {
        System.out.println("commit");
        if (this.isAuthenticated) {
            this.subject.getPrincipals().add(this.principal);
        } else {
            throw new LoginException("Authentication failure");
        }

        return this.isAuthenticated;
    }

    public void initialize(Subject subject, CallbackHandler callbackHandler, Map sharedState, Map options) {

        this.subject = subject;
        this.callbackHandler = callbackHandler;
        this.sharedState = sharedState;
        this.options = options;

        debug = (String) options.get("debug");
    }

    public boolean login() throws LoginException {

        Callback[] callbacks = new Callback[2];
        callbacks[0] = new NameCallback("User[test]> ");
        callbacks[1] = new PasswordCallback("Password[test]> ", false);

        try {

            callbackHandler.handle(callbacks);
            userName = ((NameCallback) callbacks[0]).getName();
            password = ((PasswordCallback) callbacks[1]).getPassword();

            if (debug.equals("true")) {
                System.out.println("userName:" + userName);
                System.out.println("Password:" + new String(password));
            }

            if (userName.equals("test") && new String(password).equals("test")) {
                System.out.println("user/password passed");
                this.principal = new SimplePrincipal(userName);
                isAuthenticated = true;
                return true;
            } else {
                System.out.println("failed");
                userName = null;
                password = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new LoginException("invalid user password");
        } catch (UnsupportedCallbackException e) {
            e.printStackTrace();
            throw new LoginException("login failuer");

        }

        return this.isAuthenticated;
    }

    public boolean logout() throws LoginException {
        this.subject.getPrincipals().remove(this.principal);

        this.principal = null;
        System.out.println("logout");
        return true;
    }

}
