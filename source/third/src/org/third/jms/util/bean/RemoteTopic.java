
import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

import org.basic.common.bean.CommonLogger;
import org.basic.common.util.BasicException;
import org.third.jms.util.NbiMessageTest;
import org.third.jms.util.helper.ActiveMqMsgService;

/**
 * This class provides a wrapper of JMS Topic implementation.
 * 
 * @see NbiMessageTest for example
 */
public class RemoteTopic extends BaseTopic {

    private static final int TIME_TO_LIVE = 300000;

    private int msgSize = 0;

    private int consumerSize = 0;

    protected Topic jmsTopic;

    // There is one session/publisher per topic for publishing
    // messages
    private TopicSession publishSession;
    private TopicPublisher topicPublisher;

    // Maintains a map between JMS listener and local listener
    private Map<BaseMessageListener, JmsTopicListener> baseMessageListeners = new HashMap<BaseMessageListener, JmsTopicListener>();

    public RemoteTopic(TopicName_T topicName, CommonLogger logger) {

        super(topicName, logger);
    }

    public void setJmsTopic(Topic jmsTopic) {

        this.jmsTopic = jmsTopic;
    }

    public Topic getJmsTopic() {

        return jmsTopic;
    }

    public void setMsgSize(int size) {

        msgSize = size;
    }

    public int getMsgSize() {

        return msgSize;
    }

    public void setConsumerSize(int size) {

        consumerSize = size;
    }

    public int getConsumerSize() {

        return consumerSize;
    }

    public synchronized void publish(NbiMessage message) throws BasicException {

        try {
            if (topicFlag.isTrue()) {
                initPublishTopic();

                Object msgData = message.getBody().getMessageData();
                ObjectMessage jmsMessage = null;

                jmsMessage = publishSession.createObjectMessage((Serializable) msgData);

                Properties msgProps = message.getHeader().getMsgProperties();
                if (msgProps != null) {
                    Enumeration enumProps = msgProps.propertyNames();
                    while (enumProps.hasMoreElements()) {
                        String nextName = (String) enumProps.nextElement();
                        jmsMessage.setStringProperty(nextName, msgProps.getProperty(nextName));
                    }
                }

                topicPublisher.publish(jmsMessage, DeliveryMode.NON_PERSISTENT, javax.jms.Message.DEFAULT_PRIORITY,
                        TIME_TO_LIVE);

                if (logger.isDebugEnabled()) {
                    String msg = "Message Published to " + getTopicName() + " " + message.getHeader().getMessageId();
                    logger.log(CommonLogger.DEBUG, "publish", msg);
                }
            }
        } catch (javax.jms.IllegalStateException ex) {
            logger.log(CommonLogger.ERROR, "publish", "", ex);
            if (!ex.getErrorCode().equals("C4059")) {
                throw new BasicException(BasicException.BasicExceptionType.COMMUNICATION_EXCEPTION, ex);
            } else {
                topicFlag.setValue(false);
            }
        } catch (Exception ex) {
            logger.log(CommonLogger.ERROR, "publish", "", ex);
            throw new BasicException(BasicException.BasicExceptionType.COMMUNICATION_EXCEPTION, ex);
        }
    }

    /**
     * Null msgSelector means no filtering. all the messages will be delivered LocalTopic does not
     * support message filtering
     */
    public void addTopicSubscriber(BaseMessageListener nbiMsgListener, String mesgSelector) throws BasicException {

        try {
            JmsTopicListener jmsTopicListener = new JmsTopicListener(this, mesgSelector, nbiMsgListener);
            baseMessageListeners.put(nbiMsgListener, jmsTopicListener);
        } catch (Exception ex) {
            logger.log(CommonLogger.ERROR, "Message Service", "", ex);
            throw new BasicException(BasicException.BasicExceptionType.COMMUNICATION_EXCEPTION, ex);
        }
    }

    public void removeTopicSubscriber(BaseMessageListener nbiMsgListener) {

        try {
            JmsTopicListener jmsTopicListener = baseMessageListeners.remove(nbiMsgListener);
            if (jmsTopicListener != null)
                jmsTopicListener.close();
        } catch (Exception ex) {
            logger.log(CommonLogger.ERROR, "Message Service", "", ex);
        }
    }

    private synchronized void initPublishTopic() throws JMSException {

        if (publishSession == null) {
            publishSession = ActiveMqMsgService.getInstance().getTopicSession();
        }
        if (topicPublisher == null) {
            topicPublisher = publishSession.createPublisher(jmsTopic);
        }
    }

    public void resetRemoteTopic() throws JMSException {

        publishSession = null;
        topicPublisher = null;
        initPublishTopic();
        resetTopicListener();
        topicFlag.setValue(true);
    }

    private void resetTopicListener() throws JMSException {

        Iterator<JmsTopicListener> it = baseMessageListeners.values().iterator();
        while (it.hasNext()) {
            JmsTopicListener listener = it.next();
            TopicSubscriber subscriber = ActiveMqMsgService.getInstance().getTopicConsumer(this,
                    listener.getSelector());
            listener.setTopicSubscriber(subscriber);
        }
    }

    
}