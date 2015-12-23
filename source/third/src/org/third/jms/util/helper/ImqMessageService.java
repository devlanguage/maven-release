package org.third.jms.util.helper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

import org.basic.common.bean.CommonLogger;
import org.third.jms.util.bean.BaseQueue;
import org.third.jms.util.bean.BaseTopic;
import org.third.jms.util.bean.DestinationType_T;
import org.third.jms.util.bean.ImqBooleanLock;
import org.third.jms.util.bean.JMSMetricListener;
import org.third.jms.util.bean.LocalQueue;
import org.third.jms.util.bean.LocalTopic;
import org.third.jms.util.bean.QueueName_T;
import org.third.jms.util.bean.RemoteQueue;
import org.third.jms.util.bean.RemoteTopic;
import org.third.jms.util.bean.ServiceName_T;
import org.third.jms.util.bean.TopicName_T;

import com.sun.messaging.ConnectionConfiguration;

/**
 * This class a singleton messaging manager instance. It maintains Queues/Topics
 * instances and manages connections/sessions to JMS Broker
 */
public class ImqMessageService {
	
    private QueueConnection queueConnection = null;
    private TopicConnection topicConnection = null;
    private String userName = "";
    private String host = "";
    private String port = "";
    private Map<QueueName_T, BaseQueue> queuesMap = new HashMap<QueueName_T, BaseQueue>();
    private Map<TopicName_T, BaseTopic> topicsMap = new HashMap<TopicName_T, BaseTopic>();
    private static ImqMessageService instance = null;
    Vector<RemoteQueue> queuesMonitored = new Vector<RemoteQueue>();
    Vector<RemoteTopic> topicsMonitored = new Vector<RemoteTopic>();
    ImqBooleanLock jmsFlag = new ImqBooleanLock(true);
    RemoteQueue queue = null;
    RemoteTopic topic = null;
    ImqBooleanLock reconnectFlag = new ImqBooleanLock(false);
    CommonLogger logger = CommonLogger.getLogger(ImqMessageService.class);
    private JMSMonitorListener listener = new JMSMonitorListener();
    int reconnectInterval = 10;
    private JMSMetricListener metricListener = new JMSMetricListener();
    
    public static synchronized ImqMessageService getInstance() {
    	if(instance == null){
    		instance = new ImqMessageService();
    	}
        return instance;
    }
    
    private ImqMessageService() {
    	Configuration config = ConfigurationLoader.load(ConfigurationConstant.CONFIG_FILE_NAME);
    	if(config != null){
    		String interval = config.getValue(ConfigurationConstant.MSG_RECONNECT_INTERVAL);
    		if(interval != null){
    			try{
    				reconnectInterval = Integer.parseInt(interval);
    			} catch(Exception e){
    				logger.log(CommonLogger.ERROR, "NbiMessagingService", 
    						"Error occurred on reading " + ConfigurationConstant.MSG_RECONNECT_INTERVAL);
    			}
    		}
    	}
    }
    	
	void resetNbiQueue() {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				int count = 0;
				while (jmsFlag.isTrue() && count < queuesMonitored.size()) {
					try {
						queue = queuesMonitored.get(count++);
						queue.queueFlag.setValue(false);
						restartNbiRemoteQueue(queue);
					} catch (JMSException e) {
						logger.log(CommonLogger.ERROR, "QueueMonitorThread", "", e);
						count--;
						continue;
					} catch (Exception e) {
						logger.log(CommonLogger.ERROR, "QueueMonitorThread", "", e);
						count--;
						continue;
					} finally {
						try {
							Thread.sleep(reconnectInterval * 1000);
						} catch (Exception e) {

						}
					}
				}
				startMetricTopic();
			}
		});
		thread.start();
	}
	
	void resetNbiTopic(){
		Thread thread = new Thread(new Runnable() {
			public void run() {
				int count = 0;
				while (jmsFlag.isTrue() && count < topicsMonitored.size()) {
					try {
						topic = topicsMonitored.get(count++);
						topic.topicFlag.setValue(false);
						restartNbiRemoteTopic(topic);
					} catch (JMSException e) {
						logger.log(CommonLogger.ERROR, "TopicMonitorThread", "", e);
						count--;
						continue;
					} catch (Exception e) {
						logger.log(CommonLogger.ERROR, "TopicMonitorThread", "", e);
						count--;
						continue;
					} finally {
						try {
							Thread.sleep(reconnectInterval * 1000);
						} catch (Exception e) {

						}
					}
				}
			}
		});
		
		for (int i=0; i< topicsMonitored.size(); i++) {
			RemoteTopic remoteTopic = topicsMonitored.get(i);
			remoteTopic.topicFlag.setValue(false);
		}
		
		thread.start();
	}
	
	void restartNbiRemoteQueue(RemoteQueue queue) throws JMSException{
		logger.log(CommonLogger.INFO, "restartNbiRemoteQueue", "Begin to restart NbiRemoteQueue: " + queue.getQueueName());
		queue.setJmsQueue(createJmsQueue(queue.getQueueName()));
		queue.resetRemoteQueue();
		queue.queueFlag.setValue(true);
		logger.log(CommonLogger.INFO, "restartNbiRemoteQueue", "Successful to restart NbiRemoteQueue: " + queue.getQueueName());
	}
	
	void restartNbiRemoteTopic(RemoteTopic topic) throws JMSException{
		logger.log(CommonLogger.INFO, "restartNbiRemoteTopic", "Begin to restart NbiRemoteTopic: " + topic.getTopicName());
		topic.setJmsTopic(createJmsTopic(topic.getTopicName()));
		topic.resetRemoteTopic();
		topic.topicFlag.setValue(true);
		logger.log(CommonLogger.INFO, "restartNbiRemoteTopic", "Successful to restart NbiRemoteTopic: " + topic.getTopicName());
	}
	
	void reconnectJmsServer(){
		Thread thread = new Thread(new Runnable(){
			public void run(){
				logger.log(CommonLogger.INFO, "reconnectJmsServer", "Begin to reconnect with jms server.");
				while(jmsFlag.isFalse()){
					try{
						connectJmsServer();
						startBrokerConnections();
						jmsFlag.setValue(true);
						reconnectFlag.setValue(false);
						logger.log(CommonLogger.INFO, "reconnectJmsServer", "Successful to reconnect with jms server.");
						resetNbiQueue();
						resetNbiTopic();
					}catch(Exception e){
						logger.log(CommonLogger.ERROR, "reconnectJmsServer", "", e);
					}finally{
						try{
							Thread.sleep(reconnectInterval * 1000);
						}catch(Exception e){
							
						}
					}
				}
			}
		});
		thread.start();
	}
	
	void connectJmsServer() throws JMSException{
        com.sun.messaging.QueueConnectionFactory queueFactory = new com.sun.messaging.QueueConnectionFactory();
        com.sun.messaging.TopicConnectionFactory topicFactory = new com.sun.messaging.TopicConnectionFactory();
        queueFactory.setProperty(ConnectionConfiguration.imqAddressList,host);
        topicFactory.setProperty(ConnectionConfiguration.imqAddressList,host);
//        queueFactory.setProperty("imqBrokerHostName", host);
//        queueFactory.setProperty("imqBrokerHostPort", port);
//        topicFactory.setProperty("imqBrokerHostName", host);
//        topicFactory.setProperty("imqBrokerHostPort", port);

        queueConnection = queueFactory.createQueueConnection();
        topicConnection = topicFactory.createTopicConnection();
        queueConnection.setExceptionListener(listener);
        topicConnection.setExceptionListener(listener);
	}
	
    public void setupConnections(String brokerHost, String brokerPort, String userName ) throws JMSException {
    	this.host = brokerHost;
    	this.port = brokerPort;
        this.userName = userName;
        connectJmsServer();
    }
    
    public void startBrokerConnections() throws JMSException {
        queueConnection.start();
        topicConnection.start();
    }
    
    void startMetricTopic() {
    	try{
    		TopicSession metricSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
    		if (metricSession != null) {
    			for(int i=0; i<queuesMonitored.size(); i++){
    				RemoteQueue queue = queuesMonitored.get(i);
    				String queueName = queue.getJmsQueue().getQueueName();
    				Topic metricTopic = metricSession.createTopic("mq.metrics.destination.queue." + queueName);
    				TopicSubscriber subscriber = metricSession.createSubscriber(metricTopic);
    				metricListener.addQueue(queue);
    				subscriber.setMessageListener(metricListener);
    			}
    			
    			for(int i=0; i<topicsMonitored.size(); i++){
    				RemoteTopic topic = topicsMonitored.get(i);
    				String topicName = topic.getJmsTopic().getTopicName();
    				Topic metricTopic = metricSession.createTopic("mq.metrics.destination.topic." + topicName);
    				TopicSubscriber subscriber = metricSession.createSubscriber(metricTopic);
    				metricListener.addTopic(topic);
    				subscriber.setMessageListener(metricListener);
    			}
    		}
    	}catch(JMSException e){
    		logger.log(CommonLogger.ERROR, "startMetricTopic", "", e);
    	}
    }
    
    public void stopMessageQueue() {
    	jmsFlag.setValue(false);
    	synchronized(queuesMap){
    		if(queuesMap != null){
        		Iterator it = queuesMap.values().iterator();
        		while(it.hasNext()){
        			((BaseQueue)it.next()).release();
        		}
    		}
    	}
    }
    
    public void stopBrokerConnections() throws JMSException {
    	if(queueConnection != null)
    		queueConnection.close();
    	if(topicConnection != null)
        topicConnection.close();
    }
    
    public QueueSession getQueueSession() throws JMSException {
        return queueConnection.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE);
    }
    
    public TopicSession getTopicSession() throws JMSException {
        return topicConnection.createTopicSession(false, Session.CLIENT_ACKNOWLEDGE);
    }
    
    protected QueueSender getQueueSender(RemoteQueue queue) throws JMSException {
        QueueSession session = getQueueSession();
        return session.createSender(queue.getJmsQueue());
    }
    
    protected QueueSender getQueueSender(Queue queue) throws JMSException {
        QueueSession session = getQueueSession();
        return session.createSender(queue);
    }
    
    public QueueReceiver getQueueReceiver(RemoteQueue queue) throws JMSException {
        QueueSession session = getQueueSession();
        return session.createReceiver(queue.getJmsQueue());
    }
    
    protected TopicSubscriber getTopicConsumer(RemoteTopic topic) throws JMSException {
        TopicSession session = getTopicSession();
        return session.createSubscriber(topic.getJmsTopic());
    }
    
    public TopicSubscriber getTopicConsumer(RemoteTopic topic, String msgSelector) throws JMSException {
        TopicSession session = getTopicSession();
        return session.createSubscriber(topic.getJmsTopic(), msgSelector, false);
    }
    
    protected TopicPublisher getTopicPublisher(RemoteTopic topic) throws JMSException {
        TopicSession session = getTopicSession();
        return session.createPublisher(topic.getJmsTopic());
    }
    
    public void createQueue(QueueName_T queueName, CommonLogger logger) throws JMSException {
        BaseQueue baseQueue = queuesMap.get(queueName);
        if (baseQueue == null) {
            if (queueName.getDestinationType().compareTo(DestinationType_T.REMOTE) == 0) {
                baseQueue = new RemoteQueue(queueName, logger);
                ((RemoteQueue)baseQueue).setJmsQueue(createJmsQueue(queueName));
                if(!queuesMonitored.contains(baseQueue)) {
                	queuesMonitored.add((RemoteQueue)baseQueue);
                }
            } else {
                baseQueue = new LocalQueue(queueName, logger);
            }
            queuesMap.put(queueName, baseQueue);
        }
    }
    
    public int getQueueSize(ServiceName_T serviceName, Integer index) {
    	QueueName_T queueName = ServiceQueueMapper.getQueueName(serviceName, index);
    	BaseQueue baseQueue = queuesMap.get(queueName);
    	if(baseQueue != null){
    		return baseQueue.getQueueSize();
    	}
    	return -1;
    }
    
	public void setQueueResetFlag(ServiceName_T serviceName, Integer index, boolean flag){
    	QueueName_T queueName = ServiceQueueMapper.getQueueName(serviceName, index);
    	BaseQueue baseQueue = queuesMap.get(queueName);
    	if(baseQueue instanceof RemoteQueue) {
    		((RemoteQueue)baseQueue).setResetFlag(flag);
    	}
	}
    
    public void createTopic(TopicName_T topicName, CommonLogger logger) throws JMSException {
        BaseTopic baseTopic = topicsMap.get(topicName);
        if (baseTopic == null) {
            if (topicName.getDestinationType().compareTo(DestinationType_T.REMOTE) == 0) {
                baseTopic = new RemoteTopic(topicName, logger);
                ((RemoteTopic)baseTopic).setJmsTopic(createJmsTopic(topicName));
                if(!topicsMonitored.contains(baseTopic)){
                	topicsMonitored.add((RemoteTopic)baseTopic);
                }
            } else {
                baseTopic = new LocalTopic(topicName, logger);
            }
            topicsMap.put(topicName, baseTopic);
        }
    }
    
    public BaseQueue getQueue(QueueName_T queue) {
        return queuesMap.get(queue);
    }
    
    public BaseTopic getTopic(TopicName_T topic) {
        return topicsMap.get(topic);
    }
    
    public Queue createJmsQueue(QueueName_T queueName) throws JMSException {
      QueueSession session = queueConnection.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE);
      String queueNameStr = userName + "_" + queueName.toString();
      return session.createQueue(queueNameStr);
    }

    private Topic createJmsTopic(TopicName_T name) throws JMSException {
        TopicSession session = topicConnection.createTopicSession(false, Session.CLIENT_ACKNOWLEDGE);
        String topicName = userName + "_" + name;
        return session.createTopic(topicName);
    }
    
    boolean getJMSServerStatus(){
    	if(jmsFlag.isTrue()){
    		return true;
    	}
    	return false;
    }
    
    Map<QueueName_T, BaseQueue> getAllNbiQueue(){
    	return queuesMap;
    }
    
    Map<TopicName_T, BaseTopic> getAllNbiTopic(){
    	return topicsMap;
    }
    
    class JMSMonitorListener implements ExceptionListener{
    	public void onException(JMSException e){
    		if(reconnectFlag.isFalse()){
    			jmsFlag.setValue(false);
    			reconnectFlag.setValue(true);
    			reconnectJmsServer();
    		}
    	}
    }
}
