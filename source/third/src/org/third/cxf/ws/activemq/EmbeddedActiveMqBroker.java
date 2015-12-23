/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

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
