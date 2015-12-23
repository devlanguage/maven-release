/**
 * The file org.java.arithmetic.data.SortBase.java was created by yongjie.gong on 2008-6-2
 */
package org.basic.arithmetic.data;

/**
 * @author feiye
 * 
 */
public abstract class SortBase {

    public final static Person[] PERSON_LIST = ArithmeticData.PERSON_LIST;

    protected void beforeClass() {

        System.out.println("Tested method: beforeClass()");
        for (Person person : PERSON_LIST) {
            System.out.println(person);
        }
    }

    protected void afterClass() {

        System.out.println("\n\nTested method: afterClass()");
        for (Person person : PERSON_LIST) {
            System.out.println(person);
        }
    }

    /**
     * If persons[index1].getId()>persons[index2].getId(), persons[index1] will
     * move to position index1-1;
     * 
     * @param persons
     * @param index1
     * @param index2
     */
    public void swap(Person[] persons, int index1, int index2) {

        Person temp = persons[index2];
        persons[index2] = persons[index1];
        persons[index1] = temp;

    }
}
