package org.basic.jdk.core.jcf;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class ListTest {

    public static void main(String[] args) {

        List<Object> a = null;
        a = testLinkedList();
        printList(a);
        a = testVector();
        printList(a);
        a = testArray();
        printList(a);
    }

    private static void printList(List<Object> list) {

        for (Object obj : list) {
            System.out.println(obj);

        }
    }

    private static List<Object> testVector() {

        Vector<Object> list = new Vector<Object>();
        list.add(null);
        list.add(null);
        list.add("Vector");
        return list;
    }

    private static List<Object> testArray() {

        List<Object> list = new ArrayList<Object>();
        list.add(null);
        list.add(null);
        list.add("ArrayList");
        return list;
    }

    private static List<Object> testLinkedList() {

        List<Object> list = new LinkedList<Object>();
        list.add(null);
        list.add(null);
        list.add("LinkedList");
        return list;
    }

}
