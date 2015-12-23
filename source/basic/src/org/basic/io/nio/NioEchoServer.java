package org.basic.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.Set;

/**
 * <pre>
 * nio是new io的简称，从jdk1.4就被引入了。现在的jdk已经到了1.6了，可以说不是什么新东西了。但其中的一些思想值得我来研究。这两天，我研究了下其中的套接字部分，
 * 有一些心得，在此分享。 首先先分析下：为什么要nio套接字？
 * 
 * nio的主要作用就是用来解决速度差异的
 * 举个例子：计算机处理的速度，和用户按键盘的速度。这两者的速度相差悬殊。
 * 如果按照经典的方法：一个用户设定一个线程，专门等待用户的输入，无形中就造成了严重的资源浪费：每一个线程都需要珍贵的cpu时间片
 * ，由于速度差异造成了在这个交互线程中的cpu都用来等待。 nio套接字是怎么做到的？
 * 
 * 其实，其中的思想很简单：轮询。
 *   一个线程轮询 + 多个input；  vs
 *   传统的方式： 有n个客户端就要有n个服务线程+一个监听线程，
 *   
 * 现在采取这种凡是，可以仅仅使用1个线程来代替n个服务线程以此来解决。 具体应用例子：
 * 在ftp的控制连接中，因为只有少量的字符命令进行传输，所以可以考虑利用这种轮询的方式实现，以节省资源
 * </pre>
 * 
 * @author ygong
 * 
 */
public class NioEchoServer {

    private static Selector selector = null;
    private static final int port = 8080;
    private static NioEchoServer instance = null;
    private ThreadLocal<StringBuffer> stringLocal = new ThreadLocal<StringBuffer>();

    private NioEchoServer() throws IOException {

        java.nio.channels.ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.socket().bind(new InetSocketAddress(port));
        serverChannel.configureBlocking(false);
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public synchronized static NioEchoServer getInstance() throws IOException {

        if (instance == null) {
            selector = Selector.open();
            instance = new NioEchoServer();
        }
        return instance;
    }

    public void start() throws IOException {

        int keyAdded = 0;
        while (true) {
            if (((keyAdded = selector.select()) == 0)) {
                continue;
            }
            // Get an iterator over the set of selected keys
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while (iter.hasNext()) { // step1 Is a new connection coming in?
                SelectionKey key = (SelectionKey) iter.next();

                // step1 Is a new connection coming in?
                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel channel = server.accept();

                    channel.configureBlocking(false);// 与Selector一起使用时，Channel必须处于非阻塞模式下。这意味着不能将FileChannel与Selector一起使用，因为FileChannel不能切换到非阻塞模式。而套接字通道都可以
                    channel.register(selector, SelectionKey.OP_READ);

                    sayHello(channel);
                }

                // step2 Is there data to read on this channel?
                if (key.isReadable()) {
//                    System.out.println(key.isWritable());
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    int count;
                    buffer.clear(); // Empty buffer
                    // Loop while data is available; channel is nonblocking
                    while ((count = socketChannel.read(buffer)) > 0) {
                        System.out.println("direct=" +  buffer.isDirect() + ", readonly=" + buffer.isReadOnly());
                        buffer.flip(); // Make buffer readable only data from 0 to current position will be readable
                        // Send the data; don't assume it goes all at once
                        Charset charset = Charset.forName("UTF-8");
                        while (buffer.hasRemaining()) {
                            System.out.println(charset.decode(buffer));
                        }
//                        CharBuffer outputChar = CharBuffer.allocate(20);
//                        outputChar.put("asdfffffff");
//                        ByteBuffer outputBytes = charset.encode(outputChar);
                        buffer.rewind();
                        socketChannel.write(buffer);                            
                        
                        // WARNING: the above loop is evil. Because
                        // it's writing back to the same nonblocking
                        // channel it read the data from, this code can
                        // potentially spin in a busy loop. In real life
                        // you'd do something more useful than this.
                        buffer.clear(); // Empty buffer

                    }
                    if (count < 0) {
                        // Close channel on EOF, invalidates the key
                        socketChannel.close();
                    }
                }

                // step3 Remove key from selected set; it's been handled
                iter.remove();
            }
        }
    }

    // ByteBuffer buffer = ByteBuffer.allocate(256);
    // SocketChannel socketChannel = (SocketChannel) key.channel();
    // socketChannel.read(buffer);
    // buffer.flip();
    // String temp = decode(buffer);
    // StringBuffer strBuffer = stringLocal.get();
    // if (strBuffer == null) {
    // strBuffer = new StringBuffer();
    // }
    // strBuffer.append(temp);
    // if (temp.equals("“r“n")) {
    // System.out.println(strBuffer.toString());
    // strBuffer = null;
    // }
    // stringLocal.set(strBuffer);

    /**
     * @param socketChannel
     */
    private ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

    private void sayHello(SocketChannel socketChannel) throws IOException {
        buffer.clear();
        buffer.put("Hi there!\r\n".getBytes());
        buffer.flip();
        socketChannel.write(buffer);
    }

    public String decode(ByteBuffer buffer) {

        Charset charset = null;
        CharsetDecoder decoder = null;
        CharBuffer charBuffer = null;
        try {
            charset = Charset.forName("UTF-8");
            decoder = charset.newDecoder();

            charBuffer = decoder.decode(buffer);
            return charBuffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args) {
        try {
            NioEchoServer.getInstance().start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}