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
