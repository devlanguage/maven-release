package org.basic.security.jaas.login;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.basic.security.jaas.secret.CountFilesAction;
/**
 * <pre>
 * java -classpath %APUSIC_HOME%/lib/apusic.jar;./build;./build/actions 
 * -Djava.security.manager
 * -Djava.security.policy==policy.jaas
 * -Djava.security.auth.login.config==login.conf
 * -Dapusic.home=%APUSIC_HOME%
 * </pre>
 */
public class SimpleLogin {

    public static void main(String[] args) {

        System.setProperty("java.security.manager","");
        System.setProperty("java.security.auth.login.config","org/basic/jvm/jaas/login.conf");
        javax.security.auth.login.LoginContext loginContext = null;
        try {

            loginContext = new LoginContext("simple", new SimpleCallbackHandle());
        } catch (LoginException e) {

            System.out.println(e.getMessage());
        }

        try {
            loginContext.login();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        
        CountFilesAction<Integer> action = new CountFilesAction<>();
        Subject.doAs(loginContext.getSubject(), action);

    }

}
