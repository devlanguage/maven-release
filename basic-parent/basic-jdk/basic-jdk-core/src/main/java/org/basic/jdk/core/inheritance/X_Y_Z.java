package org.basic.jdk.core.inheritance;

class X {

    X() {
        System.out.println("X: construct-001");
    }

    X(int a) {

        System.out.println("X: construct-002");
    }
}

class Y {

    X b = new X(3);

    Y() {

        System.out.println("Y()");
    }
}

public class X_Y_Z extends X {

    public static synchronized native void method();

    Y y = new Y();

    X_Y_Z() {

        System.out.println("X_Y_Z()");
    }

    public static void main(String[] args) {

        // Parent
        // field
        // constructor
        new X_Y_Z();
    }
}