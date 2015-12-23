package org.basic.gc;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Minor GC主要负责收集Young Generation，Minor GC一般在新生代不够用的情况下触发，比如我们一次性创建了很多对象等。
 *         List<byte[]> buffer = new ArrayList<byte[]>();
 *         for (int i = 0; i < 8 * 1024; i++) {
 *             buffer.add(new byte[1024]);
 *         }
 * 以上代码通过一个字节数组的List模拟触发Minor gc，设置JVM参数如下：
 * -verbose:gc -Xmn10M -Xms64M -Xmx64M -XX:+PrintGC
 * 设置以上参数以后，因为-Xmn=10M,默认-XX:SurvivorRatio=8 ，则eden的空间大小为8M，当eden对象大小超过8M的时候就会触发Minor gc.
 * 运行的结果如下：
 * [GC 8192K->8030K(64512K), 0.0243391 secs]
 * 从运行结果可以看出，gc前和gc后的eden区的占用情况，需要注意的是括号里（64512)这个数值时63M，它不包括一块Survivor 空间。
 * 这里需要注意的一点就是，如果创建的对象大于eden的大小，那么将不会通过Survivor空间复制，直接转移到old generation.
 * 调整以上代码如下： 
 * List<byte[]> buffer = new ArrayList<byte[]>();
 * buffer.add(new byte[8*1024*1024]);
 * 通过同样的JVM参数运行，则发现不会触发Minor gc，这是因为对象超过了eden的大小，从而直接分配到了Old generation.
 * 
 * </pre>
 * 
 * 
 */
public class MinorGC {

    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());

        System.out.println(8*1024*1024);
        List<byte[]> buffer = new ArrayList<byte[]>();
        for (int i = 0; i < 8 * 1024; i++) {
            buffer.add(new byte[1024]);
        }
        try {
            Thread.sleep( 2000*60*1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
