package org.basic.jdk.jdk10;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class Java10FeaturesUnitTest {

    private static List<Integer> someIntList;
    @Test()
    public void whenModifyCopyOfList_thenThrowshException() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            List<Integer> copyList = List.copyOf(Arrays.asList(1, 2, 3, 4));
            copyList.add(77);
        });
    }
    @BeforeAll
    public static void setup() {
        someIntList = new ArrayList<>();

        someIntList.add(1);
        someIntList.add(2);
        someIntList.add(3);
    }

    @Test
    public void whenVarInitWithString_thenGetStringTypeVar() {
        var message = "Hello, Java 10";
        assertTrue(message instanceof String);
    }

    @Test
    public void whenVarInitWithAnonymous_thenGetAnonymousType() {
        var obj = new Object() {
        };
        assertFalse(obj.getClass().equals(Object.class));
    }

    @Test()
    public void whenModifyCopyOfList_thenThrowsException() {

        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            List<Integer> copyList = List.copyOf(someIntList);
            copyList.add(4);
        });
    }

    @Test()
    public void whenModifyToUnmodifiableList_thenThrowsException() {

        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            List<Integer> evenList = someIntList.stream().filter(i -> i % 2 == 0)
                    .collect(Collectors.toUnmodifiableList());
            evenList.add(4);

        });
    }

    @Test
    public void whenListContainsInteger_OrElseThrowReturnsInteger() {
        Integer firstEven = someIntList.stream().filter(i -> i % 2 == 0).findFirst().orElseThrow();
        is(firstEven).equals(Integer.valueOf(2));
    }
}
