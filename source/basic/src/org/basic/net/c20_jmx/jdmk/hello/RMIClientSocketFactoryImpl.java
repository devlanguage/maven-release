/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.jmx.examples.hello.RMIClientSocketFactory.java is created on 2008-1-8
 */
package org.basic.net.c20_jmx.jdmk.hello;

import java.io.IOException;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;

/**
 * 
 */
public class RMIClientSocketFactoryImpl implements RMIClientSocketFactory {

    public Socket createSocket(String host, int port) throws IOException {

        return new Socket(host, port);
    }

}
