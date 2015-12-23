package org.third.jms.util.bean;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.basic.common.bean.CommonLogger;
import org.basic.common.util.BasicException;
import org.third.jms.util.NbiMessageTest;
import org.third.jms.util.helper.BooleanLock;
import org.third.jms.util.helper.MessageLock;

/**
 * This class provides abstract default implementation of JMS Queue. Used for communication between
 * services. Has 2 concrete implementations - NbiLocalQueue (In memory Queue implementation) and
 * NbiRemoteQueue (JMS Broker Queue). On sender side it supports - sendReceive (blocked call) - send
 * On receiver side it supports - recv - replyTo
 * 
 * @see NbiMessageTest for example
 */
public abstract class BaseQueue {

    protected QueueName_T queueName;

    // Saves sender thread messageId and the monitor
    private Map<Long, MessageLock> senderMap = new ConcurrentHashMap<Long, MessageLock>();

    // Saves sender monitor and the reply message
    private Map<MessageLock, NbiMessage> replyMap = new ConcurrentHashMap<MessageLock, NbiMessage>();

    // Maintains list of listeners interested in receiving the message
    private Set<BaseMessageListener> baseMessageListeners = new HashSet<BaseMessageListener>();

    // Single thread receiving the message on the queue and notifying
    // one of the listeners
    protected ReceiverThread receiverThread;

    // Sender thread timesout in 1.5 mins if no reply is received
    private static final int MESSAGE_TIMOUT_SECS = 90;

    // used for close the ReceiverThread
    public BooleanLock queueFlag = null;
    protected CommonLogger logger = null;

    public BaseQueue(QueueName_T queueName, CommonLogger _logger) {

        this.queueName = queueName;
        queueFlag = new BooleanLock(true);
        this.logger = _logger;
    }

    abstract public int getQueueSize();

    public boolean getQueueStatus() {

        if (queueFlag.isFalse()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 
     * Blocked Send and Receive call
     * 
     * @param nbiMessage
     * @return
     * @throws BasicException
     */
    @SuppressWarnings("boxing")
    public NbiMessage sendReceive(NbiMessage nbiMessage) throws BasicException {

        if (logger.isDebugEnabled()) {
            String msg = "NbiQueue : sendReceive " + getQueueName() + " " + nbiMessage;
            logger.log(CommonLogger.DEBUG, "Message Service", msg);
        }

        MessageLock msgSenderLock = new MessageLock();

        try {
            msgSenderLock.lock();
            senderMap.put(nbiMessage.getHeader().getMessageId(), msgSenderLock);
            send(nbiMessage);
            msgSenderLock.await(MESSAGE_TIMOUT_SECS);
            NbiMessage nbiReplyMessage = replyMap.remove(msgSenderLock);

            if (nbiReplyMessage == null)
                throw new BasicException(BasicException.BasicExceptionType.COMMUNICATION_EXCEPTION,
                        BasicException.BasicExceptionSubType.MESSAGE_TIME_OUT);
            return nbiReplyMessage;
        }

        catch (InterruptedException ex) {
            senderMap.remove(nbiMessage.getHeader().getMessageId());
            logger.log(CommonLogger.ERROR, "sendReceive", "", ex);

            throw new BasicException(BasicException.BasicExceptionType.COMMUNICATION_EXCEPTION,
                    BasicException.BasicExceptionSubType.WAIT_INTERRUPTED);
        } finally {
            msgSenderLock.unlock();
        }
    }

    public abstract void send(NbiMessage response) throws BasicException;

    public abstract void replyTo(NbiMessage orgMessage, NbiMessage responseMsg) throws BasicException;

    public void addQueueReceiver(BaseMessageListener nbiMsgListener) {

        startReceiverThread();
        baseMessageListeners.add(nbiMsgListener);
    }

    public void removeQueueReceiver(BaseMessageListener nbiMsgListener) {

        if (nbiMsgListener != null && baseMessageListeners.contains(nbiMsgListener)) {
            baseMessageListeners.remove(nbiMsgListener);
        }
    }

    public abstract NbiMessage recv() throws BasicException;

    /**
     * Once reply is received on the sender side, this function notifies the waiting thread
     * 
     * @param response
     * @throws BasicException
     */
    @SuppressWarnings("boxing")
    protected void replyReceived(NbiMessage response) throws BasicException {

        if (logger.isDebugEnabled()) {
            String msg = "NbiQueue : replyReceived " + getQueueName() + " - NbiResponse " + response;
            logger.log(CommonLogger.DEBUG, "replyReceived", msg);
        }

        long msgId = response.getHeader().getCorrelationId();
        MessageLock msgSenderLock = senderMap.remove(msgId);
        if (msgSenderLock != null) {
            try {
                msgSenderLock.lock();
                replyMap.put(msgSenderLock, response);
                msgSenderLock.signal();
            } finally {
                msgSenderLock.unlock();
            }
        } else
            throw new BasicException(BasicException.BasicExceptionType.COMMUNICATION_EXCEPTION,
                    BasicException.BasicExceptionSubType.MESSAGE_UNDELIVERABLE);
    }

    public QueueName_T getQueueName() {

        return queueName;
    }

    // One of the listener is notified
    protected void notifyListeners(NbiMessage message) {

        for (BaseMessageListener listener : baseMessageListeners) {
            listener.onMessage(message);
            return;
        }
    }

    protected synchronized void startReceiverThread() {

        if (receiverThread == null) {
            receiverThread = new ReceiverThread(this);
            receiverThread.start();
        }
    }

    public void release() {

        queueFlag.setValue(false);
    }

    /**
     * One receiver thread per queue and once message is received it notifies one of listeners
     */
    private class ReceiverThread extends Thread {

        BaseQueue baseQueue;

        ReceiverThread(BaseQueue queue) {

            super("RECEIVER_" + queue.getQueueName());
            this.baseQueue = queue;
        }

        public void run() {

            while (queueFlag.isTrue()) {
                try {
                    // if(logger.isDebugEnabled()){
                    // String msg = "Receiver Thread on queue " + getName() + " is running";
                    // logger.log(NbiLogger.DEBUG, "ReceiverThread", msg);
                    // }

                    NbiMessage msgReceived = baseQueue.recv();
                    if (msgReceived != null) {
                        if (logger.isDebugEnabled()) {
                            String msg = "Received Message on Queue " + queueName + " NbiMessage " + msgReceived;
                            logger.log(CommonLogger.DEBUG, "ReceiverThread", msg);
                        }
                        baseQueue.notifyListeners(msgReceived);
                    }
                } catch (Exception ex) {
                    logger.log(CommonLogger.FATAL, "NbiQueue.ReceiverThread.run", "ReceiverThread of Nbi Queue ["
                            + queueName + "] crashed!", ex);
                }
            }
        }
    }
}
