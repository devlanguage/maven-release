
import static org.third.jms.util.helper.ConfigurationConstant.CONFIG_FILE_NAME;
import static org.third.jms.util.helper.ConfigurationConstant.MESSAGE_IMQHOME;
import static org.third.jms.util.helper.ConfigurationConstant.MESSAGE_IMQPORT;
import static org.third.jms.util.helper.ConfigurationConstant.MESSAGE_USERNAME;

import java.util.Properties;

import javax.jms.JMSException;

import org.basic.common.bean.CommonLogger;
import org.third.jms.util.bean.BaseMessageListener;
import org.third.jms.util.bean.NbiBody;
import org.third.jms.util.bean.NbiHeader;
import org.third.jms.util.bean.NbiMessage;
import org.third.jms.util.bean.QueueName_T;
import org.third.jms.util.bean.ServiceName_T;
import org.third.jms.util.bean.TopicName_T;
import org.third.jms.util.helper.ActiveMqMsgService;
import org.third.jms.util.helper.Configuration;
import org.third.jms.util.helper.ConfigurationLoader;
import org.third.jms.util.helper.ImqMessageUtil;

/**
 * This class gives examples to show the usage of NbiQueues and NbiTopics
 */
public class NbiMessageTest {

    public static CommonLogger logger = CommonLogger.getLogger(NbiMessageTest.class);
    static Configuration config = null;
    static ActiveMqMsgService IMQ_MESSAGE_SERVICE = ActiveMqMsgService.getInstance();
    static {
        try {
            config = ConfigurationLoader.load(CONFIG_FILE_NAME);
            IMQ_MESSAGE_SERVICE.setupConnections(config.get(MESSAGE_IMQHOME), config
                    .get(MESSAGE_IMQPORT), config.get(MESSAGE_USERNAME));
            IMQ_MESSAGE_SERVICE.startBrokerConnections();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void testQueue(final ServiceName_T serviceName, final QueueName_T queueName, final Integer queueType) {

        try {
            IMQ_MESSAGE_SERVICE.createQueue(QueueName_T.MTOSI_SERVICE_REMOTE_QUEUE, CommonLogger.getLogger(NbiMessageTest.class));
        } catch (JMSException e) {
            e.printStackTrace();
        }
        BaseMessageListener consumer = new BaseMessageListener() {

            public void onMessage(NbiMessage nbiMsg) {

                try {
                    System.out.println("Received the Message..");
                    NbiMessageSender.replyTo(nbiMsg, nbiMsg);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

        };

        try {
            ImqMessageUtil.addQueueConsumer(serviceName, queueType, consumer);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        for (int j = 0; j < 10; j++) {
            Thread senderThread = new Thread() {

                public void run() {

                    int i = 0;
                    while (true) {
                        try {
                            i++;
                            NbiHeader header1 = new NbiHeader();
                            header1.setServiceName(serviceName);
                            header1.setQueueNo(queueType);

                            NbiMessage nbiMessage = new NbiMessage(header1, new NbiBody());
                            System.out.println("Sending a message" + getName() + "-" + i);
                            NbiMessage replyMsg = NbiMessageSender.sendReceive(nbiMessage);
                            System.out.println("Received the reply..." + getName() + "-" + i);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            };

            senderThread.start();
        }
    }

    private static void testTopics() {

        BaseMessageListener faultServiceConsumer = new BaseMessageListener() {

            public void onMessage(NbiMessage nbiMsg) {

                try {
                    System.out.println("Received the Message.." + nbiMsg.getHeader().getMessageId() + "---"
                            + nbiMsg.getHeader().getMsgProperties().getProperty("eventType"));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

        };

        try {
            NbiMessageReceiver.addTopicSubscriber(TopicName_T.FAULT_TOPIC,
                    "eventType='objectCreation' OR eventType='objectDeletion'", faultServiceConsumer);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        for (int j = 0; j < 10; j++) {
            Thread senderThread = new Thread() {

                public void run() {

                    try {
                        int i = 0;
                        while (i++ < 5) {
                            sleep(2000);
                            NbiHeader header1 = new NbiHeader();
                            header1.setServiceName(ServiceName_T.ADAPTER_SERVICE);
                            Properties msgProperties = new Properties();
                            msgProperties.setProperty("eventType", "objectCreation");
                            header1.setMsgProperties(msgProperties);
                            NbiMessage nbiMessage = new NbiMessage(header1, new NbiBody());
                            System.out.println("Publishing message");
                            NbiMessageSender.publish(TopicName_T.FAULT_TOPIC, nbiMessage);

                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            };
            senderThread.start();
        }

        for (int j = 0; j < 10; j++) {
            Thread senderThread1 = new Thread() {

                public void run() {

                    try {
                        int i = 0;
                        while (i++ < 5) {
                            sleep(2000);
                            NbiHeader header1 = new NbiHeader();
                            header1.setServiceName(ServiceName_T.ADAPTER_SERVICE);
                            Properties msgProperties = new Properties();
                            msgProperties.setProperty("eventType", "objectDeletion");
                            header1.setMsgProperties(msgProperties);
                            NbiMessage nbiMessage = new NbiMessage(header1, new NbiBody());
                            System.out.println("Publishing message");
                            NbiMessageSender.publish(TopicName_T.FAULT_TOPIC, nbiMessage);

                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            };
            senderThread1.start();
        }

    }

    public static void main(String[] args) {

        try {
            // msgService.createQueue(QueueName_T.MTOSI_SERVICE_REMOTE_QUEUE,
            // CommonLogger.getLogger(NbiMessageTest.class));
            // msgService.createTopic(TopicName_T.FAULT_TOPIC);
            // testLocalQueue();
            System.out.println("Testing queues.......");
            testQueue(ServiceName_T.MTOSI_MEDIATION_SERVICE, QueueName_T.MTOSI_SERVICE_REMOTE_QUEUE,
                    QueueName_T.REMOTE_QUEUE_NO);

            // System.out.println("Testing topics.....");
            // testTopics();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
