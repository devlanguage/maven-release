/**
 * The file org.java.arithmetic.ArithmeticData.java was created by yongjie.gong on 2008-6-2
 */
package org.basic.arithmetic.data;

import java.util.Set;

/**
 * @author feiye
 * 
 */
public class ArithmeticData {

    /*
     * new Person[] { new Person(100), new Person(110), new Person(102), new
     * Person(106), new Person(104), new Person(109), new Person(107), new
     * Person(105), new Person(103), new Person(101), new Person(108) };
     */
    public static Person[] PERSON_LIST = null;

    public final static int TEST_LENGTH = 20;
    static {
        Set<Person> personList = new java.util.LinkedHashSet<Person>(TEST_LENGTH);
        int i = 0;
        while (++i < TEST_LENGTH) {
            Person p = new Person((int) (TEST_LENGTH * Math.random()));
            personList.add(p);
        }
        PERSON_LIST = personList.toArray(new Person[0]);

    }
}
