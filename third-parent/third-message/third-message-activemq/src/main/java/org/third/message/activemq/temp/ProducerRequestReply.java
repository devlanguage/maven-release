package org.third.message.activemq.temp;

import java.util.concurrent.TimeUnit;

import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.basic.common.util.SystemUtils;
import org.third.message.activemq.MessageUtil;
import org.third.message.activemq.MessageUtil.JmsServerType;

/**
 * @author <a href="http://www.christianposta.com/blog">Christian Posta</a>
 */
public class ProducerRequestReply {

	private static final int NUM_MESSAGES_TO_SEND = 100;
	private static final long DELAY = 100;

	public static void main(String[] args) {
		try {
			Session session = MessageUtil.getInstance().createSession(JmsServerType.ACTIVE_MQ, false,
					Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue("test-queue");
			MessageProducer producer = session.createProducer(destination);
			Destination replyDest = session.createTemporaryQueue();

			// set up the consumer to handle the reply
			MessageConsumer replyConsumer = session.createConsumer(replyDest);
			replyConsumer.setMessageListener(new MessageListener() {
				@Override
				public void onMessage(Message message) {
					SystemUtils.printMessage("*** REPLY *** ");
					SystemUtils.printMessage(message.toString());
				}
			});

			TextMessage message = session.createTextMessage("I need a response for this, please");
			message.setJMSReplyTo(replyDest);

			producer.send(message);

			// wait for a response
			TimeUnit.SECONDS.sleep(2);
			producer.close();
			session.close();

		} catch (Exception e) {
			System.out.println("Caught exception!");
		} finally {

		}
	}

}