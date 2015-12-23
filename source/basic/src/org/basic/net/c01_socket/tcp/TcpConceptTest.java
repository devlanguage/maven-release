package org.basic.net.c01_socket.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import org.basic.net.c01_socket.SocketConstant;

class SocketClientThread implements Runnable {

    private Socket client;

    public SocketClientThread(Socket client) {

        this.client = client;
    }

    public void run() {

        java.util.Scanner userInput = new Scanner(System.in);
        java.io.PrintWriter output;
        java.io.BufferedReader input;

        try {
            while (true) {
                output = new PrintWriter(client.getOutputStream(), true);
                input = new BufferedReader(new InputStreamReader(client.getInputStream()));

                String sendMsg = userInput.nextLine();
                output.println(sendMsg);

                String received = input.readLine();
                System.out.println(received);

                System.out.println("SoLinger:   " + client.getSoLinger());
                System.out.println("SoTimeout:  " + client.getSoTimeout());
                System.out.println("tcpNoDelay: " + client.getTcpNoDelay());
                System.out.println("tcpNoDelay: " + client.getKeepAlive());
                // System.out.println("isBound: " + client.isBound());
                // System.out.println("isClosed: " + client.isClosed());
                // System.out.println("isConnected: " + client.isConnected());
                // System.out.println("isInputShutdown: " + client.isInputShutdown());
                // System.out.println("isOutputShutdown: " + client.isOutputShutdown());
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                System.out.println("Socket exit");
                client.close();
            } catch (IOException e1) {
                e.printStackTrace();
            }
        }

    }
}

public class TcpConceptTest {

    public static void main(String[] args) {

        try {
            Socket clientSocket = new Socket(SocketConstant.SERVER_HOST, SocketConstant.SERVER_PORT);
            new Thread(new SocketClientThread(clientSocket)).start();

            System.out.println("client ok");
            System.out.println("************************************************");
            System.out.println("");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
