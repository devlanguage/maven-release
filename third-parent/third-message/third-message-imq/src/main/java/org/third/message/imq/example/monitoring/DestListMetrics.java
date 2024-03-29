package org.third.message.imq.example.monitoring;

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
 * @(#)DestListMetrics.java 1.6 07/02/07
 */

import java.util.Enumeration;
import java.util.Hashtable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;

import org.third.message.imq.util.JmsUtil;
import org.third.message.imq.util.MessageUtil.JmsServerType;

/**
 * The DestListMetrics example is a JMS application that monitors the destinations on a Sun Java(tm) System Message
 * Queue broker. It does so by subscribing to a topic named 'mq.metrics.destination_list'. The messages that arrive
 * contain information describing the destinations that currently exist on the broker such as: - destination name -
 * destination type - whether the destination is temporary or not
 * 
 * By default DestListMetrics will connect to the broker running on localhost:7676. You can use -DimqAddressList
 * attribute to change the host, port and transport:
 * 
 * java -DimqAddressList=mq://<host>:<port>/jms DestListMetrics
 */
public class DestListMetrics implements MessageListener {

    ConnectionFactory metricConnectionFactory;
    Connection metricConnection;
    Session metricSession;
    MessageConsumer metricConsumer;
    Topic metricTopic;
    MetricsPrinter mp;
    String metricTopicName = null;
    int rowsPrinted = 0;

    public static void main(String args[]) {

        DestListMetrics dlm = new DestListMetrics();

        dlm.initPrinter();
        dlm.initJMS();
        dlm.subscribeToMetric();
    }

    public DestListMetrics() {

    }

    /*
     * Initializes the class that does the printing, MetricsPrinter. See the MetricsPrinter class for details.
     */
    private void initPrinter() {

        String oneRow[] = new String[3];
        int i = 0;

        mp = new MetricsPrinter(3, 2, "-");
        oneRow[i++] = "Destination Name";
        oneRow[i++] = "Type";
        oneRow[i++] = "Is Temporary";
        mp.addTitle(oneRow);
    }

    /**
     * Create the Connection and Session etc.
     */
    public void initJMS() {

        try {
            // creating Session
            // Transaction Mode: None
            // Acknowledge Mode: Automatic
            metricSession = JmsUtil.getInstance().getSession(JmsServerType.IMQ, false, Session.AUTO_ACKNOWLEDGE);
        } catch (Exception e) {
            System.err.println("Cannot create metric connection or session: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void subscribeToMetric() {

        try {

            metricTopicName = "mq.metrics.destination_list";
            metricTopic = metricSession.createTopic(metricTopicName);

            metricConsumer = metricSession.createConsumer(metricTopic);
            metricConsumer.setMessageListener(this);
        } catch (JMSException e) {
            System.err.println("Cannot subscribe to metric topic: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    /*
     * When a metric message arrives - verify it's type - extract it's fields - print one row of output
     */
    public void onMessage(Message m) {

        try {
            MapMessage mapMsg = (MapMessage) m;
            String type = mapMsg.getStringProperty("type");

            if (type.equals(metricTopicName)) {
                String oneRow[] = new String[3];

                /*
                 * Extract metrics
                 */
                for (Enumeration e = mapMsg.getMapNames(); e.hasMoreElements();) {

                    String metricDestName = (String) e.nextElement();
                    Hashtable destValues = (Hashtable) mapMsg.getObject(metricDestName);
                    int i = 0;

                    oneRow[i++] = (destValues.get("name")).toString();
                    oneRow[i++] = (destValues.get("type")).toString();
                    oneRow[i++] = (destValues.get("isTemporary")).toString();

                    mp.add(oneRow);
                }

                mp.print();
                System.out.println("");

                mp.clear();
            } else {
                System.err.println("Msg received: not destination list metric type");
            }
        } catch (Exception e) {
            System.err.println("onMessage: Exception caught: " + e);
        }
    }
}
