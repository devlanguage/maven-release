
import static org.third.jms.imq.transaction.order_pc.TransactionOrderTest.A1_retailerOrderQueueName;
import static org.third.jms.imq.transaction.order_pc.TransactionOrderTest.A2_retailerConfirmationQueueName;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.third.jms.imq.example.jms.SampleUtilities;

/**
 * The Retailer class orders a number of computers by sending a message to a vendor. It then waits
 * for the order to be confirmed.
 * <p>
 * In this example, the Retailer places two orders, one for the quantity specified on the command
 * line and one for twice that number.
 * <p>
 * This class does not use transactions.
 */
public class Retailer extends Thread {

    int quantity = 0;
    int exitResult = 0;

    /**
     * Constructor. Instantiates the retailer with the quantity of computers being ordered.
     */
    public Retailer(int q, String name) {

        super(name);
        quantity = q;
    }

    public void run() {

        ConnectionFactory connectionFactory = null;
        Connection connection = null;
        Session nonTransactedSession = null;
        Queue vendorOrderQueue = null;
        Queue retailerConfirmationQueue = null;
        try {
            connectionFactory = SampleUtilities.getConnectionFactory();
            connection = connectionFactory.createConnection();
            nonTransactedSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            vendorOrderQueue = SampleUtilities.getQueue(A1_retailerOrderQueueName, nonTransactedSession);
            retailerConfirmationQueue = SampleUtilities.getQueue(A2_retailerConfirmationQueueName,
                    nonTransactedSession);
        } catch (Exception e) {
            System.out.println("Connection problem: " + e.toString());
            System.out.println("Program assumes five queues named A B C D E");
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException ee) {
                }
            }
            System.exit(1);
        }

        /*
         * Create non-transacted session and sender for vendor order queue. Create message to
         * vendor, setting item and quantity values. Send message. Create receiver for retailer
         * confirmation queue. Get message and report result. Send an end-of-message-stream message
         * so vendor will stop processing orders.
         */
        MessageProducer msgProducer = null;
        MapMessage orderMapMsg = null;
        MessageConsumer orderConfirmationConsumer = null;
        MapMessage confirmOrderMapMsg = null;
        try {
            msgProducer = nonTransactedSession.createProducer(vendorOrderQueue);
            System.out.println("\n\n##############   Retailer: placing the order 001  ############\n");
            orderMapMsg = nonTransactedSession.createMapMessage();
            orderMapMsg.setString("Item", "Computer(s)");
            orderMapMsg.setInt("Quantity", quantity);
            orderMapMsg.setJMSReplyTo(retailerConfirmationQueue);
            msgProducer.send(orderMapMsg);
            System.out.println("Retailer: ordered " + quantity + " computer(s) on "+A1_retailerOrderQueueName+" and wait for result on "+A2_retailerConfirmationQueueName);

            orderConfirmationConsumer = nonTransactedSession.createConsumer(retailerConfirmationQueue);
            connection.start();
            confirmOrderMapMsg = (MapMessage) orderConfirmationConsumer.receive();
            if (confirmOrderMapMsg.getBoolean("OrderAccepted") == true) {
                System.out.println("Retailer: Order filled");
            } else {
                System.out.println("Retailer: Order not filled");
            }

            System.out.println("\n\n##############   Retailer: placing the order 002  ############\n");
            orderMapMsg.setInt("Quantity", quantity * 2);
            msgProducer.send(orderMapMsg);
            System.out.println("Retailer: ordered " + orderMapMsg.getInt("Quantity")
                    + " computer(s)  on "+A1_retailerOrderQueueName+" and wait for result on "+A2_retailerConfirmationQueueName);
            confirmOrderMapMsg = (MapMessage) orderConfirmationConsumer.receive();
            if (confirmOrderMapMsg.getBoolean("OrderAccepted") == true) {
                System.out.println("Retailer: Order filled");
            } else {
                System.out.println("Retailer: Order not filled");
            }

            // Send a non-text control message indicating end of messages.
            msgProducer.send(nonTransactedSession.createMessage());
        } catch (Exception e) {
            System.out.println("Retailer: Exception occurred: " + e.toString());
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