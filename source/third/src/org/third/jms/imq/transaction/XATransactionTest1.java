package org.third.jms.imq.transaction;

import javax.jms.JMSException;
import javax.jms.XAConnection;

import com.sun.messaging.XAQueueConnectionFactory;

public class XATransactionTest1 {

    public static void main(String[] args) {

        com.sun.messaging.XAQueueConnectionFactory xaCf = new XAQueueConnectionFactory();
        try {
            XAConnection xaConn = xaCf.createXAConnection();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
