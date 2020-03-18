package org.basic.jdk.core.pattern.behavioral.Visitor.test2.visitor;

import java.util.Collection;

import org.basic.jdk.core.pattern.behavioral.Visitor.test2.object_structure.ElementFloat;
import org.basic.jdk.core.pattern.behavioral.Visitor.test2.object_structure.ElementString;
import org.basic.jdk.core.pattern.behavioral.Visitor.test2.object_structure.VisitableElement;

public abstract class Visitor {
    public abstract VisitableElement visitString(ElementString element);

    public abstract VisitableElement visitFloat(ElementFloat element);

    public abstract Collection<VisitableElement> visitAll(Collection<VisitableElement> element);
}