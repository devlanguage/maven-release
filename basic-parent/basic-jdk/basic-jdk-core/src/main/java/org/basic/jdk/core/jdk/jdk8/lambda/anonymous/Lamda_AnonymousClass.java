package org.basic.jdk.core.jdk.jdk8.lambda.anonymous;

public class Lamda_AnonymousClass {
    void testThis() {
        Runnable t1 = new Runnable() {

            @Override
            public void run() {
                this.getClass(); // INVOKEVIRTUAL Object.getClass () : Class
            }
        };

        Runnable t2 = () -> {
            this
            .getClass();

        };
    }
}
