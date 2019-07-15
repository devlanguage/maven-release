
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TopicSubscriber;

import org.basic.common.bean.CommonLogger;
import org.third.jms.util.helper.ActiveMqMsgService;

public class JmsTopicListener implements MessageListener {

    private BaseMessageListener localMessageListener;
    private TopicSubscriber topicSubscriber;
    private String selector;
    final static CommonLogger logger = CommonLogger.getLogger(JmsTopicListener.class);

    JmsTopicListener(RemoteTopic nbiTopic, String mesgSelector, BaseMessageListener localMessageListener)
            throws Exception {

        this.localMessageListener = localMessageListener;
        topicSubscriber = ActiveMqMsgService.getInstance().getTopicConsumer(nbiTopic, mesgSelector);
        topicSubscriber.setMessageListener(this);
        selector = mesgSelector;
    }

    public void setTopicSubscriber(TopicSubscriber subscriber) throws JMSException {

        topicSubscriber = subscriber;
        topicSubscriber.setMessageListener(this);
    }

    public String getSelector() {

        return this.selector;
    }

    void close() {

        try {
            topicSubscriber.close();
        } catch (Exception ex) {
            logger.log(CommonLogger.ERROR, "Message Service", "", ex);
        }
    }

    public void onMessage(Message message) {

        try {
            message.acknowledge();

            NbiBody body = new NbiBody();
            body.setMessageData(((ObjectMessage) message).getObject());
            NbiHeader header = new NbiHeader();
            NbiMessage nbiMessage = new NbiMessage(header, body);
            // NbiMessage nbiMessage = (NbiMessage)((ObjectMessage)message).getObject();
            if (logger.isDebugEnabled()) {
                String msg = "Message received by Primary topic listener " + ((ObjectMessage) message).getObject();
                logger.log(CommonLogger.DEBUG, "Message Service", msg);
            }
            localMessageListener.onMessage(nbiMessage);
        } catch (Exception ex) {
            logger.log(CommonLogger.ERROR, "Message Service", "", ex);
        }
    }
}