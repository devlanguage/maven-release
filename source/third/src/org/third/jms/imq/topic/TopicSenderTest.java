/**
 * 
 */
package org.third.jms.imq.topic;

import javax.jms.DeliveryMode;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.basic.common.util.JmsUtil;
import org.basic.common.util.JmsUtil.JmsServerType;

/**
 * @author ygong
 * 
 */
public class TopicSenderTest {

    /**
     * This value is returned from the method <CODE>getAcknowledgeMode</CODE> if the session is transacted. If a
     * <CODE>Session</CODE> is transacted, the acknowledgement mode is ignored.
     */
    /**
     * @param args
     */
    public static void main(String[] args) {

        try {
            Session jmsSession1 = JmsUtil.getInstance().getSession(JmsServerType.IMQ);
            Topic metricTopic1 = jmsSession1.createTopic("Test");
            MessageProducerThread producer = new MessageProducerThread(jmsSession1, metricTopic1);
            producer.start();

        } catch (Exception e) {
            System.err.println("Cannot subscribe to metric topic: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    static class MessageProducerThread extends Thread {

        javax.jms.Session jmsSession;
        javax.jms.Topic metricTopic;

        MessageProducerThread(javax.jms.Session session, javax.jms.Topic topic) {

            this.jmsSession = session;
            this.metricTopic = topic;
        }

        public void run() {

            MessageProducer producer = null;
            int i = 0;
            while (true) {
                try {
                    if (producer == null) {
                        producer = jmsSession.createProducer(metricTopic);
                    }
                    TextMessage msg = jmsSession.createTextMessage("Test_" + i++);
                    producer.send(msg, DeliveryMode.PERSISTENT, 4, 0);
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
