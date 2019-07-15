
import java.util.AbstractQueue;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.basic.common.bean.CommonLogger;
import org.basic.common.util.BasicException;
import org.third.jms.util.helper.MessageLock;


/**
 * Inmemory JMS topic implementation
 */
public class LocalTopic extends BaseTopic {

    // Stores NBI Message   
    private AbstractQueue<NbiMessage> nbiTopicQueue = 
        new ConcurrentLinkedQueue<NbiMessage>();

    // Monitor used to wake up subscriber thread
    private MessageLock msgSubscriberLock = new MessageLock();

    // Single subscriber thread per topic
    private SubscriberThread primarySubscriberThread;
    
    private Set<BaseMessageListener> baseMessageListeners = new HashSet<BaseMessageListener>();
    private final int TIME_OUT = 60000;
    
    public LocalTopic(TopicName_T topicName, CommonLogger logger) {
        super(topicName, logger);
    }
    
    public int getMsgSize(){
    	return nbiTopicQueue.size();
    }
    
    public int getConsumerSize(){
    	return baseMessageListeners.size();
    }

    public void publish(NbiMessage message) throws BasicException {
        try {
            msgSubscriberLock.lock();
            nbiTopicQueue.add(message);
            msgSubscriberLock.signal();
            String msg = "Message Published to " + getTopicName() + " " + 
            			message.getHeader().getMessageId();
            if (logger.isDebugEnabled()){
            	logger.log(CommonLogger.DEBUG, "publish", msg);
            }
        } finally {
            msgSubscriberLock.unlock();
        }
    }

    private synchronized void startSubscriber() {
        if (primarySubscriberThread == null) {
            primarySubscriberThread = new SubscriberThread(this);
            primarySubscriberThread.start();
        }
    }

    /**
      Null msgSelector means no filtering. all the messages will be delivered
      LocalTopic does not support message filtering
    **/
    public void addTopicSubscriber(BaseMessageListener nbiMsgListener, String mesgSelector) 
    		throws BasicException {
        startSubscriber();
        baseMessageListeners.add(nbiMsgListener);
    }
    
    public void removeTopicSubscriber(BaseMessageListener nbiMsgListener) {
        baseMessageListeners.remove(nbiMsgListener);
    }
    
    protected void notifyListeners(NbiMessage message) {
        for (BaseMessageListener listener : baseMessageListeners) {
            listener.onMessage(message);
        }
    }
    
    private class SubscriberThread extends Thread {
        BaseTopic baseTopic;

        SubscriberThread(BaseTopic baseTopic) {
            super("PRIMARY_SUBSCRIBER_" + baseTopic.getTopicName());
        }

        public void run() {
            while (true) {
                try {
                    msgSubscriberLock.lock();
                    if (nbiTopicQueue.peek() != null) {
                        NbiMessage msg = nbiTopicQueue.remove();
                        String message = "NbiLocalTopic : SubscriberThread " + getTopicName() + 
                        				" NbiMessage - " + msg;
                        notifyListeners(msg);
                        continue;
                    }
                    msgSubscriberLock.await(TIME_OUT);
                } catch (InterruptedException iex) {
                    logger.log(CommonLogger.ERROR, "Message Service", "", iex);
                } finally {
                    msgSubscriberLock.unlock();
                }
            }
        }
    }
}
