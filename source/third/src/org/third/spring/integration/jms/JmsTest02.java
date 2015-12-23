package org.third.spring.integration.jms;

import org.third.spring.SpringTest;

public class JmsTest02 extends SpringTest {

    public final static String BEAN_NAME_activeMqResourceAdapter = "activeMqResourceAdapter";
    public final static String BEAN_NAME_imqResourceAdapter = "imqResourceAdapter";
    public final static String BEAN_NAME_imqLocalConnectionFactory = "imqLocalConnectionFactory";

    public JmsTest02(String configFile) {
        super(configFile);
    }

    public static void main(String[] args) {

        new JmsTest02("spring_jms.xml").test();
    }

    /**
     * 
     */
    public void test() {
        // com.sun.messaging.jms.ra.ResourceAdapter imqResourceAdapter =
        // (com.sun.messaging.jms.ra.ResourceAdapter) ctx
        // .getBean(BEAN_NAME_imqResourceAdapter);
        // System.out.println(ctx.getBean(BEAN_NAME_imqLocalConnectionFactory));
        // org.springframework.jca.support.LocalConnectionFactoryBean
        // imqConnectionFactory =
        // (LocalConnectionFactoryBean)ctx.getBean(BEAN_NAME_imqLocalConnectionFactory);
//        com.sun.messaging.jms.ra.ConnectionFactoryAdapter imqConnectionFactory = (ConnectionFactoryAdapter) ctx
//                .getBean(BEAN_NAME_imqLocalConnectionFactory);
//        try {
//
//            com.sun.messaging.jms.ra.ConnectionAdapter imqConnectionAdapter = (com.sun.messaging.jms.ra.ConnectionAdapter) imqConnectionFactory
//                    .createConnection();
//            final javax.jms.Session consumerSession = imqConnectionAdapter.createSession(false,
//                    Session.AUTO_ACKNOWLEDGE);
//           javax.jms.Topic  MyTopic1 = consumerSession.createTopic("MyTopic1");
//            consumerSession.createConsumer(MyTopic1).setMessageListener(new MessageListener() {
//
//                public void onMessage(Message message) {
//                    System.out.println(message);
//                    
//                }
//                
//            });
            /*
             String queueAName = "ygong_dest_queue1";
             javax.jms.Queue queueA = consumerSession.createQueue(queueAName);
             // consumerSession.setMessageListener(new MessageListener() {
             //
             // public void onMessage(javax.jms.Message msg) {
             // System.out.println(msg);
             // }
             // });

             final javax.jms.MessageConsumer consumer01 = consumerSession.createConsumer(queueA);
             consumer01.setMessageListener(new MessageListener() {

             public void onMessage(Message message) {
             System.err.println(message);

             }
             });
             // new Thread(new Runnable() {
             //
             // public void run() {
             // while (true) {
             // TextMessage textMsg;
             // try {
             // textMsg = (TextMessage) consumer01.receive();
             // System.out.println(textMsg);
             // } catch (JMSException e) {
             // // TODO Auto-generated catch block
             // e.printStackTrace();
             // }
             //
             // }
             // }
             //
             // }).start();
*/
//             new Thread(new Runnable() {
//
//             public void run() {
//             while (true)
//             try {
//             Thread.sleep(100);
//             } catch (InterruptedException e) {
//             // TODO Auto-generated catch block
//             e.printStackTrace();
//             }
//             }
//             }).start();
//             
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        /*
         * // ResourceAdapterFactoryBean raf = (ResourceAdapterFactoryBean) //
         * ctx.getBean(BEAN_NAME_jcaResourceAdapterFactory);
         * javax.resource.spi.ResourceAdapter ra = (ActiveMQResourceAdapter) ctx
         * .getBean(BEAN_NAME_activeMqResourceAdapter);
         * 
         * ActiveMQResourceAdapter activeMqResourceAdapter =
         * (ActiveMQResourceAdapter) ra;
         * 
         * try { System.out.println(activeMqResourceAdapter.makeConnection()); }
         * catch (JMSException e) { // TODO Auto-generated catch block
         * e.printStackTrace(); }
         */
    }
}
