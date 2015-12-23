/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.mq.imq.example.client.TopicTest_1.java is created on 2008-1-16
 */
package org.third.jms.imq.topic;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.basic.common.util.JmsUtil;
import org.basic.common.util.JmsUtil.JmsServerType;
import org.basic.concurrent.concurrent_jdk15.BooleanLock;

/**
 * 
 */
public class TopicTest_1 {

    final static BooleanLock GO_ON = new BooleanLock(true);
    javax.jms.Session session = null;
    javax.jms.Topic topic = null;
    Connection conn = null;

    public TopicTest_1() {

        try {

            session = JmsUtil.getInstance().getSession(JmsServerType.IMQ, false, Session.AUTO_ACKNOWLEDGE);
            // session.setMessageListener(new MessageListener() {
            //
            // public void onMessage(Message message) {
            //
            // System.out.println("Received: " + message);
            // }
            // });
            // final Topic topic = session.createTopic("Topic1");
            topic = session.createTopic("MyTopic1");
            startProducer(session, topic);
            
            startConsumer(session, topic);
        } catch (Exception e) {
            e.printStackTrace();
            GO_ON.setAlive(false);
        }
    }

    public void startConsumer(final javax.jms.Session session, final javax.jms.Topic topic) throws JMSException {

        final MessageConsumer consumer1 = session.createConsumer(topic);
        consumer1.setMessageListener(new MessageListener() {

            public void onMessage(Message message) {
//                consumer1.receive(100L);
//                consumer1.receiveNoWait();
//                consumer1.receive();
                TextMessage tmsg = (TextMessage) message;
                try {
                    System.err.println("Consumer1 Received: " + tmsg.getText());
                } catch (JMSException e) {
                    GO_ON.setAlive(false);
                    e.printStackTrace();
                }
            }
        });

        final MessageConsumer consumer2 = session.createConsumer(topic);
        consumer2.setMessageListener(new MessageListener() {

            public void onMessage(Message message) {

                TextMessage tmsg = (TextMessage) message;
                try {
                    System.err.println("Consumer2 Received: " + tmsg.getText());
                } catch (JMSException e) {
                    GO_ON.setAlive(false);
                    e.printStackTrace();
                }
            }
        });
    }

    public void startProducer(final javax.jms.Session session, final javax.jms.Topic topic) throws InterruptedException {

        Thread msgSender1 = new Thread(new Runnable() {

            public void run() {

                int i = 0;
                try {
                    MessageProducer producer = session.createProducer(topic);
                    while (GO_ON.isAlive()) {

                        TextMessage tmsg = session.createTextMessage("hello-" + i++);
                        System.out.println("Producer1 Send: " + tmsg.getText());
                        producer.send(tmsg);
                        Thread.sleep(3000);
                    }
                } catch (Exception e) {
                    GO_ON.setAlive(false);
                    e.printStackTrace();

                }
            }
        });
        msgSender1.start();

        Thread msgSender2 = new Thread(new Runnable() {

            public void run() {

                int i = 0;

                try {
                    MessageProducer producer = session.createProducer(topic);
                    while (GO_ON.isAlive()) {
                        TextMessage tmsg = session.createTextMessage("hello-" + i++);
                        System.out.println("Producer2 Send: " + tmsg.getText());
                        producer.send(tmsg);
                        Thread.sleep(3000);
                    }
                } catch (Exception e) {
                    GO_ON.setAlive(false);
                    e.printStackTrace();
                }
            }
        });
        msgSender2.start();
        msgSender2.join();

    }

    public static void main(String[] args) {

        TopicTest_1 topicTest = new TopicTest_1();
        try {
            topicTest.startConsumer(topicTest.session, topicTest.topic);
            topicTest.startProducer(topicTest.session, topicTest.topic);
        } catch (Exception e) {
            e.printStackTrace();
            GO_ON.setAlive(false);
        } finally {
            try {
                if (topicTest.conn != null)
                    topicTest.conn.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

    }

}
