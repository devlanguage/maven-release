package org.third.jms.util.helper;

import java.util.Map;
import java.util.Vector;

import javax.jms.JMSException;

import org.basic.common.bean.CommonLogger;
import org.basic.common.util.BasicException;
import org.third.jms.util.NbiMessageReceiver;
import org.third.jms.util.NbiMessageSender;
import org.third.jms.util.bean.BaseMessageListener;
import org.third.jms.util.bean.BaseQueue;
import org.third.jms.util.bean.BaseTopic;
import org.third.jms.util.bean.NbiMessage;
import org.third.jms.util.bean.QueueName_T;
import org.third.jms.util.bean.ServiceName_T;
import org.third.jms.util.bean.TopicName_T;

public class ImqMessageUtil {

    public static void startNbiMessaging(String host, String port, String username) throws BasicException {

        try {
            ActiveMqMsgService.getInstance().setupConnections(host, port, username);
            ActiveMqMsgService.getInstance().startBrokerConnections();
        } catch (JMSException e) {
            throw new BasicException(e);
        }
    }

    public static void stopNbiMessaging(boolean mtosiEnable) throws BasicException {

        try {
            ActiveMqMsgService.getInstance().stopMessageQueue();
            if (mtosiEnable) {
                ActiveMqMsgService.getInstance().stopBrokerConnections();
            }
        } catch (JMSException e) {
            throw new BasicException(e);
        }
    }

    public static void startMetricTopic() {

        ActiveMqMsgService.getInstance().startMetricTopic();
    }

    public static boolean getJMSServerStatus() {

        return ActiveMqMsgService.getInstance().getJMSServerStatus();
    }

    public static Map<QueueName_T, BaseQueue> getAllNbiQueue() {

        return ActiveMqMsgService.getInstance().getAllNbiQueue();
    }

    public static Map<TopicName_T, BaseTopic> getAllNbiTopic() {

        return ActiveMqMsgService.getInstance().getAllNbiTopic();
    }

    public static void createQueue(Vector<QueueName_T> names, CommonLogger logger) throws BasicException {

        try {
            for (int i = 0; i < names.size(); i++) {
                QueueName_T name = names.get(i);
                ActiveMqMsgService.getInstance().createQueue(name, logger);
            }
        } catch (JMSException e) {
            throw new BasicException(e);
        }
    }

    public static void setQueueResetFlag(ServiceName_T serviceName, Integer index, boolean flag) {

        ActiveMqMsgService.getInstance().setQueueResetFlag(serviceName, index, flag);
    }

    public static void createTopic(Vector<TopicName_T> names, CommonLogger logger) throws BasicException {

        try {
            for (int i = 0; i < names.size(); i++) {
                TopicName_T name = names.get(i);
                ActiveMqMsgService.getInstance().createTopic(name, logger);

            }
        } catch (JMSException e) {
            throw new BasicException(e);
        }
    }

    public static void addQueueConsumer(ServiceName_T serviceName, BaseMessageListener msgListener)
            throws BasicException {

        addQueueConsumer(serviceName, 0, msgListener);
    }

    public static void addQueueConsumer(ServiceName_T serviceName, Integer index, BaseMessageListener msgListener)
            throws BasicException {

        NbiMessageReceiver.addQueueConsumer(serviceName, index, msgListener);
    }

    public static void removeQueueConsumer(ServiceName_T serviceName, BaseMessageListener msgListener)
            throws BasicException {

        removeQueueConsumer(serviceName, 0, msgListener);
    }

    public static void removeQueueConsumer(ServiceName_T serviceName, Integer index, BaseMessageListener msgListener)
            throws BasicException {

        NbiMessageReceiver.removeQueueConsumer(serviceName, index, msgListener);
    }

    public static Integer getQueueSize(ServiceName_T serviceName, Integer index) {

        return Integer.valueOf(ActiveMqMsgService.getInstance().getQueueSize(serviceName, index));
    }

    public static void addTopicSubscriber(TopicName_T topicName, BaseMessageListener msgListener) throws BasicException {

        NbiMessageReceiver.addTopicSubscriber(topicName, msgListener);
    }

    public static void addTopicSubscriber(TopicName_T topicName, String selector, BaseMessageListener msgListener)
            throws BasicException {

        NbiMessageReceiver.addTopicSubscriber(topicName, selector, msgListener);
    }

    public static void removeTopicSubscriber(TopicName_T topicName, BaseMessageListener msgListener)
            throws BasicException {

        NbiMessageReceiver.removeTopicSubscriber(topicName, msgListener);
    }

    public static NbiMessage sendReceive(NbiMessage nbiMessage) throws BasicException {

        return NbiMessageSender.sendReceive(nbiMessage);
    }

    public static void send(NbiMessage nbiMessage) throws BasicException {

        NbiMessageSender.send(nbiMessage);
    }

    public static void replyTo(NbiMessage orgNbiMessage, NbiMessage replyMessage) throws BasicException {

        NbiMessageSender.replyTo(orgNbiMessage, replyMessage);
    }

    public static void publish(TopicName_T topicName, NbiMessage nbiMessage) throws BasicException {

        NbiMessageSender.publish(topicName, nbiMessage);
    }
}
