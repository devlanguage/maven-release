
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
 * The GenericSupplier class receives an item order from the vendor and sends a message accepting or
 * refusing it.
 */
public class GenericSupplier extends Thread {

    final String productName;
    final String vendorOrderQueueName;
    int quantity = 0;
    int exitResult;

    /**
     * Constructor. Instantiates the supplier as the supplier for the kind of item being ordered.
     * 
     * @param itemName
     *            the name of the item being ordered
     * @param inQueue
     *            the queue from which the order is obtained
     */
    public GenericSupplier(String itemName, String inQueue) {

        productName = itemName;
        vendorOrderQueueName = inQueue;
    }

    /**
     * Checks to see if there are enough items in inventory. Rather than go to a database, it
     * generates a random number related to the order quantity, so that some of the time there won't
     * be enough in stock.
     * 
     * @return the number of items in inventory
     */
    public int checkInventory() {

        Random rgen = new Random();

        return (rgen.nextInt(quantity * 5));
    }

    /**
     * Runs the thread.
     */
    public void run() {

        ConnectionFactory connectionFactory = null;
        Connection connection = null;
        Session session = null;
        Queue orderQueue = null;
        

        try {
            connectionFactory = SampleUtilities.getConnectionFactory();
            connection = connectionFactory.createConnection();
            session = connection.createSession(true, 0);
            orderQueue = SampleUtilities.getQueue(vendorOrderQueueName, session);
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

        MessageConsumer msgConsumer = null;
        // Create receiver for order queue and start message delivery.
        try {
            msgConsumer = session.createConsumer(orderQueue);
            connection.start();
        } catch (JMSException je) {
            exitResult = 1;
        }

        /*
         * Keep checking supplier order queue for order request until end-of-message-stream message
         * is received. Receive order and send an order confirmation as one transaction.
         */
        while (true) {
            try {
                Message inMessage = null;
                MapMessage orderMessage = null;
                MapMessage outMessage = null;
                
                inMessage = msgConsumer.receive();
                if (inMessage instanceof MapMessage) {
                    orderMessage = (MapMessage) inMessage;
                } else {
                    /*
                     * Message is an end-of-message-stream message. Send a similar message to reply
                     * queue, commit transaction, then stop processing orders by breaking out of
                     * loop.
                     */
                    MessageProducer msgProducer = session.createProducer((Queue) inMessage
                            .getJMSReplyTo());
                    msgProducer.send(session.createMessage());
                    session.commit();
                    break;
                }

                // Extract quantity ordered from order message.
                quantity = orderMessage.getInt("Quantity");
                System.out.println(productName + " Supplier: Vendor ordered " + quantity + " "
                        + orderMessage.getString("Item") + "(s)");

                /*
                 * Create sender and message for reply queue. Set order number and item; check
                 * inventory and set quantity available. Send message to vendor and commit
                 * transaction.
                 */
                MessageProducer msgProducer = session.createProducer((Queue) orderMessage
                        .getJMSReplyTo());
                outMessage = session.createMapMessage();
                outMessage.setInt("VendorOrderNumber", orderMessage.getInt("VendorOrderNumber"));
                outMessage.setString("Item", productName);
                int numAvailable = checkInventory();
                if (numAvailable >= quantity) {
                    outMessage.setInt("Quantity", quantity);
                } else {
                    outMessage.setInt("Quantity", numAvailable);
                }
                msgProducer.send(outMessage);
                System.out.println(productName + " Supplier: sent "
                        + outMessage.getInt("Quantity") + " " + outMessage.getString("Item")
                        + "(s)");
                session.commit();
                System.out.println("  " + productName + " Supplier: committed transaction");
            } catch (Exception e) {
                System.out.println(productName + " Supplier: Exception occurred: " + e.toString());
                e.printStackTrace();
                exitResult = 1;
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (JMSException e) {
                exitResult = 1;
            }
        }
    }
}