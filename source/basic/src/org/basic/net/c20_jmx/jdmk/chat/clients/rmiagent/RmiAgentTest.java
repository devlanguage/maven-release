package org.basic.net.c20_jmx.jdmk.chat.clients.rmiagent;

import java.io.IOException;

import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.basic.net.c20_jmx.jdmk.chat.clients.BaseAccessTest;

public class RmiAgentTest extends BaseAccessTest {

    public static void main(String[] args) {

        new RmiAgentTest().startTest("service:jmx:rmi:///jndi/rmi://localhost:8081/server");

    }

    private void startTest(String rmiUrlString) {

        try {
            JMXServiceURL rmiUrl = new JMXServiceURL(rmiUrlString);
            client = JMXConnectorFactory.newJMXConnector(rmiUrl, null);
            client.connect();
            System.out.println("ConnectionId=" + client.getConnectionId());
            displayMBeanServerConnection(client.getMBeanServerConnection());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
