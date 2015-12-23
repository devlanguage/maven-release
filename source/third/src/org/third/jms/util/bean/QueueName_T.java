package org.third.jms.util.bean;


public enum QueueName_T {	
    
    ADAPTER_SERVICE_LOCAL_QUEUE(DestinationType_T.LOCAL),
    
    MTOSI_SERVICE_REMOTE_QUEUE(DestinationType_T.REMOTE),
    MTOSI_SERVICE_LOCAL_QUEUE(DestinationType_T.LOCAL),
    
    CONFIG_SERVICE_LOCAL_QUEUE(DestinationType_T.LOCAL),
    SECURITY_SERVICE_LOCAL_QUEUE(DestinationType_T.LOCAL),
    ADMIN_SERVICE_LOCAL_QUEUE(DestinationType_T.LOCAL);
    
    public final static Integer LOCAL_QUEUE_NO = Integer.valueOf(0);
    public final static Integer REMOTE_QUEUE_NO = Integer.valueOf(0);
    
    private DestinationType_T queueType = DestinationType_T.LOCAL;
    
    QueueName_T(DestinationType_T type) {
        queueType = type;
    }
    
    public DestinationType_T getDestinationType() {
        return queueType;
    }
}
