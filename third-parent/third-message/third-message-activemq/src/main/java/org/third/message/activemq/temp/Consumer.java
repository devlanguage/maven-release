package org.third.message.activemq.temp;

import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.third.message.activemq.MessageUtil;
import org.third.message.activemq.MessageUtil.JmsServerType;

/**
 * @author <a href="http://www.christianposta.com/blog">Christian Posta</a>
 */
public class Consumer {
    private static final Boolean NON_TRANSACTED = false;
    private static final long TIMEOUT = 20000;

    public static void main(String[] args) {

        try {

            final Session session = MessageUtil.getInstance().createSession(JmsServerType.ACTIVE_MQ, false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("test-queue");
            MessageConsumer consumer = session.createConsumer(destination);

            int i = 0;
            while (true) {
                Message message = consumer.receive(TIMEOUT);

                if (message != null) {
                    if (message instanceof TextMessage) {
                        String text = ((TextMessage) message).getText();
                        System.out.println("Got " + i++ + ". message: " + text);
                        Destination replyTo = message.getJMSReplyTo();
                        MessageProducer producer = session.createProducer(replyTo);
                        producer.send(session.createTextMessage("You made it to the consumer, here is your response"));
                        producer.close();
                    }
                } else {
                    break;
                }
            }

            consumer.close();
            session.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Caught exception!");
        } finally {
        }
    }
}