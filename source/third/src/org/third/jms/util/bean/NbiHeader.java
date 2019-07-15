
import java.io.Serializable;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;


public class NbiHeader implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    // Identifies the operation name
    private ActivityName_T activityName;

    // Identifies the service the message needs to be routed
    private ServiceName_T serviceName;
    
    // Message properties used for topic filtering
    private Properties msgProperties;
   
    private Integer queueNo;
    
    // -------- Used internally my messaging
    // Unique message Id
    private long messageId;
    
    // Used for correlating response and sender thread
    private long correlationId;
    
    // JMS reply queue instance
    private transient Object jmsReplyToQueue;
    
    public NbiHeader() {
        messageId = getNextSeqNumber();
        queueNo = 0;
    }
    
    public Integer getQueueNo(){
    	return queueNo;
    }
    
    public void setQueueNo(Integer no){
    	queueNo = no;
    }

    public long getMessageId() {
        return messageId;
    }

    public long getCorrelationId() {
        return correlationId;
    }

    public void setMessageId(long msgId) {
        this.messageId = msgId;
    }

    public void setCorrelationId(long correlationId) {
        this.correlationId = correlationId;
    }

    public ActivityName_T getActivityName() {
        return activityName;
    }

    public ServiceName_T getServiceName() {
        return serviceName;
    }

    public void setActivityName(ActivityName_T activityName) {
        this.activityName = activityName;
    }

    public void setServiceName(ServiceName_T serviceName) {
        this.serviceName = serviceName;
    }

    public void setJmsReplyTo(Object jmsReplyTo) {
        this.jmsReplyToQueue = jmsReplyTo;
    }

    public Object getJmsReplyTo() {
        return jmsReplyToQueue;
    }

    public void setMsgProperties(Properties msgProperties) {
        this.msgProperties = msgProperties;
    }
    
    public Properties getMsgProperties() {
        return  this.msgProperties;
    }
    
    private static AtomicLong curNumber = new AtomicLong(1);

    private static synchronized long getNextSeqNumber() {
        long newMsgId = curNumber.incrementAndGet();
        if (newMsgId > 1000000)
        {
            curNumber.set(1);
            return 1;
        }
        
        return newMsgId;
    }
    
    
    public String toString() {
        StringBuffer strBuf = new StringBuffer();
        strBuf.append(" Message Id " + messageId);
        strBuf.append(" ActivityName " + activityName);
        strBuf.append(" ServiceName " + serviceName);
        
        return strBuf.toString();
        
    }

}
