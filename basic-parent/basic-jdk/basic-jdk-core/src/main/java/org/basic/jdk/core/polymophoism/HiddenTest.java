package org.basic.jdk.core.polymophoism;

import java.io.IOException;

/**
 * <pre>
 * 隐藏：
 * 隐藏是一种现象，也算是一种特性，在c++中，子类可以让调用者看不到父类同名函数，也就是说子类中不能使用父类的同名函数，这种现象叫隐藏。
 * 
 * 特点： 1.在不同类内(一个在子类，一个在父类)
 *       2.函数名相同
 *       2.如果基类函数使用了virtual，那么子类函数需要有不同的参数或者返回值
 *       4.如果基类没有virtual，那么子类函数可以与基类的函数完全相同，也可不同
 * 
 * -----------------------------------------------------------------------------------------------------------------------------------
 * 
 * 多态性概念: 在程序中同一符号或名字在不同情况下具有不同的语义解释,其有两种基本形式: 编译时多态性: 在程序编译时可确定的多态性,由重载机制实现 运行时多态性:
 *    指程序动态运行时才可确定的多态性,由继承结合动态绑定实现。我的理解就是，方法的重载体现了多态，累的继承体现了多态。
 * 
 * 隐藏 ，也是出现在继承上，当父类有静态变量或者静态方法时，子类覆盖父类的变量，就会隐藏子类的变量和方法。也有点奇怪，但却是如此。
 * 
 * 网友PC总结：
 * 1. 重载：看参数(参数、返回值)
 * 2. 隐藏：用什么就调用什么(子类看不到基类的隐藏函数)
 * 3. 覆盖：调用派生类 (传什么指针调谁的)
 * </pre>
 * 
 * @author ygong
 * 
 */
class ParentA {
    private int private1;
    int package1;
    protected int protected1;
    public int public1;
    
    class InnerClassInClass{
        private int a1;
        void test1(){
            System.out.println(ParentA.this.private1);
             
        }
    }
    public int test1(int i, int j) throws java.io.IOException {
        InnerClassInClass cc1 = new InnerClassInClass();
        System.out.println(cc1.a1);
        
        class InnerClassInMethod{
            private int a1;
            void test1(){
                System.out.println(ParentA.this.private1);
                 
            }
        }
        InnerClassInMethod cm1 = new InnerClassInMethod();
        System.out.println(cm1.a1);
        
        System.out.println(getClass() + ".test1");
        return 0;
    }
    
    protected ParentA test2(int i, int j) throws java.io.IOException {
        System.out.println(getClass() + ".test2");
        return null;
    }

    static int test3(int i, int j) throws java.io.IOException {
        System.out.println("ParentA.test3");
        return 0;
    }

}

class ChildA extends ParentA {
    public int test1(int i, int j) throws java.io.FileNotFoundException {
        
        System.out.println(getClass() + ".test1");
        return 0;
    } // FileNotFoundException is child of IOException

    protected ChildA test2(int i, int j) throws java.io.IOException {
        System.out.println(getClass() + ".test2");
        return null;
    }

    static int test3(int i, int j) throws java.io.IOException {
        System.out.println("ChildA.test3");
        return 0;
    }
}

public class HiddenTest {

    public static void main(String[] args) throws Exception {

        ParentA pa1 = new ParentA();
        pa1.test1(1, 2);
        pa1.test2(1, 2);

        ChildA ca1 = new ChildA();
        ca1.test1(1, 2);
        ca1.test2(1, 2);
        ca1.test3(1, 2);
        ChildA.test3(1, 2);
        ParentA.test3(1, 2);

        ParentA pa2 = ca1;
        pa2.test1(1, 2);
        pa2.test2(1, 2);
        pa2.test3(1, 2);
    }
}
