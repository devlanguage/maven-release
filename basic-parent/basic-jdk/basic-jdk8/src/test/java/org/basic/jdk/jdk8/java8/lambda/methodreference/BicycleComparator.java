package org.basic.jdk.jdk8.java8.lambda.methodreference;

import java.util.Comparator;

public class BicycleComparator implements Comparator<Bicycle> {

    @Override
    public int compare(Bicycle a, Bicycle b) {
        return a.getFrameSize()
            .compareTo(b.getFrameSize());
    }

}
