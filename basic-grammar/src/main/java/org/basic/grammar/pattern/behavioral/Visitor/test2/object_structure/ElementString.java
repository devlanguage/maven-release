package org.basic.grammar.pattern.behavioral.Visitor.test2.object_structure;

import org.basic.grammar.pattern.behavioral.Visitor.test2.visitor.Visitor;

public class ElementString implements VisitableElement {
    private String value;

    public ElementString(String string) {
        value = string;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitString(this);
    }
}
