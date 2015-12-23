package org.basic.pattern.behavioral.Visitor.test2.object_structure;

import org.basic.pattern.behavioral.Visitor.test2.visitor.Visitor;

public interface VisitableElement {

    public void accept(Visitor visitor);
}
