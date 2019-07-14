/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.pattern.ActionType.Mediator.test1.ColleagueAbstract.java is created on 2008-9-16
 */
package org.basic.grammar.pattern.behavioral.Mediator.test1;

/**
 * 
 */
public abstract class ColleagueAbstract implements Colleague {

    protected Mediator med;
    protected String type;

    public ColleagueAbstract(Mediator m) {

        med = m;
    }

    public void change() {

        System.out.println("-----  " + type + " changed now !  -----");
        med.changed(type);
    }

    public void action() {

        System.out.println("  " + type + " is changed by mediator ");
    }

}
