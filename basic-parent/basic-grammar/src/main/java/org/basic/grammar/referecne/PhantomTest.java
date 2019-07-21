package org.basic.grammar.referecne;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

import org.junit.Test;


/**
 * 此时你会发现referenceQueue.remove(3000)输出了你想要的结果，这也说明虚引用 (PhantomReference), 这是一个最虚幻的引用类型 . 无论是从哪里都无法再次返回被虚引用所引用的对象 .
 * 虚引用在系统垃圾回收器开始回收对象时 , 将直接调用 finalize() 方法 , 但不会立即将其加入回收队列 . 只有在真正对象被 GC 清除时 , 才会将其加入 Reference 队列中去
 */
public class PhantomTest {

    @Test
    public void testReference() throws InterruptedException {

        Object object = new JavaRefObject("test");
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<Object>();
        Reference<Object> reference = new PhantomReference<Object>(object, referenceQueue);
        System.out.println(reference);
        System.out.println(reference.isEnqueued());
        object = null;
        System.gc();
        System.out.println(reference.isEnqueued());
        try {
            System.out.println(referenceQueue.remove(2000));
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // PhantomObject abc = new PhantomObject("test");
        // System.out.println(abc.getClass() + "@" + abc.hashCode());
        // final ReferenceQueue<PhantomObject> refQueue = new ReferenceQueue<PhantomObject>();
        //
        // new Thread() {
        //
        // public void run() {
        //
        // while (running) {
        // Object o = refQueue.poll();
        // if (o != null) {
        // try {
        // java.lang.reflect.Field referent = Reference.class.getDeclaredField("referent");
        // referent.setAccessible(true);
        // Object result = referent.get(o);
        // System.out.println("GC will collect: " + result.getClass() + "@" + result.hashCode());
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // }
        // }
        // }
        //
        // }.start();
        // PhantomReference<PhantomObject> phantomRef = new PhantomReference<PhantomObject>(abc, refQueue);
        // abc = null;
        // Thread.currentThread().sleep(3000);
        // System.gc();
        // Thread.currentThread().sleep(3000);
        // running = false;
    }

    static boolean running = true;

    public static void main(String[] args) throws InterruptedException {

        new PhantomTest().testReference();

    }
}
