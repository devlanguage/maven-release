package org.basic.jdk.core.jcf;

import java.util.HashMap;
import java.util.HashSet;
import java.util.WeakHashMap;

class SetA {

    String value;
    String name;

    public SetA(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (null == obj)
            return false;
        SetA other = (SetA) obj;
        return other.value.equals(this.value);
    }

    @Override
    public int hashCode() {
//         return super.hashCode();
//        return 111;
//        return this.value.hashCode();
        return this.name.hashCode();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": [name=" + name + "]";

    }

    @Override
    public void finalize() throws Throwable {
        System.out.println(this + " call the finalize()");
    }
}

class TestHashMap<K, V> extends HashMap<K, V>{
    @Override
    public void finalize() throws Throwable {
        System.out.println("TestHashMap" + " call the finalize()");
    }
}
public class SetTest {

    public static void main(String[] args) {
        testHashcode();
        System.runFinalization();
        System.gc();
        new Thread(new Runnable() {

            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Wait Thread exit");
            }
        }).start();
    }

    public static void testHashcode() {
        HashSet<SetA> set = new HashSet<SetA>();
        SetA sa1 = new SetA("s1", "value1");
        SetA sa2 = new SetA("s2", "value1");
        // System.out.println("Added s1:" + set.add(sa1));
        // System.out.println("Added s2:" + set.add(sa2));
        System.out.println(set);        
        TestHashMap<SetA, String> hm = new TestHashMap<SetA, String>();
         System.out.println("Put the s1:" + hm.put(sa1, sa1.name));
         System.out.println("Put the s1:" + hm.put(sa2, sa2.name));
         System.out.println(hm.size());
         System.out.println("get the sa1:" + hm.get(sa1));
         System.out.println("get the sa2:" + hm.get(sa2));
//         sa1 = null;
//         sa2 = null;
        set = null;
        hm = null;
    }

    public SetA get() {
        return new SetA("s10", "value10");
    }
}
