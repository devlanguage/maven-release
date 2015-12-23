package org.third.jms.util;

import org.basic.common.util.BasicException;
import org.third.jms.util.bean.BaseQueue;
import org.third.jms.util.bean.BaseTopic;
import org.third.jms.util.bean.NbiHeader;
import org.third.jms.util.bean.NbiMessage;
import org.third.jms.util.bean.QueueName_T;
import org.third.jms.util.bean.ServiceName_T;
import org.third.jms.util.bean.TopicName_T;
import org.third.jms.util.helper.ActiveMqMsgService;
import org.third.jms.util.helper.ServiceQueueMapper;


/**
 * NbiMessaging Layer Facade class to be used for sending messages
 * to queues or topics
 */
public class NbiMessageSender {
    
    public static NbiMessage sendReceive(NbiMessage nbiMessage) throws BasicException {
        BaseQueue queue = getQueue(nbiMessage);
        NbiMessage nbiReplyMessage = queue.sendReceive(nbiMessage);
       
        return nbiReplyMessage;
    }

	private static BaseQueue getQueue(NbiMessage nbiMessage) {
		NbiHeader header = nbiMessage.getHeader();
        ServiceName_T serviceName = header.getServiceName();
        int index = header.getQueueNo();
        QueueName_T queueName = ServiceQueueMapper.getQueueName(serviceName, index);     
        BaseQueue queue = ActiveMqMsgService.getInstance().getQueue(queueName);
		return queue;
	}
    
    public static void send(NbiMessage nbiMessage) throws BasicException {
        BaseQueue queue = getQueue(nbiMessage);
        queue.send(nbiMessage);
     }
    
    public static void replyTo(NbiMessage orgNbiMessage, NbiMessage replyMessage) throws BasicException {
        BaseQueue queue = getQueue(orgNbiMessage);
        queue.replyTo(orgNbiMessage, replyMessage);
    }
    
    public static void publish(TopicName_T topicName, NbiMessage nbiMessage) throws BasicException {
        BaseTopic topic = ActiveMqMsgService.getInstance().getTopic(topicName);
        if(topic != null)
        	topic.publish(nbiMessage);
    }
}
