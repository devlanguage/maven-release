package org.basic.pattern.structural.Decorator.test2;

import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;

public class WorkDecorator implements Work {

    private Work work;

    private List<String> others = new ArrayList<String>();

    public WorkDecorator(Work work) {

        this.work = work;
        others.add("额外功能:");
        others.add("额外功能:");
    }

    public void insert() {

        otherMethod();

        work.insert();
    }

    public void otherMethod() {

        ListIterator listIterator = others.listIterator();

        while (listIterator.hasNext()) {
            System.out.println(listIterator.next() + " ");
        }
    }
}