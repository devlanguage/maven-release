package org.basic.jdk.core.jdk.jdk6.generictype;

import java.util.ArrayList;

public class GenericsFooDemo {

    public static void printExtends(GenericsFoo<? extends Object> str) {

        System.out.println("strFoo.getX=" + str);
    }

    public synchronized static <T> T printSuper(GenericsFoo<? super String> str, T t) {

        System.out.println("strFoo.getX=" + str);
        return t;
    }

    public static void main(String args[]) {

        // ע�⣬�м������Եĸı䣺
        // 1�� ���󴴽�ʱ����ȷ�������ͣ���GenericsFoo<String>��
        // 2�� ����ͨ��getX����ȡ��ʱ������Ҫ��������ת����
        // 3�� �Ը��������ĵ��ã�������������봴��ʱָ�������Ͳ�ƥ��ʱ���������ͻᱨ��

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
