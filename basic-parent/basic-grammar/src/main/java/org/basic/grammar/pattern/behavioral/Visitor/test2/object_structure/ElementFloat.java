package org.basic.grammar.pattern.behavioral.Visitor.test2.object_structure;

import org.basic.grammar.pattern.behavioral.Visitor.test2.visitor.Visitor;

public class ElementFloat implements VisitableElement {
    private Float value;

    public ElementFloat(Float float1) {
        value = float1;
    }

    public Float getValue() {
        return value;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitFloat(this);

    }
}
