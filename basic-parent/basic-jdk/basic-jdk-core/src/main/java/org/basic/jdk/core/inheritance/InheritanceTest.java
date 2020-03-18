package org.basic.jdk.core.inheritance;

/**
 * <pre>
 *    static block or filed initialized up to down in class and inheritance class
 *    Call the self-construct but not execute the constuct content
 *          Call the parent construct and execute the parent cosntrutor content
 *          initilize the parent non-static field.
 *    Call the self-construct content
 *       initilize the self non-static field.
 * </pre>
 */
class A {

    private String a1 = initA1();;
    private final static String sa1 = initSa1();;
    static {
        print("static block: A");
    }
    private final static String sa2 = initSa2();;

    public A() {

        print("constrcuting the A()");
    }

    private String initA1() {
        print("non-static field: A.a1");
        return null;
    }

    public A(String a1) {

        print("constrcuting the A(String a1)");
    }

    public final static <T> void print(T t) {

        System.out.println(t);
    }

    private static String initSa1() {

        print("static field: A.sa1");
        return "sa1_value";
    }

    private static String initSa2() {

        print("static field: A.sa2");
        return "sa2_value";
    }
}

class B extends A {

    static {
        print("static stock: B");
    }

    private A a1 = new A("Test");

    public B() {

        print("constrcuting the B()");
    }

}

class C extends B {

    static {
        print("static stock: C");
    }

    private B b1 = new B();

    public C() {

        print("constrcuting the C()");
    }
}

public class InheritanceTest {

    public static void main(String[] args) {
        Parent p = new Parent();
        p.test();
//        static field: A.sa1
//        static block: A
//        static field: A.sa2
//        static stock: B
//        static stock: C
//        non-static field: A.a1
//        constrcuting the A()
//        non-static field: A.a1
//        constrcuting the A(String a1)
//        constrcuting the B()
//        non-static field: A.a1
//        constrcuting the A()
//        non-static field: A.a1
//        constrcuting the A(String a1)
//        constrcuting the B()
//        constrcuting the C()
        B b = new C();
        assert b.getClass().getName().equals(C.class.getName());
    }
}
