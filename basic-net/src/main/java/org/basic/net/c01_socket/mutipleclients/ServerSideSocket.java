package org.basic.net.c01_socket.mutipleclients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 */
public class ServerSideSocket implements Runnable {

    private Socket socket = null;
    private BufferedReader input;
    private PrintWriter output;
    private boolean enable = true;
    private String connectName;
    StringBuffer buffer = new StringBuffer();

    public ServerSideSocket(Socket request, String welcomeMessage, String connectName) {

        this.connectName = connectName;
        this.socket = request;
        try {
            input = new BufferedReader(new InputStreamReader(request.getInputStream()));
            output = new PrintWriter(request.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        buffer.append("Welcome to the server!#@MESSAGE_NEXT_LINE@#");
        // 客户机连接欢迎词
        buffer.append("Now is: " + new java.util.Date() + " " + "Port:" + request.getLocalPort()
                + "#@MESSAGE_NEXT_LINE@#");
        buffer.append("What can I do for you?#@MESSAGE_NEXT_LINE@#");
        responseSocket(buffer);
    }

    final static Set<String> COMMANDS = new HashSet<String>();
    static {
        COMMANDS.add("query");
        COMMANDS.add("help");
        COMMANDS.add("haha");
        COMMANDS.add("quit");
    }

    public void run() {

        String responseMessage = "";
        while (enable) {
            String command = null;
            try {
                if (socket.isInputShutdown() || socket.isOutputShutdown()) {
                    enable = false;
                    break;
                }
                command = input.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                enable = false;
                break;
            }
            command = command == null ? "help" : command.trim();
            buffer.delete(0, buffer.length());
            if (!COMMANDS.contains(command)) {
                buffer.append("please key in correct command.").append("#@MESSAGE_NEXT_LINE@#");

            }
            if ("help".equalsIgnoreCase(command)) {
                buffer.append("help, ....").append("#@MESSAGE_NEXT_LINE@#");
                buffer.append("pls key in valid command.").append("#@MESSAGE_NEXT_LINE@#");
            } else if ("quit".equalsIgnoreCase(command)) {
                buffer.append("Bye�?Bye").append("#@MESSAGE_NEXT_LINE@#");
                enable = false;
            } else if ("haha".equalsIgnoreCase(command)) {
                buffer.append("Hello," + command).append("#@MESSAGE_NEXT_LINE@#");
            }
            responseSocket(buffer);
        }
        try {
            socket.close();
            System.out.println("Closed the " + connectName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void responseSocket(StringBuffer buffer) {

        String responseMessage = buffer.toString();
        output.print(responseMessage.replaceAll("#@MESSAGE_NEXT_LINE@#", "\r\n"));
        output.print(MultiThreadServer.RESPONSE_END_STRING);
        output.flush();
    }

    public static void main(String[] args) {

        System.err.println("zhan#@hah@#gsan".replaceAll("#@hah@#", "---"));
    }
}
