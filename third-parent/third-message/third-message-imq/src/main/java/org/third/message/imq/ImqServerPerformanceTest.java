package org.third.message.imq;

import java.util.concurrent.atomic.AtomicInteger;

import javax.jms.DeliveryMode;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.third.message.imq.util.JmsUtil;
import org.third.message.imq.util.MessageUtil.JmsServerType;

/**
 * @author ygong
 * 
 */
public class ImqServerPerformanceTest {

    /**
     * This value is returned from the method <CODE>getAcknowledgeMode</CODE> if the session is transacted. If a
     * <CODE>Session</CODE> is transacted, the acknowledgement mode is ignored.
     */
    /**
     * @param args
     */
    public static void main(String[] args) {
        // ActiveMQ
        // Localhost server: 2000 message per seconds by average
        // Remote server: 120 message per seconds by average
        try {
            Session jmsSession1 = JmsUtil.getInstance().getSession(JmsServerType.IMQ, false, javax.jms.Session.AUTO_ACKNOWLEDGE);
            Session jmsSession2 = JmsUtil.getInstance().getSession(JmsServerType.IMQ, false, javax.jms.Session.AUTO_ACKNOWLEDGE);

            String testQueueName = ImqServerPerformanceTest.class.getName().replaceAll("\\.", "_");
            Queue metricQueue1 = jmsSession1.createQueue(testQueueName);
            //

            MessageConsumer consumer = jmsSession1.createConsumer(metricQueue1);
            consumer.setMessageListener(new MessageListener() {
                AtomicInteger counter = new AtomicInteger(0);
                int statisticsTimeCount = 0;
                long lastTime1 = System.currentTimeMillis();
                long lastTime2 = System.currentTimeMillis();
                int refSpot = 1000;

                public void onMessage(javax.jms.Message msg) {
                    if (counter.getAndIncrement() % refSpot == 0) {
                        if (counter.get() > 1) {
                            long currentTime = System.currentTimeMillis();
                            System.out.println("send " + refSpot * 1000 / (currentTime - lastTime2) + " message per seconds");
                            if (statisticsTimeCount != 0 && statisticsTimeCount % 10 == 0) {
                                System.out.println("average: " + (statisticsTimeCount * refSpot * 1000) / (currentTime - lastTime1)
                                        + " message per seconds");
                                lastTime1 = currentTime;
                            }
                            lastTime2 = currentTime;
                            statisticsTimeCount++;
                        }
                    }
                    // try {
                    // msg.acknowledge();
                    // } catch (JMSException e) {
                    // e.printStackTrace();
                    // }
                    // System.out.println("\tReceived: " + JmsUtil.toString(msg));
                }
            });
            MessageProducerThread produceer = new MessageProducerThread(jmsSession1, metricQueue1);
            produceer.start();
            MessageProducerThread produceer2 = new MessageProducerThread(jmsSession2, metricQueue1);
            produceer2.start();
        } catch (Exception e) {
            System.err.println("Cannot subscribe to metric topic: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    static class MessageProducerThread extends Thread {

        javax.jms.Session jmsSession;
        javax.jms.Queue metricQueue;

        MessageProducerThread(javax.jms.Session session, javax.jms.Queue queue) {

            this.jmsSession = session;
            this.metricQueue = queue;
        }

        public void run() {

            MessageProducer producer = null;
            int i = 0;
            while (true) {
                try {
                    if (producer == null) {
                        producer = jmsSession.createProducer(metricQueue);
                    }
                    TextMessage msg = jmsSession.createTextMessage("Test_" + i++);
                    // System.out.println("Send message: " + JmsUtil.toString(msg));
                    producer.send(msg, DeliveryMode.NON_PERSISTENT, 4, 0);
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
