package org.basic.net.c01_socket.simple;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    public final static int SERVER_PORT = 18080;

    public static void main(String[] args) throws IOException {

        ServerSocket s = new ServerSocket(SERVER_PORT);

        Socket socket = null;
        try {
            System.out.println("Socket Server started in Port: " + SERVER_PORT);
            socket = s.accept();
            socket.setKeepAlive(true);

            System.out.println("accept:" + socket);
            BufferedReader socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter socketOutput = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
                    .getOutputStream())), true);

            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                // read message from client
                String str = socketInput.readLine();
                // if (str.equals("q"))
                // break;
                System.out.println("Client Say： " + str);

                // Read from console and send it to client
                System.out.print("Read from console and send it to client");
                String input = console.readLine().trim();
                socketOutput.println(input);
                socketOutput.flush();
            }
        } finally {
            System.out.println("Closing...");
            socket.close();
        }
    }

}
