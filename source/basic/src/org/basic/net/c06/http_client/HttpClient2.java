package org.basic.net.c06.http_client;

import java.net.*;
import java.io.*;

public class HttpClient2 {
    public static void main(String args[]) throws IOException {
        URL url = new URL("http://www.javathinker.org/hello.htm");
        URLConnection connection = url.openConnection();
        // 接收响应结果
        System.out.println("正文类型：" + connection.getContentType());
        System.out.println("正文长度：" + connection.getContentLength());

        InputStream in = connection.getInputStream(); // 读取响应正文
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int len = -1;

        while ((len = in.read(buff)) != -1) {
            buffer.write(buff, 0, len);
        }

        System.out.println(new String(buffer.toByteArray())); // 把字节数组转换为字符串
    }
}
