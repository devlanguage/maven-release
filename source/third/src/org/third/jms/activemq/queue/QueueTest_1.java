
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
import org.third.jms.util.helper.BooleanLock;

public class QueueTest_1 {

    final static BooleanLock GO_ON = new BooleanLock();
    javax.jms.Session session = null;
    javax.jms.Queue queue1 = null;

    Connection conn = null;

    /**
     * This acknowledgment mode instructs the session to lazily acknowledge the delivery of messages. This is likely to
     * result in the delivery of some duplicate messages if the JMS provider fails, so it should only be used by
     * consumers that can tolerate duplicate messages. Use of this mode can reduce session overhead by minimizing the
     * work the session does to prevent duplicates.
     */
    public QueueTest_1() {

        try {
            session = JmsUtil.getInstance().getSession(JmsServerType.ACTIVE_MQ, false, Session.AUTO_ACKNOWLEDGE);
            queue1 = session.createQueue(QueueTest_1.class.getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startConsumer(final javax.jms.Session session, final javax.jms.Queue queue) throws JMSException {

        final MessageConsumer consumer1 = session.createConsumer(queue1);
        consumer1.setMessageListener(new MessageListener() {

            public void onMessage(Message message) {

                TextMessage textMsg = (TextMessage) message;
                // textMsg.acknowledge();
                try {
                    System.err.println("Consumer1 Received: " + textMsg.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        //
        // final MessageConsumer consumer2 = session.createConsumer(queue);
        // consumer2.setMessageListener(new MessageListener() {
        //
        // public void onMessage(Message message) {
        //
        // TextMessage tmsg = (TextMessage) message;
        // try {
        // System.err.println("Consumer2 Received: " + tmsg.getText());
        // } catch (JMSException e) {
        // GO_ON.setValue(false);
        // e.printStackTrace();
        // }
        // }
        // });

        final MessageConsumer consumer3 = session.createConsumer(queue1);
        consumer3.setMessageListener(new MessageListener() {

            public void onMessage(Message message) {

                TextMessage tmsg = (TextMessage) message;
                try {
                    System.err.println("consumer3 Received: " + tmsg.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void startProducer(final javax.jms.Session session, final javax.jms.Queue queue) throws InterruptedException {

        Thread msgSender1 = new Thread(new Runnable() {

            public void run() {

                int i = 0;

                try {
                    MessageProducer producer = session.createProducer(queue);
                    while (GO_ON.isTrue()) {
                        TextMessage tmsg = session.createTextMessage("Apple-" + i++);
                        System.out.println("Producer1 Send: " + tmsg.getText());
                        producer.send(tmsg);
                        Thread.sleep(3000);
                    }
                } catch (Exception e) {
                    GO_ON.setValue(false);
                    e.printStackTrace();

                }
            }
        });
        msgSender1.start();

        Thread msgSender2 = new Thread(new Runnable() {

            public void run() {

                int i = 0;

                try {
                    MessageProducer producer = session.createProducer(queue);
                    while (GO_ON.isTrue()) {
                        TextMessage tmsg = session.createTextMessage("Orange-" + i++);
                        System.out.println("Producer2 Send: " + tmsg.getText());
                        producer.send(tmsg);
                        Thread.sleep(3000);
                    }
                } catch (Exception e) {
                    GO_ON.setValue(false);
                    e.printStackTrace();

                }
            }
        });
        msgSender2.start();

    }

    public static void main(String[] args) {

        QueueTest_1 queueTest = new QueueTest_1();
        try {
            queueTest.startProducer(queueTest.session, queueTest.queue1);
            queueTest.startConsumer(queueTest.session, queueTest.queue1);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (queueTest.conn != null)
                    queueTest.conn.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

    }
}
