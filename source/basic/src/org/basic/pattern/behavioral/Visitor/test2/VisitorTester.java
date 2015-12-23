package org.basic.pattern.behavioral.Visitor.test2;

import org.basic.pattern.behavioral.Visitor.test2.object_structure.ElementFloat;
import org.basic.pattern.behavioral.Visitor.test2.object_structure.ElementString;
import org.basic.pattern.behavioral.Visitor.test2.object_structure.VisitableElement;

public class VisitorTester {
    public static void main(String[] args) {
        VisitableElement[] elements = new VisitableElement[] { new ElementString("Test1"), new ElementFloat(11F) };
        org.basic.pattern.behavioral.Visitor.test2.visitor.Visitor[] vs = new org.basic.pattern.behavioral.Visitor.test2.visitor.Visitor[] { new org.basic.pattern.behavioral.Visitor.test2.visitor.ConcreteVisitor() };

        for (VisitableElement e : elements) {
            for (org.basic.pattern.behavioral.Visitor.test2.visitor.Visitor v : vs) {
                e.accept(v);
            }
        }

    }
}
