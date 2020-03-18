package org.basic.jdk.core.jdk.jdk6.generictype;

public class Generics_Erasure<T> {

    private T x;

    public T getX() {

        return x;
    }

    public void setX(T x) {

        this.x = x;
    }

    public void setGeneric(Generics_Erasure<?> foo) {
        setGenericHelper(foo);
    }
    private <V> void setGenericHelper(Generics_Erasure<V> foo) {
        foo.setX(foo.getX());
    }

    public static void main(String[] args) {

        Generics_Erasure<String> gf = new Generics_Erasure<String>();
        gf.setX("Hello");

        // ���ڲ������ƣ�GenericsFoo<?>����������GenericsFoo�����ͱ���ʧ����ô��Ӧ�ķ��������ͱ���ˣ�
        // public Object getX();
        // public void setX(null x);
        Generics_Erasure<?> gf2 = gf;
//        gf2.setX("World"); // ��������
//        String str = gf2.getX(); // ��������
//        gf2.setX(gf2.getX()); // ��������
    }
}
