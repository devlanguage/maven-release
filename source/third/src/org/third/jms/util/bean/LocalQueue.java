
import java.util.AbstractQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.basic.common.bean.CommonLogger;
import org.basic.common.util.BasicException;
import org.third.jms.util.helper.MessageLock;

/**
 * In-memory queue
 */
public class LocalQueue extends BaseQueue {

    // NbiMessage storage queue
    private AbstractQueue<NbiMessage> nbiQueue = new ConcurrentLinkedQueue<NbiMessage>();

    // Monitor used to wake up receiver thread
    private MessageLock msgReceiverLock = new MessageLock();

    public LocalQueue(QueueName_T queueName, CommonLogger logger) {

        super(queueName, logger);
    }

    public int getQueueSize() {

        return nbiQueue.size();
    }

    public AbstractQueue<NbiMessage> getQueue() {

        return nbiQueue;
    }

    public void send(NbiMessage message) throws BasicException {

        try {
            msgReceiverLock.lock();
            nbiQueue.add(message);
            msgReceiverLock.signal();
        } finally {
            msgReceiverLock.unlock();
        }
    }

    public void replyTo(NbiMessage originalMsg, NbiMessage resp) throws BasicException {

        resp.getHeader().setCorrelationId(originalMsg.getHeader().getMessageId());
        super.replyReceived(resp);
        if (logger.isDebugEnabled()) {
            String msg = "Message ReplyTo  " + getQueueName() + " " + resp.getHeader().getMessageId();
            logger.log(CommonLogger.DEBUG, "Message Service", msg);
        }
    }

    public NbiMessage recv() throws BasicException {

        while (queueFlag.isTrue()) {
            try {
                msgReceiverLock.lock();
                if (nbiQueue.peek() != null) {
                    NbiMessage msg = nbiQueue.remove();
                    return msg;
                }

                // Wait for 20 secondes and incase signal is missed then check again
                msgReceiverLock.await(20);
            } catch (InterruptedException iex) {
                throw new BasicException(BasicException.BasicExceptionType.COMMUNICATION_EXCEPTION,
                        BasicException.BasicExceptionSubType.WAIT_INTERRUPTED);
            } finally {
                msgReceiverLock.unlock();
            }
        }

        return null;
    }

    public void release() {

        super.release();
        msgReceiverLock.lock();
        msgReceiverLock.signal();
        msgReceiverLock.unlock();
    }
}
