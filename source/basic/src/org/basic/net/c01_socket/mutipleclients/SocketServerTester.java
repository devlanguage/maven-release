/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.threads.socket2.SocketServerTester.java is created on 2008-3-12
 */
package org.basic.net.c01_socket.mutipleclients;


/**k
 * 
 */
public class SocketServerTester {

    public static void main(String[] args) {

        SocketServerTester tester = new SocketServerTester();
        tester.startSocketServer();
    }

    private void startSocketServer() {

        new MultiThreadServer();
    }
}
