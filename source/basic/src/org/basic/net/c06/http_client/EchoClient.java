package org.basic.net.c06.http_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import org.basic.net.c06.http_client.echo.EchoContentHandlerFactory;
import org.basic.net.c06.http_client.echo.EchoURLConnection;
import org.basic.net.c06.http_client.echo.EchoURLStreamHandlerFactory;

public class EchoClient {
    public static void main(String args[]) throws IOException {
        // 设置URLStreamHandlerFactory
        URL.setURLStreamHandlerFactory(new EchoURLStreamHandlerFactory());
        // 设置ContentHandlerFactory
        URLConnection.setContentHandlerFactory(new EchoContentHandlerFactory());
        URL url = new URL("echo://localhost:8000");
        EchoURLConnection connection = (EchoURLConnection) url.openConnection();
        connection.setDoOutput(true);
        PrintWriter pw = new PrintWriter(connection.getOutputStream(), true);
        while (true) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String msg = br.readLine();
            pw.println(msg); // 向服务器发送消息
            String echoMsg = (String) connection.getContent(); // 读取服务器返回的消息
            System.out.println(echoMsg);
            if (echoMsg.equals("echo:bye")) {
                connection.disconnect(); // 断开连接
                break;
            }
        }
    }
}
