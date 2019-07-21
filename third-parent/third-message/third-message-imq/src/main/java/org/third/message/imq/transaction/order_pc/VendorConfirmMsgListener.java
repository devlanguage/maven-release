package org.third.message.imq.transaction.order_pc;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.third.message.imq.example.jms.SampleUtilities;

/**
 * The VendorMessageListener class processes an order confirmation message from a supplier to the
 * vendor.
 * <p>
 * It demonstrates the use of transactions within message listeners.
 */
public class VendorConfirmMsgListener implements MessageListener {

    final SampleUtilities.DoneLatch monitor = new SampleUtilities.DoneLatch();
    private final Session session;
    int numSuppliers;

    /**
     * Constructor. Instantiates the message listener with the session of the consuming class (the
     * vendor).
     * 
     * @param session
     *            the session of the consumer
     * @param numSuppliers
     *            the number of suppliers
     */
    public VendorConfirmMsgListener(Session session, int numSuppliers) {

        this.session = session;
        this.numSuppliers = numSuppliers;
    }

    /**
     * Casts the message to a MapMessage and processes the order. A message that is not a MapMessage
     * is interpreted as the end of the message stream, and the message listener sets its monitor
     * state to all done processing messages.
     * <p>
     * Each message received represents a fulfillment message from a supplier.
     * 
     * @param message
     *            the incoming message
     */
    public void onMessage(Message message) {

        /*
         * If message is an end-of-message-stream message and this is the last such message, set
         * monitor status to all done processing messages and commit transaction.
         */
        if (!(message instanceof MapMessage)) {
            if (Order.outstandingOrders() == 0) {
                numSuppliers--;
                if (numSuppliers == 0) {
                    monitor.allDone();
                }
            }
            try {
                session.commit();
            } catch (JMSException je) {
            }
            return;
        }

        /*
         * Message is an order confirmation message from a supplier.
         */
        int orderNumber = -1;
        try {
            MapMessage component = (MapMessage) message;

            /*
             * Process the order confirmation message and commit the transaction.
             */
            orderNumber = component.getInt("VendorOrderNumber");
            Order order = Order.getOrder(orderNumber).processSubOrder(component);
            session.commit();

            /*
             * If this message is the last supplier message, send message to Retailer and commit
             * transaction.
             */
            if (!order.isPending()) {
                System.out.println("Vendor: Completed processing for order " + order.orderNumber);
                Queue replyQueue = (Queue) order.order.getJMSReplyTo();
                MessageProducer mp = session.createProducer(replyQueue);
                MapMessage retailerConfirmationMessage = session.createMapMessage();
                if (order.isFulfilled()) {
                    retailerConfirmationMessage.setBoolean("OrderAccepted", true);
                    System.out.println("Vendor: sent " + order.quantity + " computer(s)");
                } else if (order.isCancelled()) {
                    retailerConfirmationMessage.setBoolean("OrderAccepted", false);
                    System.out.println("Vendor: unable to send " + order.quantity + " computer(s)");
                }
                mp.send(retailerConfirmationMessage);
                session.commit();
                System.out.println("  Vendor: committed transaction 2");
            }
        } catch (JMSException je) {
            je.printStackTrace();
            try {
                session.rollback();
            } catch (JMSException je2) {
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                session.rollback();
            } catch (JMSException je2) {
            }
        }
    }
}