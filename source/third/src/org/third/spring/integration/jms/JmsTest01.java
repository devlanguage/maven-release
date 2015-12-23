package org.third.spring.integration.jms;

import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.third.spring.SpringTest;

public class JmsTest01 extends SpringTest {

    public final static String BEAN_NAME_activeMqResourceAdapter = "activeMqResourceAdapter";
    public final static String BEAN_NAME_imqResourceAdapter = "imqResourceAdapter";
    public final static String BEAN_NAME_imqLocalConnectionFactory = "imqLocalConnectionFactory";

    public JmsTest01(String configFile) {
        super(configFile);
    }

    public static void main(String[] args) {

        new JmsTest01("spring_jms.xml").test();
    }

    /**
     * 
     */
    public void test() {
//        // com.sun.messaging.jms.ra.ResourceAdapter imqResourceAdapter =
//        // (com.sun.messaging.jms.ra.ResourceAdapter) ctx
//        // .getBean(BEAN_NAME_imqResourceAdapter);
//        // System.out.println(ctx.getBean(BEAN_NAME_imqLocalConnectionFactory));
//        // org.springframework.jca.support.LocalConnectionFactoryBean
//        // imqConnectionFactory =
//        // (LocalConnectionFactoryBean)ctx.getBean(BEAN_NAME_imqLocalConnectionFactory);
//        com.sun.messaging.jms.ra.ConnectionFactoryAdapter imqConnectionFactory = (ConnectionFactoryAdapter) ctx
//                .getBean(BEAN_NAME_imqLocalConnectionFactory);
//        try {
//
//            com.sun.messaging.jms.ra.ConnectionAdapter imqConnectionAdapter = (com.sun.messaging.jms.ra.ConnectionAdapter) imqConnectionFactory
//                    .createConnection();
//            final javax.jms.Session producerSession = imqConnectionAdapter.createSession(false, Session.AUTO_ACKNOWLEDGE);
//            String queueAName = "ygong_dest_queue1";
//            javax.jms.Queue queueA = producerSession.createQueue(queueAName);
//            producerSession.setMessageListener(new MessageListener() {
//
//                public void onMessage(javax.jms.Message msg) {
//                    TextMessage txtMsg = (TextMessage) msg;
//                    try {
//                        System.out.println("Received" + txtMsg.getText());
//                    } catch (JMSException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
//            });
//
//            final javax.jms.MessageProducer producer01 = producerSession.createProducer(queueA);
//            new Thread(new Runnable() {
//
//                public void run() {
//                    int i = 1;
//                    while (i++ < 10) {
//                        try {
//                            javax.jms.TextMessage textMessage = producerSession.createTextMessage("Zhangsan");
//                            System.out.println("Send:" + textMessage.getText());
//                            producer01.send(textMessage);
//
//                            Thread.sleep(2000);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//            }).start();
//            
////            final javax.jms.Session consumerSession = imqConnectionAdapter.createSession(false, Session.AUTO_ACKNOWLEDGE);
////            final javax.jms.Queue queueA02= consumerSession.createQueue(queueAName);
////            final javax.jms.MessageConsumer consumer01 = consumerSession.createConsumer(queueA02);
////            consumer01.setMessageListener(new javax.jms.MessageListener() {
////
////                public void onMessage(Message message) {
////                    System.out.println("Received: " + message);
////
////                }
////            });
//            
//            // System.out.println(imqQueueConnection.getXAResource());
//            //
//            // com.sun.messaging.jms.ra.ManagedConnection imqTopicConnection =
//            // (ManagedConnection) imqConnectionFactory
//            // .createTopicConnection();
//
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        // // ResourceAdapterFactoryBean raf = (ResourceAdapterFactoryBean) //
        // ctx.getBean(BEAN_NAME_activeMqResourceAdapter);
        // javax.resource.spi.ResourceAdapter ra = (ActiveMQResourceAdapter) ctx
        // .getBean(BEAN_NAME_activeMqResourceAdapter);
        //
        // ActiveMQResourceAdapter activeMqResourceAdapter =
        // (ActiveMQResourceAdapter) ra;
        // try {
        // ActiveMQConnection amqConnectin =
        // activeMqResourceAdapter.makeConnection();
        // //amqConnectin.createSession(transacted, acknowledgeMode)
        // } catch (JMSException e) { // TODO Auto-generated catch block
        // e.printStackTrace();
        // }

    }
}
