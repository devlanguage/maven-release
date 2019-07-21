package org.third.message.imq.util;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 
 */
public class ImqMessageListener implements MessageListener {

    @SuppressWarnings( { "null", "cast", "unchecked", "unused" })
    public void onMessage(Message jmsMessage) {

        if (jmsMessage instanceof TextMessage) {
            TextMessage msg = (TextMessage) jmsMessage;
            try {
                System.out.println(msg.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(jmsMessage);
        }
    }

}
