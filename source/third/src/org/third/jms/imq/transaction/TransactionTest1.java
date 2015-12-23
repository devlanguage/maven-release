package org.third.jms.imq.transaction;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.TextMessage;

import org.basic.common.util.JmsUtil;
import org.basic.common.util.JmsUtil.JmsServerType;
import org.third.jms.activemq.queue.QueueBrowser_WithWindow;
import org.third.jms.util.ImqMessageListener;

public class TransactionTest1 {

    public final static String QUEUE_NAME_TransactionTest1 = TransactionTest1.class.getSimpleName();

    public static void main(String[] args) {

        QueueBrowser_WithWindow.startQueueBrowser();

        javax.jms.Session jmsSession2;
        try {
            jmsSession2 = JmsUtil.getInstance().getSession(JmsServerType.IMQ, false, javax.jms.Session.AUTO_ACKNOWLEDGE);
            Queue testQueue2 = jmsSession2.createQueue(QUEUE_NAME_TransactionTest1);
            MessageConsumer consumer = jmsSession2.createConsumer(testQueue2);
            consumer.setMessageListener(new ImqMessageListener());
        } catch (JMSException e) {
            e.printStackTrace();
        }

        try {
            javax.jms.Session jmsSession = JmsUtil.getInstance().getSession(JmsServerType.IMQ, true, javax.jms.Session.SESSION_TRANSACTED);

            Queue testQueue1 = jmsSession.createQueue(QUEUE_NAME_TransactionTest1);
            Queue testQueue1_reply = jmsSession.createQueue(QUEUE_NAME_TransactionTest1 + "_Reply");
            MessageProducer producer = jmsSession.createProducer(testQueue1);
            int i = 1;
            while (true) {
                TextMessage textMsg = jmsSession.createTextMessage("Message_" + i++);
                textMsg.setJMSReplyTo(testQueue1_reply);
                producer.send(textMsg);
                if (0 == i % 5) {
                    System.out.println("#################");
                    jmsSession.commit();
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
