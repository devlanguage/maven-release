package org.third.message.imq.example.jaxm;
/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2000-2007 Sun Microsystems, Inc. All rights reserved. 
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License ("CDDL") (collectively, the "License").  You may
 * not use this file except in compliance with the License.  You can obtain
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 * or mq/legal/LICENSE.txt.  See the License for the specific language
 * governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at mq/legal/LICENSE.txt.  Sun designates
 * this particular file as subject to the "Classpath" exception as provided by
 * Sun in the GPL Version 2 section of the License file that accompanied this
 * code.  If applicable, add the following below the License Header, with the
 * fields enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * Contributor(s):
 * 
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or  to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright holder. 
 */

/*
 * @(#)SendSOAPMessage.java	1.6 07/02/07
 */ 

import javax.xml.messaging.URLEndpoint;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPPart;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPConnection;

/**
 * This example demonstrates a hello world example for using JAXM API.
 */
public class SendSOAPMessage {

    /**
     * send a simple soap message with JAXM API.
     */
    public void sendMessage (String url) {

        try {
            /**
             * Construct a default SOAP message factory.
             */
            MessageFactory mf = MessageFactory.newInstance();
            /**
             * Create a SOAP message object.
             */
            SOAPMessage soapMessage = mf.createMessage();
            /**
             * Get SOAP part.
             */
            SOAPPart soapPart = soapMessage.getSOAPPart();
            /**
             * Get SOAP envelope.
             */
            SOAPEnvelope soapEnvelope = soapPart.getEnvelope();

            /**
             * Get SOAP body.
             */
            SOAPBody soapBody = soapEnvelope.getBody();

            /**
             * Add child element with the specified name.
             */
            SOAPElement element = soapBody.addChildElement("HelloWorld");

            /**
             * Add text message
             */
            element.addTextNode("Welcome to SunOne Web Services!");

            soapMessage.saveChanges();

            /**
             * Construct a default SOAP connection factory.
             */
            SOAPConnectionFactory connectionFactory = SOAPConnectionFactory.newInstance();

            /**
             * Get SOAP connection.
             */
            SOAPConnection soapConnection = connectionFactory.createConnection();

            /**
             * Construct endpoint object.
             */
            URLEndpoint endpoint = new URLEndpoint (url);

            /**
             * Send SOAP message.
             */
            SOAPMessage resp = soapConnection.call(soapMessage, endpoint);

            /**
             * Print response to the std output.
             */
            resp.writeTo( System.out );

            /**
             * close the connection
             */
            soapConnection.close();

        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
        } catch (SOAPException soape) {
            soape.printStackTrace();
        }
    }

    public static void main (String args[]) {

        String url = "http://localhost:8080/imqSOAPexamples/SOAPEchoServlet";

        if (args.length > 0) {
            url = args[0];
        } else {
            System.out.println("Usage: " +
                "\tjava SendSOAPMessage <SOAP servlet url>\n" +
                "e.g.\n\tjava SendSOAPMessage http://localhost:8080/imqSOAPexamples/SOAPEchoServlet"
                );
            System.exit(1);
        }

        SendSOAPMessage ssm = new SendSOAPMessage();
        ssm.sendMessage(url);
    }

}
