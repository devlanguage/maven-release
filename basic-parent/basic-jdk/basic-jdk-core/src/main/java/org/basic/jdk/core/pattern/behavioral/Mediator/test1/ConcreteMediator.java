package org.basic.jdk.core.pattern.behavioral.Mediator.test1;

/**
 * A concrete mediator
 */
public class ConcreteMediator implements Mediator {

    private ColleagueA a;
    private ColleagueB b;
    private ColleagueC c;

    public ConcreteMediator() {

    }

    public void register(Colleague colleague, String type) {

        if (type == Colleague.COLLEAGUE_TYPE_A) {
            a = (ColleagueA) colleague;
        } else if (type == Colleague.COLLEAGUE_TYPE_B) {
            b = (ColleagueB) colleague;
        } else if (type == Colleague.COLLEAGUE_TYPE_C) {
            c = (ColleagueC) colleague;
        }
    }

    public void changed(String type) {

        if (type == Colleague.COLLEAGUE_TYPE_A) {
            b.action();
            c.action();
        } else if (type == Colleague.COLLEAGUE_TYPE_B) {
            a.action();
            c.action();
        } else if (type == Colleague.COLLEAGUE_TYPE_C) {
            a.action();
            b.action();
        }
    }
}