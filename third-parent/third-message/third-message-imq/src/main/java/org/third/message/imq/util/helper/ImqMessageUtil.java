package org.third.message.imq.util.helper;

import java.util.Map;
import java.util.Vector;

import javax.jms.JMSException;

import org.basic.common.bean.CommonLogger;
import org.basic.common.util.BasicException;
import org.third.message.imq.util.NbiMessageReceiver;
import org.third.message.imq.util.NbiMessageSender;
import org.third.message.imq.util.bean.BaseMessageListener;
import org.third.message.imq.util.bean.BaseQueue;
import org.third.message.imq.util.bean.BaseTopic;
import org.third.message.imq.util.bean.NbiMessage;
import org.third.message.imq.util.bean.QueueName_T;
import org.third.message.imq.util.bean.ServiceName_T;
import org.third.message.imq.util.bean.TopicName_T;

public class ImqMessageUtil {

    public static void startNbiMessaging(String host, String port, String username) throws BasicException {

        try {
            ImqMessageService.getInstance().setupConnections(host, port, username);
            ImqMessageService.getInstance().startBrokerConnections();
        } catch (JMSException e) {
            throw new BasicException(e);
        }
    }

    public static void stopNbiMessaging(boolean mtosiEnable) throws BasicException {

        try {
            ImqMessageService.getInstance().stopMessageQueue();
            if (mtosiEnable) {
                ImqMessageService.getInstance().stopBrokerConnections();
            }
        } catch (JMSException e) {
            throw new BasicException(e);
        }
    }

    public static void startMetricTopic() {

        ImqMessageService.getInstance().startMetricTopic();
    }

    public static boolean getJMSServerStatus() {

        return ImqMessageService.getInstance().getJMSServerStatus();
    }

    public static Map<QueueName_T, BaseQueue> getAllNbiQueue() {

        return ImqMessageService.getInstance().getAllNbiQueue();
    }

    public static Map<TopicName_T, BaseTopic> getAllNbiTopic() {

        return ImqMessageService.getInstance().getAllNbiTopic();
    }

    public static void createQueue(Vector<QueueName_T> names, CommonLogger logger) throws BasicException {

        try {
            for (int i = 0; i < names.size(); i++) {
                QueueName_T name = names.get(i);
                ImqMessageService.getInstance().createQueue(name, logger);
            }
        } catch (JMSException e) {
            throw new BasicException(e);
        }
    }

    public static void setQueueResetFlag(ServiceName_T serviceName, Integer index, boolean flag) {

        ImqMessageService.getInstance().setQueueResetFlag(serviceName, index, flag);
    }

    public static void createTopic(Vector<TopicName_T> names, CommonLogger logger) throws BasicException {

        try {
            for (int i = 0; i < names.size(); i++) {
                TopicName_T name = names.get(i);
                ImqMessageService.getInstance().createTopic(name, logger);

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

        return Integer.valueOf(ImqMessageService.getInstance().getQueueSize(serviceName, index));
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
