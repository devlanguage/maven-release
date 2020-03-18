package org.basic.jdk.jdk8.reduceIfelse;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.basic.jdk.jdk8.reducingIfElse.Expression;
import org.basic.jdk.jdk8.reducingIfElse.Operator;
import org.basic.jdk.jdk8.reducingIfElse.Result;
import org.basic.jdk.jdk8.reducingIfElse.RuleEngine;

public class RuleEngineUnitTest {

    @Test
    public void whenNumbersGivenToRuleEngine_thenReturnCorrectResult() {
        Expression expression = new Expression(5, 5, Operator.ADD);
        RuleEngine engine = new RuleEngine();
        Result result = engine.process(expression);

        assertNotNull(result);
        assertEquals(10, result.getValue());
    }
}
