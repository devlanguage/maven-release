package org.third.jms.imq.transaction.order_pc;

import static org.third.jms.imq.transaction.order_pc.TransactionOrderTest.A1_retailerOrderQueueName;
import static org.third.jms.imq.transaction.order_pc.TransactionOrderTest.B1_monitorOrderQueueName;
import static org.third.jms.imq.transaction.order_pc.TransactionOrderTest.B2_storageOrderQueueName;
import static org.third.jms.imq.transaction.order_pc.TransactionOrderTest.BN_vendorConfirmationQueueName;

import java.util.Random;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.third.jms.imq.example.jms.SampleUtilities;

/**
 * The Vendor class uses one transaction to receive the computer order from the retailer and order
 * the needed number of monitors and disk drives from its suppliers. At random intervals, it throws
 * an exception to simulate a database problem and cause a rollback.
 * <p>
 * The class uses an asynchronous message listener to process replies from suppliers. When all
 * outstanding supplier inquiries complete, it sends a message to the Retailer accepting or refusing
 * the order.
 */
public class Vendor extends Thread {

    public Vendor(String name) {

        super(name);
    }

    Random rgen = new Random();
    int throwException = 1;
    int exitResult = 0;

    /**
     * Runs the thread.
     */
    public void run() {

        ConnectionFactory connectionFactory = null;
        Connection connection = null;
        Session transactionSession = null;
        Session asyncTransactionSession = null;
        Queue retailerOrderQueue = null;
        Queue vendorMonitorOrderQueue = null;
        Queue vendorStorageOrderQueue = null;
        Queue vendorConfirmationQueue = null;

        try {
            connectionFactory = SampleUtilities.getConnectionFactory();
            connection = connectionFactory.createConnection();
            transactionSession = connection.createSession(true, 0);
            asyncTransactionSession = connection.createSession(true, 0);
            retailerOrderQueue = SampleUtilities.getQueue(A1_retailerOrderQueueName, transactionSession);
            
            vendorMonitorOrderQueue = SampleUtilities.getQueue(B1_monitorOrderQueueName, transactionSession);
            vendorStorageOrderQueue = SampleUtilities.getQueue(B2_storageOrderQueueName, transactionSession);
            vendorConfirmationQueue = SampleUtilities.getQueue(BN_vendorConfirmationQueueName, transactionSession);
        } catch (Exception e) {
            System.out.println("Connection problem: " + e.toString());
            System.out.println("Program assumes six queues named A B C D E F");
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException ee) {
                }
            }
            System.exit(1);
        }

        try {
            /*
             * Configure an asynchronous message listener to process supplier replies to inquiries
             * for parts to fill order. Start delivery.
             */
            MessageConsumer vendorConfirmationMsgConsumer = asyncTransactionSession
                    .createConsumer(vendorConfirmationQueue);
            VendorConfirmMsgListener vendorConfirmMsgListener = new VendorConfirmMsgListener(asyncTransactionSession, 2);
            vendorConfirmationMsgConsumer.setMessageListener(vendorConfirmMsgListener);
            connection.start();

            /*
             * Process orders in vendor order queue. Use one transaction to receive order from order
             * queue and send messages to suppliers' order queues to order components to fulfill the
             * order placed with the vendor.
             */
            Message orderMsgFromRetailer = null;
            MapMessage vendorOrderMessage = null;
            Message endOfMessageStream = null;
            Order order = null;
            int quantity = 0;
            while (true) {
                try {
                    MessageProducer vendorMonitorOrderMsgProducer = transactionSession.createProducer(vendorMonitorOrderQueue);
                    MessageProducer vendorStorageOrderMsgProducer = transactionSession.createProducer(vendorStorageOrderQueue);
                    MapMessage orderMessage = transactionSession.createMapMessage();

                    /*
                     * Create receiver for vendor order queue, sender for supplier order queues, and message
                     * to send to suppliers.
                     */
                    MessageConsumer retailerOrderMsgConsumer = transactionSession.createConsumer(retailerOrderQueue);
                                        
                    // Receive an order from a retailer.
                    orderMsgFromRetailer = retailerOrderMsgConsumer.receive();
                    if (orderMsgFromRetailer instanceof MapMessage) {
                        vendorOrderMessage = (MapMessage) orderMsgFromRetailer;
                    } else {
                        /*
                         * Message is an end-of-message-stream message from retailer. Send similar
                         * messages to suppliers, then break out of processing loop.
                         */
                        endOfMessageStream = transactionSession.createMessage();
                        endOfMessageStream.setJMSReplyTo(vendorConfirmationQueue);
                        vendorMonitorOrderMsgProducer.send(endOfMessageStream);
                        vendorStorageOrderMsgProducer.send(endOfMessageStream);
                        transactionSession.commit();
                        break;
                    }

                    /*
                     * A real application would check an inventory database and order only the
                     * quantities needed. Throw an exception every few times to simulate a database
                     * concurrent-access exception and cause a rollback.
                     */
                    if (rgen.nextInt(3) == throwException) {
                        throw new JMSException("Simulated database concurrent access exception");
                    }

                    // Record retailer order as a pending order.
                    order = new Order(vendorOrderMessage);

                    // Set order number and reply queue for outgoing message.
                    orderMessage.setInt("VendorOrderNumber", order.orderNumber);
                    orderMessage.setJMSReplyTo(vendorConfirmationQueue);
                    quantity = vendorOrderMessage.getInt("Quantity");
                    System.out.println("Vendor: Retailer ordered " + quantity + " "
                            + vendorOrderMessage.getString("Item"));

                    // Send message to monitor supplier.
                    orderMessage.setString("Item", "Monitor");
                    orderMessage.setInt("Quantity", quantity);
                    vendorMonitorOrderMsgProducer.send(orderMessage);                    
                    System.out.println("Vendor: ordered " + quantity + " "+ orderMessage.getString("Item") + "(s)");
                    
                    // Reuse message to send to storage supplier, changing only item name.
                    orderMessage.setString("Item", "Hard Drive");
                    vendorStorageOrderMsgProducer.send(orderMessage);
                    System.out.println("Vendor: ordered " + quantity + " " + orderMessage.getString("Item") + "(s)");

                    // Commit session.
                    transactionSession.commit();
                    System.out.println("  Vendor: committed transaction 1");
                } catch (JMSException e) {
                    System.out.println("Vendor: JMSException occurred: " + e.toString());
                    e.printStackTrace();
                    transactionSession.rollback();
                    System.out.println("  Vendor: rolled back transaction 1");
                    exitResult = 1;
                }
            }

            // Wait till suppliers get back with answers.
            vendorConfirmMsgListener.monitor.waitTillDone();
        } catch (JMSException e) {
            System.out.println("Vendor: Exception occurred: " + e.toString());
            e.printStackTrace();
            exitResult = 1;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    exitResult = 1;
                }
            }
        }
    }
}