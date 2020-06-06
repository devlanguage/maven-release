package org.third.server.tomcat;

import org.apache.catalina.Globals;
import org.apache.catalina.startup.Bootstrap;

public class TomcatBootstrapMain {
    public static void main(String[] args) {
        args = new String[] { ",", "start", "-config", "conf/servertest.xml" };

        System.setProperty(Globals.CATALINA_HOME_PROP, "catalina_home");
        System.setProperty(Globals.CATALINA_BASE_PROP, "catalina_base");
        Bootstrap.main(args);
    }

}
