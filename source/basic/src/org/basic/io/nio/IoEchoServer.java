package org.basic.io.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class IoEchoServer implements Runnable {

    // ThreadLocal<Socket> localSocket = new ThreadLocal<Socket>();
    Map<String, Socket> socketMap = Collections.synchronizedMap(new HashMap<String, Socket>());
    int threadCounter = 0;

    synchronized private int getCounter() {

        return threadCounter++;
    }

    public IoEchoServer() throws IOException {

        ServerSocket server = new ServerSocket(1984);
        while (true) {
            Socket socket = server.accept();
            // happened in the main thread.
            // localSocket.set(socket);
            String threadName = "---Thread" + getCounter() + "---";
            socketMap.put(threadName, socket);
            this.start(threadName);
        }
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String args) throws IOException {

        new IoEchoServer();
    }

    public void run() {

        try {
            Socket socket = socketMap.get(Thread.currentThread().getName());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // PrintWriter out = new PrintWriter(socket.getOutputStream());
            String buffer = null;
            while (!"END".equals(buffer)) {
                buffer = in.readLine();
                System.out.println(buffer);
            }
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(String threadName) {

        new Thread(this, threadName).start();
    }
}