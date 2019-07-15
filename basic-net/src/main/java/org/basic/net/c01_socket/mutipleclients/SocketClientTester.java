package org.basic.net.c01_socket.mutipleclients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * 
 */
public class SocketClientTester {

    public static void main(String[] args) {

        SocketClientTester tester = new SocketClientTester();
        try {
            tester.startTest();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Socket server;

    private void startTest() throws UnknownHostException, IOException {

        server = new Socket("localhost", MultiThreadServer.RECEIVE_PORT);
        BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
        PrintWriter out = new PrintWriter(server.getOutputStream());
        Scanner scanner = new Scanner(System.in);
        System.out.println(readFromSocketInput(in).toString());
        while (true) {
            String str = scanner.nextLine();
            out.println(str);
            out.flush();
            if (str.equals("end")) {
                break;
            }

            System.out.println(readFromSocketInput(in).toString());
        }
        server.close();
    }

    StringBuffer buffer = new StringBuffer();
    byte[] cache = new byte[1024];

    public synchronized StringBuffer readFromSocketInput(BufferedReader input) {

        buffer = new StringBuffer();
        // String response = "";
        // while (!response.endsWith("command>")) {
        // try {
        // response = input.readLine();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // buffer.append("\n").append(response);
        // }

        InputStream ins;
        try {
            ins = server.getInputStream();
            int received = -1;
            String response = "";
            while (!response.endsWith(MultiThreadServer.RESPONSE_END_STRING)
                    && (received = ins.read(cache, 0, 1024)) != -1) {
                response = new String(cache, 0, received);
                buffer.append(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buffer;

    }
}
