package org.third.message.imq.util.bean;

import java.util.Vector;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.basic.common.bean.CommonLogger;

public class JMSMetricListener implements MessageListener{

	private Vector<RemoteQueue> queueVect = new Vector<RemoteQueue>();
	private Vector<RemoteTopic> topicVect = new Vector<RemoteTopic>();
	private CommonLogger logger = CommonLogger.getLogger(JMSMetricListener.class);
	
	private void processMsg(MapMessage mapMsg){
		try {
			String type = mapMsg.getStringProperty("type");
			if(type.contains("destination.queue")){
				RemoteQueue queue = getQueue(type);
				if(queue != null){
					int numMsg = mapMsg.getInt("numMsgs");
					if(numMsg >= 0){
						queue.setQueueSize(numMsg);
					}
				}
			} else if(type.contains("destination.topic")){
				RemoteTopic topic = getTopic(type);
				if(topic != null){
					int numMsg = mapMsg.getInt("numMsgs");
					if(numMsg >= 0){
						topic.setMsgSize(numMsg);
					}
					
					int numConsumer = mapMsg.getInt("numActiveConsumers");
					if(numConsumer >= 0){
						topic.setConsumerSize(numConsumer);
					}
				}
			}
		} catch(JMSException e){
			logger.log(CommonLogger.ERROR, "processMsg", "", e);
		} catch(Exception e){
			logger.log(CommonLogger.ERROR, "processMsg", "", e);
		}
	}
	
	public void addQueue(RemoteQueue queue){
		if(!queueVect.contains(queue)){
			queueVect.add(queue);
		}
	}
	
	public RemoteQueue getQueue(String queueName) throws JMSException {
		for(int i=0; i<queueVect.size(); i++){
			RemoteQueue queue = queueVect.get(i);
			if(queueName.contains(queue.getJmsQueue().getQueueName())){
				return queue;
			}
		}
		return null;
	}
	
	public void addTopic(RemoteTopic topic){
		if(!topicVect.contains(topic)){
			topicVect.add(topic);
		}
	}
	
	public RemoteTopic getTopic(String topicName) throws JMSException {
		for(int i=0; i<topicVect.size(); i++){
			RemoteTopic topic = topicVect.get(i);
			if(topicName.contains(topic.getJmsTopic().getTopicName())){
				return topic;
			}
		}
		return null;
	}
	
	public void onMessage(Message msg){
		if(msg instanceof MapMessage){
			processMsg((MapMessage)msg);
		}
	}
}
