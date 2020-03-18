package org.basic.jdk.core.jcf;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;

class A {
    int id;

    public A(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}

public class MapTest {

    public static void main(String[] args) {

        Map<Object, Object> a = null;
        a = testHashMap();
        printMap(a);
        a = testHashtable();
        printMap(a);
        a = testTreeMap();
        printMap(a);

        Map<String, String> ha = new Hashtable<>(13);
        for (int i = 0; i < 1 << 8; i++) {
            ha.put("asdf-" + i, "2233");
        }

    }

    private static void printMap(Map<Object, Object> map) {

        for (Entry<Object, Object> entry : map.entrySet()) {
            System.out.println(entry);
        }
    }

    private static Map<Object, Object> testHashMap() {

        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put(null, new A(12));
        map.put(null, new A(12)); // Override the first group��
        return map;
    }

    /**
     * Make sure none of key,value is null, otherwise, NullPointerException
     * 
     * @return
     */
    private static Map<Object, Object> testHashtable() {

        Map<Object, Object> map = new Hashtable<Object, Object>();
        // map.put(null, "zhangsan");
        // map.put("test1", null);
        // map.put("test2", null); //throw NullPointerException, value now allowed be null
        map.put("test3", "testzhangsan");
        return map;
    }

    private static Map<Object, Object> testTreeMap() {
        Object key1 = "user1";
        int size = 100;
        WeakHashMap<Object, String> users = new WeakHashMap<Object, String>();
        for (int i = 0; i < size; ++i) {
            users.put("key" + i, "value");
        }
//        for (int i = 0; i < size; ++i) {
            System.gc();
            System.out.println(users.size()); // key1=null
            System.gc();
            System.out.println(users.size()); // key1=null
            System.gc();
            System.out.println(users.size()); // key1=null
            System.gc();
            System.out.println(users.size()); // key1=null
//        }

        Map<Object, Object> map = new HashMap<Object, Object>();
        return map;

    }

}
