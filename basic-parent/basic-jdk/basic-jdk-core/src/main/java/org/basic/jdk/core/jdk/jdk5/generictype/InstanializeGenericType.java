package org.basic.jdk.core.jdk.jdk5.generictype;

public class InstanializeGenericType<T> {

    // 另外，由于泛型的擦除机制，我们也无法直接对泛型类型用new操作符，比如
    // public T create() {
    // 由于擦除机制，语句T obj = new T(); 擦除后就变成了
    // obj = new ();
    // T obj = new T(); // 无法通过编译！！！
    // return obj;
    // }

    @SuppressWarnings("unchecked")
    public T create(Class<T> cls) {

        try {
            Object obj = cls.newInstance();
            return (T) obj;
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public T[] createArray(Class<T> cls, int len) {

        try {
            Object obj = java.lang.reflect.Array.newInstance(cls, len);
            return (T[]) obj;
        } catch (Exception e) {
            return null;
        }
    }

}