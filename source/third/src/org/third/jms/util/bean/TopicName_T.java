package org.third.jms.util.bean;


public enum TopicName_T {
   
    FAULT_TOPIC(DestinationType_T.REMOTE),
    INVENTORY_TOPIC(DestinationType_T.REMOTE),
    PROTECTION_TOPIC(DestinationType_T.REMOTE),
    FILE_TRANS_STATUS_TOPIC(DestinationType_T.REMOTE),
    NBI_INTERNAL_TOPIC(DestinationType_T.REMOTE);
                
    private DestinationType_T topicType = DestinationType_T.LOCAL;
    
    TopicName_T(DestinationType_T type) {
        topicType = type;
    }
    
    public DestinationType_T getDestinationType() {
        return topicType;
    }
}
