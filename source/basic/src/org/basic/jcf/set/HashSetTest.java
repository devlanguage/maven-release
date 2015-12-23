package org.basic.jcf.set;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class HashSetTest {

    static int BASE_INDEX = 1;
    static String BASE_NAME = "Test";

    private static class TestBean implements java.lang.Comparable<TestBean>{

        private int id;
        private String name;

        TestBean(int id, String name){
            this.id= id; this.name = name;
        }
        @Override
        public String toString() {

            return name+"_"+id;
        }

        @Override
        public int compareTo(TestBean o) {
            //For SortedMap, SortSet, Element will be not add if compareTo(obj) of the Object is the same to another
            //For others,  Element will be not add if equals(obj) of the Object is the same to another
            return this.id - o.id;
        }
        
        @Override
        public boolean equals(Object obj) {
            if(obj == null) return false;
            if(obj == this) return true;
            if(obj instanceof TestBean){
                return this.name.equals(((TestBean)obj).name);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return this.name.hashCode();
        }
    }

    public static void main(String[] args) {

        TestBean tb1 = new TestBean(1, BASE_NAME);
        TestBean tb2 = new TestBean(2, BASE_NAME);
        TestBean tb3 = new TestBean(3, BASE_NAME);
        TestBean tb4 = new TestBean(4, BASE_NAME);
        System.out.println("Add the Element:");
        System.out.println("\t" + tb2 + "\t" + tb1 + "\t" + tb3 + "\t" + tb4);
        Set<TestBean> hs1 = new HashSet<TestBean>();
        hs1.add(tb2);
        hs1.add(tb1);        
        hs1.add(tb3);      
        hs1.add(tb4);
        System.out.println("Display the Element in " + hs1.getClass() + ":");
        for (TestBean tb : hs1) {
            System.out.print("\t"+tb);
        }
        
        Set<TestBean> lhs1 = new LinkedHashSet<TestBean>();
        lhs1.add(tb2);
        lhs1.add(tb1);        
        lhs1.add(tb3);       
        lhs1.add(tb4);
        System.out.println("\nDisplay the Element in " + lhs1.getClass() + ":");
        for (TestBean tb : lhs1) {
            System.out.print("\t"+tb);
        }
        
        //All the element added to SortedSet should be implement interface Comparable
        SortedSet<TestBean> shs1 = new TreeSet<TestBean>();
        shs1.add(tb2);
        shs1.add(tb1);        
        shs1.add(tb3);
        shs1.add(tb4);
        System.out.println("\nDisplay the Element in " + shs1.getClass() + ":");
        for (TestBean tb : shs1) {
            System.out.print("\t" + tb);
        }
        
      //All the element added to SortedSet should be implement interface Comparable
        SortedMap<TestBean, String> tmap1 = new TreeMap<TestBean,String>();
        tmap1.put(tb2,"");
        tmap1.put(tb1,"");        
        tmap1.put(tb3,"");
        tmap1.put(tb4,"");
        System.out.println("\nDisplay the Element in " + tmap1.getClass() + ":");
        for (TestBean tb : tmap1.keySet()) {
            System.out.print("\t"+tb);
        }
    }
}
