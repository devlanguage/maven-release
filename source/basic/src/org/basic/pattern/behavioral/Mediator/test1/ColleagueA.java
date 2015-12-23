package org.basic.pattern.behavioral.Mediator.test1;

/**
 * A concrete colleague
 */

public class ColleagueA extends ColleagueAbstract {

    public ColleagueA(Mediator m) {
        super(m); this.type = COLLEAGUE_TYPE_A;
        med.register(this, COLLEAGUE_TYPE_A);
    }
}