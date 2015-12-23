package org.basic.pattern.behavioral.Mediator.test1;

/**
 * A concrete colleague
 */

public class ColleagueB extends ColleagueAbstract {

    public ColleagueB(Mediator m) {

        super(m);
        this.type = COLLEAGUE_TYPE_B;
        med.register(this, COLLEAGUE_TYPE_B);
    }

}