package org.basic.jdk.jdk8.reduceIfelse;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import org.basic.jdk.jdk8.reducingIfElse.AddCommand;
import org.basic.jdk.jdk8.reducingIfElse.Calculator;
import org.basic.jdk.jdk8.reducingIfElse.Operator;

public class CalculatorUnitTest {

    @Test
    public void whenCalculateUsingStringOperator_thenReturnCorrectResult() {
        Calculator calculator = new Calculator();
        int result = calculator.calculate(3, 4, "add");
        assertEquals(7, result);
    }

    @Test
    public void whenCalculateUsingEnumOperator_thenReturnCorrectResult() {
        Calculator calculator = new Calculator();
        int result = calculator.calculate(3, 4, Operator.valueOf("ADD"));
        assertEquals(7, result);
    }

    @Test
    public void whenCalculateUsingCommand_thenReturnCorrectResult() {
        Calculator calculator = new Calculator();
        int result = calculator.calculate(new AddCommand(3, 7));
        assertEquals(10, result);
    }
}
