/**
 * 
 */
package org.third.jms.imq.topic;

import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.basic.common.util.JmsUtil;
import org.basic.common.util.JmsUtil.JmsServerType;

/**
 * @author ygong
 * 
 */
public class TopicReceiverTest1 {

    /**
     * This value is returned from the method <CODE>getAcknowledgeMode</CODE> if the session is
     * transacted. If a <CODE>Session</CODE> is transacted, the acknowledgement mode is ignored.
     */
    /**
     * @param args
     */
    public static void main(String[] args) {

        try {
//            ImqConnectionService jmsService = ImqConnectionService.getInstance();
            Session jmsSession1 = JmsUtil.getInstance().getSession(JmsServerType.IMQ, false,
                    javax.jms.Session.CLIENT_ACKNOWLEDGE);
            Topic metricTopic1 = jmsSession1.createTopic("Test");
            MessageConsumer metricConsumer = jmsSession1.createConsumer(metricTopic1);
            while (true) {
                TextMessage msg = (TextMessage)metricConsumer.receive();
//                 msg.acknowledge();
                System.out.println("Received: "+msg.getText());
            }

            // metricConsumer.setMessageListener(new MessageListener() {
            // public void onMessage(Message msg) {
            // System.out.println(msg);
            // }
            // });

        } catch (Exception e) {
            System.err.println("Cannot subscribe to metric topic: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
