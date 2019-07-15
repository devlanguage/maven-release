package org.third.cxf.ws.activemq;

import java.lang.reflect.Method;

public final class EmbeddedActiveMqBroker {
    private EmbeddedActiveMqBroker() {
    }

    public static void main(String[] args) throws Exception {
//        /*
//         * The following make it easier to run this against something other than ActiveMQ. You will have to get a
//         * JMS broker onto the right port of localhost.
//         */
//        Class<?> brokerClass = ActiveMqHelloServer.class.getClassLoader().loadClass("org.apache.activemq.broker.BrokerService");
//        if (brokerClass == null) {
//            System.err.println("ActiveMQ is not in the classpath, cannot launch broker.");
//            return;
//        }
//        Object broker = brokerClass.newInstance();
//        Method addConnectorMethod = brokerClass.getMethod("addConnector", String.class);
//        addConnectorMethod.invoke(broker, "tcp://localhost:61616");
//        Method setDataDirectory = brokerClass.getMethod("setDataDirectory", String.class);
//        setDataDirectory.invoke(broker, "target/activemq-data");
//        Method startMethod = brokerClass.getMethod("start");
//        startMethod.invoke(broker);
        
        org.apache.activemq.broker.BrokerService broker = new org.apache.activemq.broker.BrokerService();
        broker.setPersistenceAdapter(new org.apache.activemq.store.memory.MemoryPersistenceAdapter());
        broker.setDataDirectory("db/activemq-data");
        broker.addConnector("tcp://localhost:61616");
        broker.start();
        System.out.println("JMS broker ready ...");
        Thread.sleep(125 * 60 * 1000);
        System.out.println("JMS broker exiting");
        broker.stop();
        System.exit(0);
    }
}
