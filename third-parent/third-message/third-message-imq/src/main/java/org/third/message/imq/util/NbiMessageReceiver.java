package org.third.message.imq.util;

import org.basic.common.util.BasicException;
import org.third.message.imq.util.bean.BaseMessageListener;
import org.third.message.imq.util.bean.BaseQueue;
import org.third.message.imq.util.bean.BaseTopic;
import org.third.message.imq.util.bean.QueueName_T;
import org.third.message.imq.util.bean.ServiceName_T;
import org.third.message.imq.util.bean.TopicName_T;
import org.third.message.imq.util.helper.ImqMessageService;
import org.third.message.imq.util.helper.ServiceQueueMapper;


/**
 * NbiMessaging Layer Facade class to be used for receiveing messages
 * on queues or topics
 */

public class NbiMessageReceiver {
  
    public static void addQueueConsumer(ServiceName_T serviceName, Integer index, BaseMessageListener msgListener) throws BasicException {
        QueueName_T queueName = ServiceQueueMapper.getQueueName(serviceName, index);
        BaseQueue queue = ImqMessageService.getInstance().getQueue(queueName);
        if(queue != null)
        	queue.addQueueReceiver(msgListener);
    }
    
    public static void removeQueueConsumer(ServiceName_T serviceName, Integer index, BaseMessageListener msgListener) throws BasicException {
        QueueName_T queueName = ServiceQueueMapper.getQueueName(serviceName, index);
        BaseQueue queue = ImqMessageService.getInstance().getQueue(queueName);
        queue.removeQueueReceiver(msgListener); 
    }
    
    public static void addTopicSubscriber(TopicName_T topicName, BaseMessageListener msgListener) throws BasicException {
        BaseTopic topic = ImqMessageService.getInstance().getTopic(topicName);
        topic.addTopicSubscriber(msgListener, null);
    }  
    
    public static void addTopicSubscriber(TopicName_T topicName, String mesgSelector, BaseMessageListener msgListener) throws BasicException {
        BaseTopic topic = ImqMessageService.getInstance().getTopic(topicName);
        if(topic != null)
        	topic.addTopicSubscriber(msgListener, mesgSelector);
    } 
    
    public static void removeTopicSubscriber(TopicName_T topicName, BaseMessageListener msgListener) throws BasicException {
        BaseTopic topic = ImqMessageService.getInstance().getTopic(topicName);
        if(topic != null)
        	topic.removeTopicSubscriber(msgListener);
    }  
}
