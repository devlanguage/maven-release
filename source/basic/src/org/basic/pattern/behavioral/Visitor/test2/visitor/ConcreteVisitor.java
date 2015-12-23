package org.basic.pattern.behavioral.Visitor.test2.visitor;

import java.util.Collection;

import org.basic.pattern.behavioral.Visitor.test2.object_structure.ElementFloat;
import org.basic.pattern.behavioral.Visitor.test2.object_structure.ElementString;
import org.basic.pattern.behavioral.Visitor.test2.object_structure.VisitableElement;

public class ConcreteVisitor extends Visitor {

    @Override
    public VisitableElement visitString(ElementString stringE) {
        System.out.println("''''" + stringE.getValue() + "''''");
        return stringE;
    }

    @Override
    public VisitableElement visitFloat(ElementFloat floatE) {
        System.out.println(floatE.getValue().toString() + "f");
        return floatE;
    }

    @Override
    public Collection<VisitableElement> visitAll(Collection<VisitableElement> elements) {
        for (VisitableElement element : elements) {
            element.accept(this);
        }
        return elements;
    }
}