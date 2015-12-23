package org.third.jms.util;

import org.basic.common.util.BasicException;
import org.third.jms.util.bean.BaseMessageListener;
import org.third.jms.util.bean.BaseQueue;
import org.third.jms.util.bean.BaseTopic;
import org.third.jms.util.bean.QueueName_T;
import org.third.jms.util.bean.ServiceName_T;
import org.third.jms.util.bean.TopicName_T;
import org.third.jms.util.helper.ActiveMqMsgService;
import org.third.jms.util.helper.ServiceQueueMapper;


/**
 * NbiMessaging Layer Facade class to be used for receiveing messages
 * on queues or topics
 */

public class NbiMessageReceiver {
  
    public static void addQueueConsumer(ServiceName_T serviceName, Integer index, BaseMessageListener msgListener) throws BasicException {
        QueueName_T queueName = ServiceQueueMapper.getQueueName(serviceName, index);
        BaseQueue queue = ActiveMqMsgService.getInstance().getQueue(queueName);
        if(queue != null)
        	queue.addQueueReceiver(msgListener);
    }
    
    public static void removeQueueConsumer(ServiceName_T serviceName, Integer index, BaseMessageListener msgListener) throws BasicException {
        QueueName_T queueName = ServiceQueueMapper.getQueueName(serviceName, index);
        BaseQueue queue = ActiveMqMsgService.getInstance().getQueue(queueName);
        queue.removeQueueReceiver(msgListener); 
    }
    
    public static void addTopicSubscriber(TopicName_T topicName, BaseMessageListener msgListener) throws BasicException {
        BaseTopic topic = ActiveMqMsgService.getInstance().getTopic(topicName);
        topic.addTopicSubscriber(msgListener, null);
    }  
    
    public static void addTopicSubscriber(TopicName_T topicName, String mesgSelector, BaseMessageListener msgListener) throws BasicException {
        BaseTopic topic = ActiveMqMsgService.getInstance().getTopic(topicName);
        if(topic != null)
        	topic.addTopicSubscriber(msgListener, mesgSelector);
    } 
    
    public static void removeTopicSubscriber(TopicName_T topicName, BaseMessageListener msgListener) throws BasicException {
        BaseTopic topic = ActiveMqMsgService.getInstance().getTopic(topicName);
        if(topic != null)
        	topic.removeTopicSubscriber(msgListener);
    }  
}
