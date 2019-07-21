package org.basic.grammar.referecne;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

import org.junit.Test;

public class WeakReferenceTest {
    @Test
    public void testWeakReferenc() throws Exception {
        Object referent = new JavaRefObject("test1");
        // SoftReference<Object> weakWr = new SoftReference<Object>(referent) run gc will not be clear out
        // String referent1 = "test1"; run gc will not be clear out
        WeakReference<Object> weakWr = new WeakReference<Object>(referent);
        System.out.println(weakWr.get());

        referent = null;

        System.out.println("before gc: " + weakWr.get());

        System.gc();

        if (weakWr.get() == null) {
            System.out.println("After gc: "+ " obj has been deallocated");
        } else {
            System.out.println("obj still there:  " + referent);
        }
    }

    /**
     * java.lang.ref.SoftReference
     * 
     * 　　Soft Reference 虽然和 Weak Reference 很类似，但是用途却不同。 被 Soft Reference 指到的对象，即使没有任何 Direct Reference，也不会被清除。一直要到 JVM
     * 内存不足时且 没有 Direct Reference 时才会清除，SoftReference 是用来设计 object-cache 之用的。如此一来 SoftReference 不但可以把对象 cache
     * 起来，也不会造成内存不足的错误 （OutOfMemoryError）。我觉得 Soft Reference 也适合拿来实作 pooling 的技巧。
     */
    @SuppressWarnings({ "unused" })
    public void testSoftReferenc() {
        Object obj = new Object();
        SoftReference<Object> sr = new SoftReference<>(obj);
        if (sr != null) {
            obj = sr.get();
        } else {
            obj = new Object();
            // sr.enqueue();
            sr = new SoftReference<>(obj);
        }
    }

    public static void main(String[] args) throws Exception {
        // new WeakReferenceTest().testSoftReferenc();
        new WeakReferenceTest().testWeakReferenc();

    }
}
