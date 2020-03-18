package org.basic.jdk.core.polymophoism;

/**
 * <pre>
 * 覆盖是c++面向对象提出的一个新概念，如果实现覆盖呢？ 这个也就是我们常说的多态。通过一个虚函数表实现的，基类使用virtual来声明一个函数为虚函数，建立了一张虚函数表，如果子类重写该函数，子类实例的指针就会指向重写的函数。这就造成了，相关实例(子类，基类)对同一接口调用时产生不同效果，我们称之为多态。多态是c++的高级特性。
 * 
 * 即覆盖了一个方法并且对其重写，以求达到不同的作用。对我们来说最熟悉的覆盖就是对接口方法的实现，在接口中一般只是对方法进行了声明，而我们在实现时，就需要实现接口声明的所有方法。除了这个典型的用法以外，我们在继承中也可能会在子类覆盖父类中的方法。在覆盖要注意以下的几点： 
 * 1、覆盖的方法的标志必须要和被覆盖的方法的标志完全匹配，才能达到覆盖的效果；
 * 2、覆盖的方法的返回值必须和被覆盖的方法的返回一致； 
 * 3、覆盖的方法所抛出的异常必须和被覆盖方法的所抛出的异常一致，或者是其子类；
 * 4、被覆盖的方法不能为private，否则在其子类中只是新定义了一个方法，并没有对其进行覆盖。
 * 
 * 覆盖，就是子类中出现于父类相同的方法。
 * 
 * 原则： 1.方法名必须相同。
 *       2.方法的参数个数及类型必须完全相同。
 *       3.返回值类型必须相同或者拥有inheritance关系
 *       4.子类不能缩小访问权限。
 *       5.子类不能抛出比覆盖方法更多的异常(当可以减少，也可以抛出子异常)。
 * 
 * 特点： 1.在不同类内(一个在子类，一个在父类)
 *       2.基类函数需要使用virtual声明,子类的可有可无
 *       3.函数名，参数，返回值均需相同
 * </pre>
 * 
 * @author ygong
 * 
 */
class Parent {
    public int test1(int i, int j) throws java.io.IOException {
        return 0;
    }

    public Parent test2(int i, int j) throws java.io.IOException {
        return null;
    }

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
    // Compile Error: Cannot reduce the visibility of the inherited method from
    // protected int test1(int i, int j){return 0;}

    // Compile Error: CharConversionException is not compatible with throws clause in Parent.test1(int, int)
    // public int test1(int i, int j) throws java.io.CharConversionException {return 0;} //Exception

    // Compile Error: type is incompatible with Parent.test1
    // public float test1(int i, int j) throws java.io.FileNotFoundException {return 0;} // The return

    // Passed: public int test1(int i, int j) {return 0;} //FileNotFoundException is child of IOException
    public int test1(int i, int j) throws java.io.FileNotFoundException {
        return 0;
    } // FileNotFoundException is child of IOException

    // Compile Error: duplicate with FileNotFoundException Method
    // public int test1(int i, int j) throws java.io.IOException{return 0;}

    public Child test2(int i, int j) throws java.io.IOException {
        return null;
    }

    // Print the same with c.test(), Child.sameSignature_PrivateParent will never be call. unless override test() in
    // Child
    public void sameSignature_PrivateParent() {
        System.out.println("  Child.sameSignature_PrivateParent()");
    }

    public void sameSignature_NonPrivateParent() {
        System.out.println("  Child.sameSignature_NonPrivateParent()");
    }
}

public class OverrideTest {
    public static void main(String[] args) {
        Child c = new Child();
        System.out.println("Child Pointer called: ");
        c.test();

        Parent p = c;
        System.out.println("Parent Pointer called: ");
        p.test(); // print the same with c.test(), Child.sameSignature_PrivateParent will never be call. unless override
                  // test() in Child
    }
}
