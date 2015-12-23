package org.basic.jdk.jdk7.e4_nio2_jsr203.newioapi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.channels.AsynchronousFileChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

/**
 * <pre>
 * 新的通道为套接字和文件提供了异步操作。
 * 
 * 当然，所有操作都是非阻塞的，但对所有异步通道，你也可以执行阻塞操作，所有异步I/O操作都有下列两种形式中的一种：
 * 
 * · 第一个返回java.util.concurrent.Future，代表等待结果，你可以使用Future特性等待I/O操作结束;
 * 
 * · 第二个是使用CompletionHandler创建的，当操作结束时，如回调系统，调用这个处理程序。
 * 
 * 下面是它们的一些例子，首 先来看看使用Future的例子：
 * 
 * AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get("Path to file")); 
 * ByteBuffer buffer = ByteBuffer.allocate(capacity);
 * 
 * Future result = channel.read(buffer, 100); //Read capacity bytes from the file starting at position 100
 * 
 * boolean done = result.isDone(); //Indicate if the result is already terminated
 * 
 * 你也可以等待结束：
 * 
 * int bytesRead = result.get();
 * 
 * 或等待超 时：
 * 
 * int bytesRead = result.get(10, TimeUnit.SECONDS); //Wait at most 10 seconds on the result
 * 
 * 再来看看使用CompletionHandler的例子：
 * 
 * Future result = channel.read(buffer, 100, null, new CompletionHandler(){ public void completed(Integer result, Object
 * attachement){ //Compute the result }
 * 
 * public void failed(Throwable exception, Object attachement){ //Answer to the fail } });
 * 
 * 正如你所看到的，你可以给操作一个附件，在操作末尾给 CompletionHandler，当然，你可以把null当作一个附件提供，你可以传递任何你想传递的，如用于
 * AsynchronousSocketChannel的Connection，或用于读操作的ByteBuffer。
 * 
 * Future result = channel.read(buffer, 100, buffer, new CompletionHandler(){ public void completed(Integer result,
 * ByteBuffer buffer){ //Compute the result }
 * 
 * public void failed(Throwable exception, ByteBuffer buffer){ //Answer to the fail } });
 * 
 * 正如你所看到 的，CompletionHandle也提供Future元素表示等待结果，因此你可以合并这两个格式。
 * 
 * 下面是NIO.2中的所有异步 通道：
 * 
 * · AsynchronousFileChannel：读写文件的异步通道，这个通道没有全局位置，因此每个读写操作都需要一个位置，你可以使用不同的线程同 时访问文件的不同部分，当你打开这个通道时，必须指定READ或WRITE选项; ·
 * AsynchronousSocketChannel：用于套接字的一个简单异步通道，连接、读/写、分散/聚集方法全都是异步的，读/写方法支持超时; ·
 * AsynchronousServerSocketChannel：用于ServerSocket的异步通道，accept()方法是异步的，当连接被接
 * 受时，调用CompletionHandler，这种连接的结果是一个AsynchronousSocketChannel; ·
 * AsynchronousDatagramChannel：用于数据报套接字的通道，读/写(连接)和接收/发送(无连接)方法是异步的。
 *  *
 * </pre>
 * 
 * 
 * 分组
 * 
 * 当你使用AsynchronousChannels时，有线程调用完整的处理程序，这些线程被绑定到一个 AsynchronousChannelGroup组，这个组包含一个线程池，它封装了所有线程共享的资源，你可以使用线程池来调用这些
 * 组，AsynchronousFileChannel可以使用它自己的组创建，将ExecutorService作为一个参数传递给open()方法，在
 * open方法中，通道是使用AsynchronousChannelGroup创建的，如果你不给它一个组，或传递一个NULL，它就会使用默认组。通道 被认为是属于组的，因此，如果组关闭了，通道也就关闭了。
 * 
 * 你可以使用ThreadFactory创建一个组：
 * 
 * ThreadFactory myThreadFactory = Executors.defaultThreadFactory();
 * 
 * AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup.withFixedThreadPool(25, threadFactory);
 * 
 * 或使用一个ExecutorService：
 * 
 * ExecutorService service = Executors.newFixedThreadPool(25); AsynchronousChannelGroup channelGroup =
 * AsynchronousChannelGroup.withThreadPool(service);
 * 
 * 而且你可以很容易地使用它：
 * 
 * AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open(channelGroup);
 * 
 * 你可 以使用在组上使用shutdown()方法关闭组，关闭之后，你就不能使用这个组创建更多的通道，当所有通道关闭后，组也终止了，处理程序结束，资源也释 放了。
 * 
 * 当你使用任何类型的池和CompletionHandler时，你必须要注意一点，不要在CompletionHandler内
 * 使用阻塞或长时间操作，如果所有线程都被阻塞，整个应用程序都会被阻塞掉。如果你有自定义或缓存的线程池，它会使队列无限制地增长，最终导致 OutOfMemoryError。
 * 
 * 我想我把新的异步I/O API涵盖的内容讲得差不多了，当然这不是一两句话可以说清楚的，也不是每一个人都会使用到它们，但在某些时候它确实很有用，Java支持这种I/O操作
 * 终归是一件好事。如果我的代码中有什么错误我表示道歉，因为我也是刚刚才接触。
 * 
 * 原文出处：www.baptiste-wicht.com/2010/04/java-7-new-io-features-asynchronous-operations-multicasting-random-access-with-
 * jsr-203-nio-2/
 * 
 * 原文名：Java 7 : New I/O features (Asynchronous operations, multicasting, random access) with JSR 203 (NIO.2)
 */
@SuppressWarnings("unused")
public class AsynchronousFileChannelTest {
    
    @Test
    public void AsynchronousFileChannel() throws InterruptedException, ExecutionException, IOException, TimeoutException, URISyntaxException {
        int capacity = 512;
        java.nio.file.Path filePath = java.nio.file.Paths.get(AsynchronousFileChannelTest.class.getResource("JDK7_newio.txt").toURI());
        java.nio.channels.AsynchronousFileChannel channel = AsynchronousFileChannel.open(filePath);
        java.nio.ByteBuffer buffer = java.nio.ByteBuffer.allocate(capacity);

        // Read capacity bytes from the file starting at position 100
        java.util.concurrent.Future<Integer> result = channel.read(buffer, 100);

        boolean done = result.isDone(); // Indicate if the result is already terminated

        // 你也可以等待结束：
        Integer bytesRead1 = result.get();

        // 或等待超 时：
        Integer bytesRead2 = result.get(10, TimeUnit.SECONDS); // Wait at most 10 seconds on the result

        // 再来看看使用CompletionHandler的例子：
        long position = 100;
        Object attachment = null;
        channel.read(buffer, position, attachment, new java.nio.channels.CompletionHandler<Integer, Object>() {
            public void completed(Integer resultx, Object attachement) { // Compute the result
                System.out.println(resultx);
            }

            public void failed(Throwable exception, Object attachement) { // Answer to the fail
            }

        });
    }
}
