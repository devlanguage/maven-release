package org.basic.pattern.behavioral.Mediator.test1;

/**
 * A concrete colleague
 */

public class ColleagueC extends ColleagueAbstract {

    public ColleagueC(Mediator m) {

        super(m);
        this.type = COLLEAGUE_TYPE_C;
        med.register(this, COLLEAGUE_TYPE_C);
    }

}