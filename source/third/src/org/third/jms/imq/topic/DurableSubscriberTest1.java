/**
 * 
 */
package org.third.jms.imq.topic;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import org.basic.common.util.JmsUtil;
import org.basic.common.util.JmsUtil.JmsServerType;

/**
 * @author ygong
 * 
 */
public class DurableSubscriberTest1 {

    /**
     * This value is returned from the method <CODE>getAcknowledgeMode</CODE> if the session is transacted. If a
     * <CODE>Session</CODE> is transacted, the acknowledgement mode is ignored.
     */
    /**
     * @param args
     */
    public static void main(String[] args) {

        javax.jms.Connection jmsConnection = null;
        javax.jms.Session jmsSession1 = null;
        TopicSubscriber subscriber = null;
        try {
            jmsConnection = JmsUtil.getInstance().getConnection(JmsServerType.IMQ, true);
            jmsSession1 = JmsUtil.getInstance().getSession(JmsServerType.IMQ, false, javax.jms.Session.CLIENT_ACKNOWLEDGE);
            Topic metricTopic1 = jmsSession1.createTopic("Test");
            subscriber = jmsSession1.createDurableSubscriber(metricTopic1, "DurableSubscription");
            while (true) {
                TextMessage msg = (TextMessage) subscriber.receive();
                // msg.acknowledge();
                System.out.println("Received: " + msg.getText());
            }
        } catch (Exception e) {
            System.err.println("Cannot subscribe to metric topic: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } finally {
            try {
                subscriber.close();
                jmsSession1.unsubscribe("DurableSubscription");
            } catch (JMSException e) {
                e.printStackTrace();
            }

        }
    }
}
