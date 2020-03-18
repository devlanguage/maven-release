package org.basic.jdk.core.inheritance;

class Parent {
    private void sameSignature_PrivateParent() {
        System.out.println("  Parent.sameSignature_PrivateParent()");
    }

    void sameSignature_NonPrivateParent() {
        System.out.println("  Parent.sameSignature_NonPrivateParent()");
    }

    protected void test() {
        sameSignature_PrivateParent();
        sameSignature_NonPrivateParent();
    }
}

class Child extends Parent {

    public void sameSignature_PrivateParent() {
        System.out.println("  Child.sameSignature_PrivateParent()");
    }

    public void sameSignature_NonPrivateParent() {
        System.out.println("  Child.sameSignature_NonPrivateParent()");
    }

}

public class SameSignature_UnlikeReturn {
    public static void main(String[] args) {
        Child c = new Child();
        System.out.println("Child Pointer called: ");
        c.test();

        Parent p = c;
        System.out.println("Parent Pointer called: ");
        p.test();

    }
}
