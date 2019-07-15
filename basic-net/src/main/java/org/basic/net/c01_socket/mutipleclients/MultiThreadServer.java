package org.basic.net.c01_socket.mutipleclients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

public class MultiThreadServer {

    public static final int RECEIVE_PORT = 3083;
    public static final String RESPONSE_END_STRING = "command>";
    private String helloString = null;

    private static int count = 0;

    private static ReentrantLock lock = new ReentrantLock();

    private int getCount() {

        int ret = 0;
        try {

            lock.lock();
            ret = count;

        } finally {
            lock.unlock();
        }

        return ret;
    }

    private void increaseCount() {

        try {
            lock.lock();
            ++count;
        } finally {
            lock.unlock();
        }
    }

    private ServerSocket serverListenSocket = null;
    // 用户连接的�?信套接字
    private Socket request = null;
    BufferedReader input;
    // 输入�?    PrintWriter output;

    public MultiThreadServer() {

        try {
            serverListenSocket = new ServerSocket(RECEIVE_PORT);
            serverListenSocket.setReuseAddress(true);
            startListener();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static ExecutorService threadPool = null;
    static {
        threadPool = Executors.newFixedThreadPool(10);
        // threadPool = Executors.newCachedThreadPool();

        // new ThreadPoolExecutor().execute(new Runnable);
        Future<String> future = threadPool.submit(new TimeConsumingTask());
        System.out.println("let's do soemthing other".getBytes());
        String result;
        try {
            result = future.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    private void startListener() {

        // ServerSocket的实�?        Socket request = null;
        // 用户请求的套接字
        Runnable receiveThread = null;
        try {
            // 初始化ServerSocket
            System.out.println("Welcome to the server!");
            System.out.println(new Date());
            System.out.println("The server is ready!");
            System.out.println("Port: " + RECEIVE_PORT);
            int i = 1;
            while (true) {
                // 等待用户请求
                request = serverListenSocket.accept();
                String welcomeMessage = "Welcome to you!";
                String connectName = "Connect-" + i;
                // 接收客户机连接请�?                receiveThread = new ServerSideSocket(request, welcomeMessage, connectName);
                // receiveThread = new ServerSideThread(request);

                // 生成serverThread的实�?                // 启动serverThread线程
                threadPool.execute(receiveThread);
                System.out.println("Received the " + connectName + ":" + request.getLocalAddress());

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
