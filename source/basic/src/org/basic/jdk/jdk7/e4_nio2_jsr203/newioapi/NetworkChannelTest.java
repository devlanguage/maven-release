package org.basic.jdk.jdk7.e4_nio2_jsr203.newioapi;

import java.io.IOException;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <pre>
 * 现在所有网络通道都实现了新的NetworkChannel接口，你可以轻松
 * 绑定套接字 管道，设置和查询套接字选项，此外，套接字选项也被扩展了，因此你可以使用操作系统特定的选项，对于高性能服务器这非常有用。
 * 
 * </pre>
 */
@SuppressWarnings("unused")
public class NetworkChannelTest {
    public void NetworkChannelTest() throws IOException {
        java.util.concurrent.ThreadFactory threadFactory = java.util.concurrent.Executors.defaultThreadFactory();
        AsynchronousChannelGroup channelGroup1 = AsynchronousChannelGroup.withFixedThreadPool(25, threadFactory);

        // 或使用一个ExecutorService：
        ExecutorService service = Executors.newFixedThreadPool(25);
        AsynchronousChannelGroup channelGroup2 = AsynchronousChannelGroup.withThreadPool(service);

        AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open(channelGroup1);
    }

    public static void main(String[] args) {

    }
}
