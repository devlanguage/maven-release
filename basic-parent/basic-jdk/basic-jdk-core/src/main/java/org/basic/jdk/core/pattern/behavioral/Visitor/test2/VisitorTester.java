package org.basic.jdk.core.pattern.behavioral.Visitor.test2;

import org.basic.jdk.core.pattern.behavioral.Visitor.test2.object_structure.ElementFloat;
import org.basic.jdk.core.pattern.behavioral.Visitor.test2.object_structure.ElementString;
import org.basic.jdk.core.pattern.behavioral.Visitor.test2.object_structure.VisitableElement;
import org.basic.jdk.core.pattern.behavioral.Visitor.test2.visitor.ConcreteVisitor;
import org.basic.jdk.core.pattern.behavioral.Visitor.test2.visitor.Visitor;

public class VisitorTester {
    public static void main(String[] args) {
        VisitableElement[] elements = new VisitableElement[] { new ElementString("Test1"), new ElementFloat(11F) };
        Visitor[] vs = new Visitor[] { new ConcreteVisitor() };

        for (VisitableElement e : elements) {
            for (Visitor v : vs) {
                e.accept(v);
            }
        }

    }
}
