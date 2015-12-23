package org.basic.jdk.jdk5.generictype;

import java.util.ArrayList;
import java.util.List;

public class GenericsFooDemo {

    public static void printExtends(GenericsFoo<? extends Object> str) {

        System.out.println("strFoo.getX=" + str);
    }

    public static void printSuper(GenericsFoo<? super String> str) {

        System.out.println("strFoo.getX=" + str);
    }

    public static void main(String args[]) {

        // 注意，有几点明显的改变：
        // 1． 对象创建时，明确给出类型，如GenericsFoo<String>。
        // 2． 对象通过getX方法取出时，不需要进行类型转换。
        // 3． 对各个方法的调用，如果参数类型与创建时指定的类型不匹配时，编译器就会报错。

        GenericsFoo<String> strFoo = new GenericsFoo<String>();
        strFoo.setX("Hello Generics!");
        GenericsFoo<Double> douFoo = new GenericsFoo<Double>();
        douFoo.setX(new Double("33"));
        GenericsFoo<ArrayList> objFoo = new GenericsFoo<ArrayList>();
        objFoo.setX(new ArrayList());

        String str = strFoo.getX();
        Double d = douFoo.getX();
        Object obj = objFoo.getX();

        System.out.println("strFoo.getX=" + str);
        System.out.println("douFoo.getX=" + d);
        System.out.println("strFoo.getX=" + obj);

        printExtends(strFoo);
    }
}
