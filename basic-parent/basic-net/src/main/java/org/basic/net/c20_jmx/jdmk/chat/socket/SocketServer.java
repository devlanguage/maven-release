package org.basic.net.c20_jmx.jdmk.chat.socket;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import org.basic.common.util.StreamUtils;
import org.basic.common.util.StringUtils;
import org.basic.net.c20_jmx.jdmk.chat.user.ChatConstants;

/**
 * 
 */
public class SocketServer {

    private ServerSocket serverSocket;

    public SocketServer() throws IOException {

        serverSocket = new ServerSocket(ChatConstants.SERVER_SOCKET_PORT, 3);
        boolean isOpen = serverSocket.isBound() && !serverSocket.isClosed();
        if (isOpen) {
            System.err.println("isOpen=" + isOpen);
        }

        // 连接请求队列的长度为3
        System.out.println("服务器启�? port=" + ChatConstants.SERVER_SOCKET_PORT);
    }

    final static String HTTP_RESPONSE_TEST = "" + "HTTP/1.1 200 OK\n"
            + "Date: Thu, 10 Jan 2008 06:04:46 GMT\n" + "Server: BWS/1.0\n"
            + "Content-Length: 30\n" + "Content-Type: text/html\n" + "Cache-Control: private\n"
            + "Expires: Thu, 10 Jan 2008 06:04:46 GMT\n" + "Content-Encoding: UTF-8\n" + "<HTML>"
            + "<BODY>" + "hell" + "</BODY>" + "</HTML>";

    public void start() {

        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                // 从连接请求队列中取出�?��连接
                System.out.println("New connection accepted " + socket.getInetAddress() + ":"
                        + socket.getPort());
                InputStream ins = socket.getInputStream();
                System.out.println("Received: " + StreamUtils.streamToString(ins));
                ins.close();

                OutputStream out = socket.getOutputStream();
                String http_response = getHttpResponse();
                out.write(http_response.getBytes());
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (socket != null)
                        socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getHttpResponse() throws IOException {

        FileInputStream ins = null;
        String response = "";
        try {
            ins = new FileInputStream("test.txt");
            response = StreamUtils.streamToString(ins);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ins != null)
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        System.out.println(response);
        return response;
    }

    public static Vector<DataOutputStream> CLIENT_OUTPUT_STREAM = new Vector<DataOutputStream>();;

    public static void main(String args[]) throws Exception {

        ServerSocket server = new ServerSocket(2000);
        System.out.println("Socket Server Started");
        while (true) {
            Socket socket = server.accept();
            new Thread(new ServerSideProcessor(socket)).start();
        }
    }

}