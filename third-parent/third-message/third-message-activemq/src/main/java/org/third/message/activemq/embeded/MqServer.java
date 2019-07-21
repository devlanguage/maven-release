package org.third.message.activemq.embeded;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.store.memory.MemoryPersistenceAdapter;

/**
 * Created on Mar 5, 2014, 5:23:17 PM
 */
public class MqServer {
    public static void main(String[] args) {
        // args = new String[] { "start" };
        // String activeHome = System.getenv().get("ACTIVEMQ_HOME"), //
        // activeMqConf = "-Dactivemq.conf " + System.getProperty("ACTIVEMQ_HOME") + "/conf", //
        // activeMqData = "-Dactivemq.data " + System.getProperty("ACTIVEMQ_HOME") + "/data";
        // try {
        // Runtime.getRuntime().exec("cmd c: \r\n cd "+activeHome+"\r\njava -jar " + activeHome + "/bin/activemq.jar");
        // } catch (IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // org.apache.activemq.console.Main.main(args);
        BrokerService broker = new BrokerService();
        try {
            broker.setPersistenceAdapter(new MemoryPersistenceAdapter());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            broker.addConnector("tcp://localhost:61616");

            broker.start();
            System.out.println("JMS broker ready ...");
            Thread.sleep(125 * 60 * 1000);
            System.out.println("JMS broker exiting");
            broker.stop();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.exit(0);
    }
}
