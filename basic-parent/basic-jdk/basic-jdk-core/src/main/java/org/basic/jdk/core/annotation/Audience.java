package org.basic.jdk.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;

//@Actor(id = "23", name = "Audience")
public class Audience extends Stadium {

    /**
     * asdf
     * 
     * @param args
     */
    public static void main(String[] args) {

        Audience st = new Audience();
        for (Annotation an : st.getClass().getAnnotations()) {
            System.out.println(an.annotationType() + an.toString());
        }

        Array.newInstance(Audience.class, 12);

    }
}
