package org.basic.grammar.jdk.jdk6.generictype;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

public class InstanializeGenericType<K> {

    // 另外，由于泛型的擦除机制，我们也无法直接对泛型类型用new操作符，比如
    // public T create() {
    // 由于擦除机制，语句T obj = new T(); 擦除后就变成了
    // obj = new ();
    // T obj = new T(); // 无法通过编译！！！
    // return obj;
    // }

    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> cls) {

        try {
            Object obj = cls.newInstance();
            return (T) obj;
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] createArray(Class<T> cls, int len) {

        try {
            Object obj = java.lang.reflect.Array.newInstance(cls, len);
            return (T[]) obj;
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings({ "unchecked", "unused" })
    public static void main(String[] args) {

        InstanializeGenericType<String> oT = InstanializeGenericType.create(InstanializeGenericType.class);
        InstanializeGenericType<String>[] arrayT = InstanializeGenericType.createArray(InstanializeGenericType.class,
                12);
        List<String> str1 = new ArrayList<String>(21);
        for (Constructor<?> str2 : str1.getClass().getConstructors()) {
            TypeVariable<?>[] typeVariables = str2.getTypeParameters();
            Annotation[][] annotations = str2.getParameterAnnotations();
            Class<?>[] paramTypes = str2.getParameterTypes();
            printArray(typeVariables, annotations, paramTypes);
        }

        System.out.println(str1.getClass());// class java.util.ArrayList

    }

    public final static <M> void printArray(M... ms) {

        for (M m : ms) {
            System.out.println(m.toString());
        }
    }

    private static <M> void extracted(M m) {

        printArray(m);
    }
}
