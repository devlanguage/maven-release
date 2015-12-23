package org.third.jms.imq.transaction.order_pc;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.MapMessage;

/**
 * The Order class represents a Retailer order placed with a Vendor. It maintains a table of pending
 * orders.
 */
public class Order {

    private static Hashtable pendingOrders = new Hashtable();
    private static int nextOrderNumber = 1;

    private static final int PENDING_STATUS = 1;
    private static final int CANCELLED_STATUS = 2;
    private static final int FULFILLED_STATUS = 3;
    int status;

    public final int orderNumber;
    public int quantity;
    public final MapMessage order; // original order from retailer
    public MapMessage monitor = null; // reply from supplier
    public MapMessage storage = null; // reply from supplier

    /**
     * Returns the next order number and increments the static variable that holds this value.
     * 
     * @return the next order number
     */
    private static int getNextOrderNumber() {

        int result = nextOrderNumber;
        nextOrderNumber++;
        return result;
    }

    /**
     * Constructor. Sets order number; sets order and quantity from incoming message. Sets status to
     * pending, and adds order to hash table of pending orders.
     * 
     * @param order
     *            the message containing the order
     */
    public Order(MapMessage order) {

        this.orderNumber = getNextOrderNumber();
        this.order = order;
        try {
            this.quantity = order.getInt("Quantity");
        } catch (JMSException je) {
            System.err.println("Unexpected error. Message missing Quantity");
            this.quantity = 0;
        }
        status = PENDING_STATUS;
        pendingOrders.put(new Integer(orderNumber), this);
    }

    /**
     * Returns the number of orders in the hash table.
     * 
     * @return the number of pending orders
     */
    public static int outstandingOrders() {

        return pendingOrders.size();
    }

    /**
     * Returns the order corresponding to a given order number.
     * 
     * @param orderNumber
     *            the number of the requested order
     * @return the requested order
     */
    public static Order getOrder(int orderNumber) {

        return (Order) pendingOrders.get(new Integer(orderNumber));
    }

    /**
     * Called by the onMessage method of the VendorMessageListener class to process a reply from a
     * supplier to the Vendor.
     * 
     * @param component
     *            the message from the supplier
     * @return the order with updated status information
     */
    public Order processSubOrder(MapMessage component) {

        String itemName = null;

        // Determine which subcomponent this is.
        try {
            itemName = component.getString("Item");
        } catch (JMSException je) {
            System.err.println("Unexpected exception. Message missing Item");
        }
        if (itemName.compareTo("Monitor") == 0) {
            monitor = component;
        } else if (itemName.compareTo("Hard Drive") == 0) {
            storage = component;
        }

        /*
         * If notification for all subcomponents has been received, verify the quantities to compute
         * if able to fulfill order.
         */
        if ((monitor != null) && (storage != null)) {
            try {
                if (quantity > monitor.getInt("Quantity")) {
                    status = CANCELLED_STATUS;
                } else if (quantity > storage.getInt("Quantity")) {
                    status = CANCELLED_STATUS;
                } else {
                    status = FULFILLED_STATUS;
                }
            } catch (JMSException je) {
                System.err.println("Unexpected exception " + je);
                status = CANCELLED_STATUS;
            }

            /*
             * Processing of order is complete, so remove it from pending-order list.
             */
            pendingOrders.remove(new Integer(orderNumber));
        }
        return this;
    }

    /**
     * Determines if order status is pending.
     * 
     * @return true if order is pending, false if not
     */
    public boolean isPending() {

        return status == PENDING_STATUS;
    }

    /**
     * Determines if order status is cancelled.
     * 
     * @return true if order is cancelled, false if not
     */
    public boolean isCancelled() {

        return status == CANCELLED_STATUS;
    }

    /**
     * Determines if order status is fulfilled.
     * 
     * @return true if order is fulfilled, false if not
     */
    public boolean isFulfilled() {

        return status == FULFILLED_STATUS;
    }
}