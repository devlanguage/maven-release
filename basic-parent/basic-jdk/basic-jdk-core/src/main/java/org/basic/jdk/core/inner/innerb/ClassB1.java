package org.basic.jdk.core.inner.innerb;

public class ClassB1 {
    public class ClassB1InnerPublic {

    }

    protected class ClassB1InnerProtected {

    }

    class ClassB1InnerDefault {

    }

    private class ClassB1InnerPrivate {

    }

    public void test1() {
        ClassB1 cb1 = new ClassB1();
        ClassB1InnerDefault d1 = cb1.new ClassB1InnerDefault();

        ClassB1InnerPrivate b1private = new ClassB1InnerPrivate();
        ClassB1InnerDefault b1default = new ClassB1InnerDefault();
        ClassB1InnerProtected b1protected = new ClassB1InnerProtected();
        ClassB1InnerPublic b1public = new ClassB1InnerPublic();
    }

    public static void test2() {
        ClassB1 cb1 = new ClassB1();
        ClassB1InnerDefault d1 = cb1.new ClassB1InnerDefault();

//        No enclosing instance of type ClassB1 is accessible. Must qualify the allocation with an enclosing instance of type ClassB1 (e.g. x.new A() where x is an instance of ClassB1).
//        ClassB1InnerPrivate b1private = new ClassB1InnerPrivate();
//        ClassB1InnerDefault b1default = new ClassB1InnerDefault();
//        ClassB1InnerProtected b1protected = new ClassB1InnerProtected();
//        ClassB1InnerPublic b1public = new ClassB1InnerPublic();
    }
}
