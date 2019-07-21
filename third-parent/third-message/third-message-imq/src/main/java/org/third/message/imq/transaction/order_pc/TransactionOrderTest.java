package org.third.message.imq.transaction.order_pc;

import org.third.message.imq.example.jms.SampleUtilities;

/**
 * <pre>
 *                                                            /-order--B1_monitorOrderQueueName     ----) Supplier01 ---/
 * Retail---order--A1_retailerOrderQueueName--)  --\         /--order--B2_storageOrderQueueName     ----) Supplier02 --/
 *    |                                         ----Vendor--      .....                                         ..--  / 
 *    |--(--waiting--A2_retailerConfirmQueName   --/         \--Waiting--BN_vendorConfirmationQueueName  ------------/
 *  
 * </pre>
 */
public class TransactionOrderTest {

    final static String A1_retailerOrderQueueName = "A1_retailerOrderQueueName";
    final static String A2_retailerConfirmationQueueName = "A2_retailerConfirmationQueueName";

    final static String B1_monitorOrderQueueName = "B1_monitorOrderQueueName";
    final static String B2_storageOrderQueueName = "B2_storageOrderQueueName";

    final static String BN_vendorConfirmationQueueName = "BN_vendorConfirmationQueueName";

    public static void main(String[] args) {

        int quantity = 0;
        int exitResult = 0;

        if (args.length != 1) {
            System.out.println("Usage: java TransactedExample <integer>");
            System.out.println("Program assumes five queues named A B C D E");
            quantity = 2;
        } else {
            quantity = (new Integer(args[0])).intValue();
        }

        System.out.println("Quantity to be ordered is " + quantity);
        if (quantity > 0) {
            Retailer retailerThread = new Retailer(quantity, "RetailerThread");

            Vendor vendorThread = new Vendor("VendorThread");
            GenericSupplier monitorSupplierThread = new GenericSupplier("Monitor",
                    B1_monitorOrderQueueName);
            GenericSupplier hardDiskSupplierThread = new GenericSupplier("Hard Drive",
                    B2_storageOrderQueueName);

            retailerThread.start();
            vendorThread.start();
            monitorSupplierThread.start();
            hardDiskSupplierThread.start();
            try {
                retailerThread.join();
                vendorThread.join();
                monitorSupplierThread.join();
                hardDiskSupplierThread.join();
            } catch (InterruptedException e) {
            }
        } else {
            System.out.println("Quantity must be positive and nonzero");
            exitResult = 1;
        }
        SampleUtilities.exit(exitResult);
    }
}
