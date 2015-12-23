package org.third.jms.util.bean;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TemporaryQueue;

import org.basic.common.bean.CommonLogger;
import org.basic.common.util.BasicException;
import org.third.jms.util.helper.ActiveMqMsgService;
import org.third.jms.util.helper.BooleanLock;

/**
 * JMS queue wrapper class
 */
public class RemoteQueue extends BaseQueue {

    // JMS Queue
    private Queue jmsQueue;

    private int queueSize = 0;

    // Temporary queue for reply messags
    TemporaryQueue replyQueue;

    // Thread listening on reply queue
    private ReplyQueueReceiverThread replyReceiverThread;

    // Message is deleted from the queue after 5 mins if nobody picks up
    private static final int TIME_TO_LIVE = 300000;

    // Single instance of receiver per queue used on the receiver side
    private QueueReceiver queueReceiver;

    // Single instance of send session/sender per queues used to send messages on
    // the sender side and used for replyTo on the receiver side
    private QueueSession sendSession;
    private QueueSender queueSender;

    private BooleanLock resetFlag = new BooleanLock(true);

    public RemoteQueue(QueueName_T queueName, CommonLogger logger) {

        super(queueName, logger);
    }

    public NbiMessage sendReceive(NbiMessage nbiMessage) throws BasicException {

        if (logger.isDebugEnabled()) {
            String msg = "NbiRemoteQueue : sendReceive " + " NbiMessage - " + nbiMessage;
            logger.log(CommonLogger.DEBUG, "Message Service", msg);
        }

        try {
            initSendQueue();
            return super.sendReceive(nbiMessage);
        } catch (Exception ex) {
            logger.log(CommonLogger.ERROR, "sendReceive", "", ex);
            throw new BasicException(BasicException.BasicExceptionType.COMMUNICATION_EXCEPTION, ex);
        }
    }

    public synchronized void send(NbiMessage nbiMessage) throws BasicException {

        if (logger.isDebugEnabled()) {
            String msg = "NbiRemoteQueue : send " + " NbiMessage - " + nbiMessage;
            logger.log(CommonLogger.DEBUG, "Message Service", msg);
        }

        try {
            initSendQueue();
            ObjectMessage jmsMessage = sendSession.createObjectMessage(nbiMessage);
            if (replyQueue != null) {
                jmsMessage.setJMSReplyTo(replyQueue);
            }

            queueSender.send(jmsQueue, jmsMessage, DeliveryMode.NON_PERSISTENT, javax.jms.Message.DEFAULT_PRIORITY,
                    TIME_TO_LIVE);

        } catch (Exception ex) {
            logger.log(CommonLogger.ERROR, "send", "", ex);
            throw new BasicException(BasicException.BasicExceptionType.COMMUNICATION_EXCEPTION, ex);
        }
    }

    public synchronized void replyTo(NbiMessage orgMessage, NbiMessage replyMessage) throws BasicException {

        if (logger.isDebugEnabled()) {
            logger.log(CommonLogger.DEBUG, "replyTo", "NbiRemoteQueue : replyTo");
        }

        try {
            initSendQueue();
            Queue replyDest = (Queue) orgMessage.getHeader().getJmsReplyTo();
            replyMessage.getHeader().setCorrelationId(orgMessage.getHeader().getMessageId());
            ObjectMessage respMessage = sendSession.createObjectMessage(replyMessage);

            queueSender.send(replyDest, respMessage, DeliveryMode.NON_PERSISTENT, javax.jms.Message.DEFAULT_PRIORITY,
                    TIME_TO_LIVE);

            if (logger.isDebugEnabled()) {
                String msg = "Message ReplyTo  " + getQueueName() + " " + replyMessage.getHeader().getMessageId();
                logger.log(CommonLogger.DEBUG, "replyTo", msg);
            }
        } catch (Exception ex) {
            logger.log(CommonLogger.ERROR, "replyTo", "", ex);
            throw new BasicException(BasicException.BasicExceptionType.COMMUNICATION_EXCEPTION, ex);
        }
    }

    public void setJmsQueue(Queue jmsQueue) {

        this.jmsQueue = jmsQueue;
    }

    public Queue getJmsQueue() {

        return jmsQueue;
    }

    public int getQueueSize() {

        return queueSize;
    }

    public void setQueueSize(int size) {

        queueSize = size;
    }

    public NbiMessage recv() throws BasicException {

        try {
            NbiMessage nbiMessage = null;
            initRecvQueue();
            Message jmsMessage = queueReceiver.receive();
            if (jmsMessage != null) {
                jmsMessage.acknowledge();

                ObjectMessage object = (ObjectMessage) jmsMessage;
                nbiMessage = (NbiMessage) object.getObject();
                nbiMessage.getHeader().setJmsReplyTo(jmsMessage.getJMSReplyTo());
            }
            return nbiMessage;
        } catch (JMSException ex) {
            queueFlag.setValue(false);
            String info = "RevThread exit with error occurred.";
            logger.log(CommonLogger.ERROR, "ReplyQueueReceiverThread", info);
            logger.log(CommonLogger.ERROR, "ReplyQueueReceiverThread", "", ex);
            throw new BasicException(BasicException.BasicExceptionType.COMMUNICATION_EXCEPTION, ex);
        } catch (Exception ex) {
            logger.log(CommonLogger.ERROR, "recv", "", ex);
            throw new BasicException(BasicException.BasicExceptionType.COMMUNICATION_EXCEPTION, ex);
        }
    }

    public synchronized void resetRemoteQueue() throws JMSException {

        sendSession = null;
        queueSender = null;
        replyQueue = null;
        queueReceiver = null;
        receiverThread = null;
        if (resetFlag.isTrue()) {
            initRecvQueue();
            startReceiverThread();
        } else {
            initSendQueue();
        }
    }

    /**
     * indicate this message service is in nbi server or in web server flag=true -- nbi server
     * flag=false -- web server
     * 
     * @param flag
     */
    public void setResetFlag(boolean flag) {

        resetFlag.setValue(flag);
    }

    private synchronized void initSendQueue() throws JMSException {

        if (sendSession == null) {
            sendSession = ActiveMqMsgService.getInstance().getQueueSession();
        }
        if (queueSender == null) {
            queueSender = sendSession.createSender(null);
        }
        if (replyQueue == null) {
            replyQueue = sendSession.createTemporaryQueue();
            replyReceiverThread = new ReplyQueueReceiverThread(this);
            replyReceiverThread.start();
        }
    }

    private synchronized void initRecvQueue() throws JMSException {

        if (sendSession == null) {
            sendSession = ActiveMqMsgService.getInstance().getQueueSession();
        }
        if (queueSender == null) {
            queueSender = sendSession.createSender(null);
        }
        if (queueReceiver == null) {
            queueReceiver = ActiveMqMsgService.getInstance().getQueueReceiver(this);
        }
    }

    private class ReplyQueueReceiverThread extends Thread {

        private RemoteQueue remoteQueue;

        ReplyQueueReceiverThread(RemoteQueue nbiQueue) {

            super("REPLY_RECEIVER_" + nbiQueue.getQueueName());
            remoteQueue = nbiQueue;
        }

        public void run() {

            try {
                QueueSession replyQueueSession = ActiveMqMsgService.getInstance().getQueueSession();
                QueueReceiver replyQueueReceiver = replyQueueSession.createReceiver(replyQueue);

                while (queueFlag.isTrue()) {
                    try {
                        ObjectMessage message = (ObjectMessage) replyQueueReceiver.receive();

                        if (message == null) {
                            continue;
                        }
                        message.acknowledge();
                        remoteQueue.replyReceived((NbiMessage) message.getObject());
                    } catch (JMSException ex) {
                        // queueFlag.setValue(false);
                        String info = "ReplyQueueReceiverThread " + getName() + "exit with error occurred.";
                        logger.log(CommonLogger.ERROR, "ReplyQueueReceiverThread", info);
                        logger.log(CommonLogger.ERROR, "ReplyQueueReceiverThread", "", ex);
                    } catch (Exception ex) {
                        logger.log(CommonLogger.ERROR, "ReplyQueueReceiverThread", "", ex);
                    }
                }
            } catch (Exception ex) {
                logger.log(CommonLogger.ERROR, "ReplyQueueReceiverThread", "", ex);
            }
        }
    }
}