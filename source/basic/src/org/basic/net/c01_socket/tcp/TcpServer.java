package org.basic.net.c01_socket.tcp;

import org.basic.net.c01_socket.SocketConstant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class SocketServerThread implements Runnable {

    private Socket servant;

    public SocketServerThread(Socket servant) {

        this.servant = servant;
    }

    public void run() {

        java.io.PrintWriter output;
        java.io.BufferedReader input;
        while (true) {
            try {
                output = new PrintWriter(servant.getOutputStream(), true);
                input = new BufferedReader(new InputStreamReader(servant.getInputStream()));

                String received = input.readLine();
                System.out.println(received);

                output.println("Server ACK:" + received);

            } catch (Exception e) {
                e.printStackTrace();
                try {
                    servant.close();
                } catch (IOException e1) {
                    e.printStackTrace();
                }
                return;
            }
        }
    }
}

public class TcpServer {

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(SocketConstant.SERVER_PORT);
            final Socket servant = serverSocket.accept();
            System.out.println(servant.getSoLinger());

            new Thread(new SocketServerThread(servant)).start();
            new Thread(new Runnable() {

                public void run() {

                    while (true) {
                        try {
                            java.io.PrintWriter output = new PrintWriter(servant.getOutputStream(),
                                    true);
                            output.flush();
                            Thread.sleep(5000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.out.println();
                        System.out.println("isBound: " + servant.isBound());
                        System.out.println("isClosed: " + servant.isClosed());
                        System.out.println("isConnected: " + servant.isConnected());
                        System.out.println("isInputShutdown: " + servant.isInputShutdown());
                        System.out.println("isOutputShutdown: " + servant.isOutputShutdown());
                    }
                }

            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
