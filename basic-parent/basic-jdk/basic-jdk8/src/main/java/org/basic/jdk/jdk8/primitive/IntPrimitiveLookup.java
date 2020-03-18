package org.basic.jdk.jdk8.primitive;

import org.openjdk.jmh.annotations.*;

@State(Scope.Thread)
public class IntPrimitiveLookup extends Lookup {

    private int[] elements;
    private final int pivot = 2;

    @Setup
    @Override
    public void prepare() {
        int common = 1;
        elements = new int[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = common;
        }
        elements[s - 1] = pivot;
    }

    @TearDown
    @Override
    public void clean() {
        elements = null;
    }


    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public int findPosition() {
        int index = 0;
        while (pivot != elements[index]) {
            index++;
        }
        return index;
    }

    @Override
    public String getSimpleClassName() {
        return IntPrimitiveLookup.class.getSimpleName();
    }


}
