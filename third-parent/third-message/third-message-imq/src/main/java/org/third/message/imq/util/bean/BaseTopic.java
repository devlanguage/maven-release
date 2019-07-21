package org.third.message.imq.util.bean;

import org.basic.common.bean.BooleanLock;
import org.basic.common.bean.CommonLogger;
import org.basic.common.util.BasicException;

/**
 * This class provides default abstract implementation of JMS topic. It has 2 concrete
 * implementations - NbiLocalTopic (Inmemory Topic implementation) and NbiRemoteTopic (JMS Topic
 * wrapper). see NbiMessageTest for an example
 */
public abstract class BaseTopic {

    private TopicName_T topicName;
    protected CommonLogger logger;
    public BooleanLock topicFlag;

    public BaseTopic(TopicName_T topicName, CommonLogger _logger) {

        this.topicName = topicName;
        this.logger = _logger;
        topicFlag = new BooleanLock(true);
    }

    public boolean getTopicFlag() {

        if (topicFlag.isTrue()) {
            return true;
        }
        return false;
    }

    public abstract int getMsgSize();

    public abstract int getConsumerSize();

    public abstract void publish(NbiMessage nbiMessage) throws BasicException;

    /**
     * Null msgSelector means no filtering. all the messages will be delivered LocalTopic does not
     * support message filtering
     */
    public abstract void addTopicSubscriber(BaseMessageListener nbiMsgListener, String mesgSelector)
            throws BasicException;

    public abstract void removeTopicSubscriber(BaseMessageListener nbiMsgListener);

    public TopicName_T getTopicName() {

        return topicName;
    }
}
