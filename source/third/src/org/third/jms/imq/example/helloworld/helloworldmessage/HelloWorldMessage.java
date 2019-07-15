
/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 2000-2007 Sun Microsystems, Inc. All rights reserved.
 * 
 * The contents of this file are subject to the terms of either the GNU General Public License
 * Version 2 only ("GPL") or the Common Development and Distribution License ("CDDL") (collectively,
 * the "License"). You may not use this file except in compliance with the License. You can obtain a
 * copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html or
 * mq/legal/LICENSE.txt. See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each file and include the
 * License file at mq/legal/LICENSE.txt. Sun designates this particular file as subject to the
 * "Classpath" exception as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information: "Portions Copyrighted
 * [year] [name of copyright owner]"
 * 
 * Contributor(s):
 * 
 * If you wish your version of this file to be governed by only the CDDL or only the GPL Version 2,
 * indicate your decision by adding "[Contributor] elects to include this software in this
 * distribution under the [CDDL or GPL Version 2] license." If you don't indicate a single choice of
 * license, a recipient has the option to distribute your version of this file under either the
 * CDDL, the GPL Version 2 or to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL Version 2 license, then the
 * option applies only if the new code is made subject to such option by the copyright holder.
 */

/*
 * @(#)HelloWorldMessage.java 1.10 07/02/07
 */

/**
 * The HelloWorldMessage class consists only of a main method, which sends a message to a queue and
 * then receives the message from the queue.
 * <p>
 * This example is used in the "Quick Start Tutorial" of the Sun Java(tm) System Message Queue
 * Developer's Guide to illustrate a very simple JMS client. The line comments associate the lines
 * of code with the steps in the tutorial.
 */

// Step 1:
// Import the JMS API classes.
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

public class HelloWorldMessage {

    /**
     * Main method.
     * 
     * @param args
     *            not used
     * 
     */
    public static void main(String[] args) {

        try {

            ConnectionFactory myConnFactory;
            final Queue myQueue;

            /**
             * The following code uses the JNDI File System Service Provider to lookup()
             * Administered Objects that were stored in the Administration Console Tutorial in the
             * Administrator's Guide
             * 
             * The following code (in this comment block replaces the statements in Steps 2 and 5 of
             * this example.
             * 
             * String MYCF_LOOKUP_NAME = "MyConnectionFactory"; <br>
             * String MYQUEUE_LOOKUP_NAME = "MyQueue";
             * 
             * Hashtable env; Context ctx = null;
             * 
             * env = new Hashtable(); // Store the environment variable that tell JNDI which initial
             * context to use and where to find the provider. For use with the File System JNDI
             * Service Provider <br>
             * <br>
             * env.put(Context.INITIAL_CONTEXT_FACTORY,
             * "com.sun.jndi.fscontext.RefFSContextFactory"); // On Unix, use file:///tmp instead of
             * file:///C:/Temp<br>
             * <br>
             * env.put(Context.PROVIDER_URL, "file:///C:/Temp"); // Create the initial <br>
             * <br>
             * context. ctx = new InitialContext(env); // Lookup my connection factory from the
             * admin object store. The name used here here must match the lookup name used when the
             * admin object was stored. <br>
             * <br>
             * myConnFactory = (javax.jms.ConnectionFactory) ctx.lookup(MYCF_LOOKUP_NAME); // Lookup
             * my queue from the admin object store. The name I search for here must match the
             * lookup name used when the admin object was stored.<br>
             * <br>
             * myQueue = (javax.jms.Queue)ctx.lookup(MYQUEUE_LOOKUP_NAME); ***
             * 
             */

            // Step 2:
            // Instantiate a Sun Java(tm) System Message Queue ConnectionFactory
            // administered object.
            // This statement can be eliminated if the JNDI code above is used.
            myConnFactory = new com.sun.messaging.ConnectionFactory();

            // Step 3:
            // Create a connection to the Sun Java(tm) System Message Queue Message
            // Service.
            Connection myConn = myConnFactory.createConnection();
            myConn.start();
            // Step 4:
            // Create a session within the connection.
            final Session mySess = myConn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // Step 5:
            // Instantiate a Sun Java(tm) System Message Queue Destination
            // administered object.
            // This statement can be eliminated if the JNDI code above is used.
            myQueue = new com.sun.messaging.Queue("world");

            // Step 6:
            // Create a message producer.
            final MessageProducer myMsgProducer = mySess.createProducer(myQueue);

            // Step 7:
            // Create and send a message to the queue.
            new Thread(new Runnable() {

                public void run() {

                    TextMessage myTextMsg;
                    int countOfMessageSent = 0;
                    while (countOfMessageSent < 10) {
                        try {
                            myTextMsg = mySess.createTextMessage();
                            myTextMsg.setText("Hello World");
                            System.out.println("Sending Message: " + myTextMsg.getText());
                            myMsgProducer.send(myTextMsg);
                            Thread.sleep(3000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

            // Step 8:
            // Create a message consumer.
            final MessageConsumer myMsgConsumer = mySess.createConsumer(myQueue);

            // Step 9:
            // Start the Connection created in step 3.
//            myConn.start();

            new Thread(new Runnable() {

                public void run() {

                    while (true) {
                        try {
                            // Step 10:
                            // Receive a message from the queue.
                            Message msg = myMsgConsumer.receive();

                            // Step 11:
                            // Retreive the contents of the message.
                            if (msg instanceof TextMessage) {
                                TextMessage txtMsg = (TextMessage) msg;
                                System.out.println("Read Message: " + txtMsg.getText());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

            Thread.sleep(30000);

            // Step 12:
            // Close the session and connection resources.
            mySess.close();
            myConn.close();

        } catch (Exception jmse) {
            System.out.println("Exception occurred : " + jmse.toString());
            jmse.printStackTrace();
            System.exit(0);
        }
    }

    public final static void exit() {

    }
}
