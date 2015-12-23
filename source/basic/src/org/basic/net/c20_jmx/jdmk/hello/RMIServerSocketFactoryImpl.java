/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.jmx.examples.hello.RMIServerSocketFactoryImpl.java is created on 2008-1-8
 */
package org.basic.net.c20_jmx.jdmk.hello;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.server.RMIServerSocketFactory;

/**
 * 
 */
public class RMIServerSocketFactoryImpl implements RMIServerSocketFactory {

    public ServerSocket createServerSocket(int port) throws IOException {

        return new ServerSocket(port);
    }

}
