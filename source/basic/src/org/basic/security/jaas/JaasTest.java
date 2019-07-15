package org.basic.security.jaas;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.basic.security.jaas.login.SimpleCallbackHandle;
import org.basic.security.jaas.secret.CountFilesAction;

/**
 * Created on Apr 29, 2014, 2:31:58 PM
 */
/**
 * <pre>
 * java -classpath %APUSIC_HOME%/lib/apusic.jar;./build;./build/actions 
 * -Djava.security.manager
 * -Djava.security.policy==policy.jaas
 * -Djava.security.auth.login.config==login.conf
 * -Dapusic.home=%APUSIC_HOME%
 * </pre>
 */
public class JaasTest {

    public static void main(String[] args) {
        String loginFile = JaasTest.class.getResource("login.conf").toString();
        String policyFile = JaasTest.class.getResource("policy.jaas").toString();

        System.setProperty("java.security.manager", "");
        System.setProperty("java.security.auth.login.config", loginFile);
        System.setProperty("java.security.policy", policyFile);

        javax.security.auth.login.LoginContext loginContext = null;
        try {
            loginContext = new LoginContext("simple", new SimpleCallbackHandle());
        } catch (LoginException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        try {
            loginContext.login();
        } catch (LoginException e) {
            e.printStackTrace();
        }

        if (loginContext.getSubject() == null) {
            System.out.println("Skip the protected action: CountFilesAction.run()");
        } else {
            System.out.println("Verification passed and try to execute the protected action: CountFilesAction.run()");
            CountFilesAction<Integer> action = new CountFilesAction<>();
            Integer result = Subject.doAs(loginContext.getSubject(), action);
            System.out.println("User " + loginContext.getSubject() + " found " + result + " files.");

        }

        try {
            loginContext.logout();
        } catch (LoginException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}