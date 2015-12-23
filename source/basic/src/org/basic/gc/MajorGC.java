package org.basic.gc;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Old generation 空间满是因为Young generation提升到Old generation的对象+Old generation的本来的大小已经接近或者超过了Old generation的大小。
 * 对于CMS GC，当Old generation空间使用率接近某一个比例，可以通过参数-XX:CMS InitialingOccupancyFraction，此参数表示Old generation的使用率，
 * 默认为68%。
 * 
 * Young generation对象提升到Old generation对象有以下三种情况：
 *      分配的对象大于eden空间的大小
 *      在Young generation代中经过了-XX:MaxTenuringThreshold次复制任然存活的对象
 *      Minor gc的时候，放不进to survivor的对象
 * 当Major GC以后，如果还没有足够的空间可以用的话，此时就会抛出java.lang.OutOfMemory：java heap space,当出现此错误的时候，说明可能存在内存泄露现象的，这时候就需要我们对程序进行检查看看什么地方存在内存泄露的。
 * 我们可以通过以下代码来模拟一下java.lang.OutOfMemory:java heap space的发生：
 * List<byte[]> buffer = new ArrayList<byte[]>();
 * buffer.add(new byte[10*1024*1024]);
 * 以上代码分配了一个10M的字节数组，我们通过以下的参数运行：
 * -verbose:gc -Xmn10M -Xms20M -Xmx20M -XX:+PrintGC
 * 或 -verbose:gc -Xms20M -Xmx20M -XX:NewRatio=1 -XX:+PrintGC
 * 以上参数指定Young generation的空间大小为10M，Old generation空间大小为10M。
 * 运行结果如下：
 * [GC 327K->134K(19456K), 0.0056516 secs]
 * [Full GC 134K->134K(19456K), 0.0178891 secs]
 * [Full GC 134K->131K(19456K), 0.0141412 secs]
 * Exception in thread "main" java.lang.OutOfMemoryError:   Java heap space
 *         at Test.main(Test.java:30)
 * 从运行结果可以看出，JVM进行了一次Minor gc和两次的Major gc，从Major gc的输出可以看出，gc以后old区使用率为134K，而字节数组为10M，加起来大于了old generation的空间，所以抛出了异常，如果调整-Xms21M,-Xmx21M,那么就不会触发gc操作也不会出现异常了。
 * 
 * </pre>
 * 
 * 
 */
public class MajorGC {
//    -XX:+PrintGCDetails 
    public static void main(String[] args) {

        List<byte[]> buffer = new ArrayList<byte[]>();
        buffer.add(new byte[10 * 1024 * 1023]);
        System.out.println(12);
    }
}
