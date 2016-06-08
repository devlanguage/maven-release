/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 5:23:17 PM Mar 5, 2014
 *
 *****************************************************************************
 */
package org.third.jms.activemq.embeded;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.store.memory.MemoryPersistenceAdapter;
import org.codehaus.activemq.store.jdbc.JDBCPersistenceAdapter;

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
