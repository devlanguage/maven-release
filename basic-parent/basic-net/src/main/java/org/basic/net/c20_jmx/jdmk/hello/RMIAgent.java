package org.basic.net.c20_jmx.jdmk.hello;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

// import com.sun.jdmk.comm.HtmlAdaptorServer;
// import com.sun.management.comm.SnmpAdaptorServer;

public class RMIAgent {

    static MBeanServer mbs;

    public static void main(String[] args) {

        try {
            LocateRegistry.createRegistry(9999);
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }
        MBeanServer mbs = MBeanServerFactory.createMBeanServer("HelloAgent");
        Map<String, Object> envMap = new HashMap<String, Object>();
        // envMap.put(Context.INITIAL_CONTEXT_FACTORY, NamingContextFactory.class.getName());
        // envMap.put(Context.URL_PKG_PREFIXES, "");
        // envMap.put(Context.PROVIDER_URL, "rmi://localhost:9999");
        try {
            System.out.println("Create an RMI Connector server");

            // Create an RMI connector server
            //  
            // JMXServiceURL jmxmpUrl = new JMXServiceURL(
            // "service:jmx:jmxmp:///jndi/jmxmp://localhost:9999/server");
            JMXServiceURL rmiUrl = new JMXServiceURL(
            "service:jmx:rmi:///jndi/rmi://localhost:9999/server");
    
            JMXConnectorServer cs = JMXConnectorServerFactory.newJMXConnectorServer(rmiUrl, null, mbs);
            cs.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // final static void createHtmlAdapter() {
    //
    // try {
    // HtmlAdaptorServer htmlAdapter = new HtmlAdaptorServer();
    // htmlAdapter.setPort(8080);
    // ObjectName connectorName = new ObjectName("HelloAgent:name=HTTPConnector,port=8080");
    // mbs.registerMBean(htmlAdapter, connectorName);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }
    //
    // final static void createSnmpAdapter() {
    //
    // try {
    // SnmpAdaptorServer snmpAdapter = new SnmpAdaptorServer();
    // snmpAdapter.setPort(8081);
    // ObjectName connectorName = new ObjectName("HelloAgent:name=HTTPConnector,port=8080");
    // mbs.registerMBean(snmpAdapter, connectorName);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }

    // &nbsp;&nbsp; rmi://localhost:1099
    final static void createRMIServerConnectorByJDMK() {

        try {
            JMXServiceURL url = new JMXServiceURL("jdmk-http", null, 8082, "/myHTTP");
            JMXConnectorServer connector = JMXConnectorServerFactory.newJMXConnectorServer(url,
                    null, mbs);
            ObjectName connectorName = new ObjectName("HelloAgent:name=HTTPConnector,port=8082");
            mbs.registerMBean(connector, connectorName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // &nbsp;&nbsp; http://localhost:8080
    final static void createHttpServerConnectorByJDMK() {

        try {
            JMXServiceURL url = new JMXServiceURL("jdmk-rmi", null, 8083, "/myRMI");
            JMXConnectorServer connector = JMXConnectorServerFactory.newJMXConnectorServer(url,
                    null, mbs);
            ObjectName connectorName = new ObjectName("HelloAgent:name=RMIConnector, port=8083");
            mbs.registerMBean(connector, connectorName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
