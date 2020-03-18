package org.basic.grammar.uml.polymophoism;

import java.io.IOException;

/**
 * <pre>
 * 重载(overload)：
 * 这不是一个新的概念，在c++中一个比较重要的概念(c语言里面不允许)，特征就是允许函数名相同，通过指定不同的参数或者返回值来区别。这里我们所说的重载是针对c++中类的，类中重载与前面所说的也完全一样。
 * 特点：
 *      1.在同一个类内
 *      2.相同的函数名
 *      3.不同参数、返回值。
 * 
 * 指我们可以定义一些名称相同的方法，通过定义不同的输入参数来区分这些方法，然后再调用时，VM就会根据不同的参数样式，来选择合适的方法执行。在使用重载要注意以下的几点： 
 * 1、在使用重载时只能通过不同的参数样式。例如，不同的参数类型，不同的参数个数，不同的参数顺序（当然，同一方法内的几个参数类型必须不一样，例如可以是fun(int, float)， 但是不能为fun(int, int)）； 
 * 2、不能通过访问权限、返回类型、抛出的异常进行重载； 
 * 3、方法的异常类型和数目不会对重载造成影响；
 * 4、对于继承来说，如果某一方法在父类中是访问权限是priavte，那么就不能在子类对其进行重载，如果定义的话，也只是定义了一个新方法，而不会达到重载的效果。
 * 重载，就是在一个类中用同一个方法名，但有不同方法
 * 
 * 原则：1.方法名必须相同。
 *       2.方法的参数类型和参数个数两者至少有一不同。
 *       3.方法的修饰符,访问权限，异常，返回值类型可以不同。
 * </pre>
 * 
 * @author ygong
 * 
 */
public class OverloadTest {
	public final static void test1(int i, int j) {
	}

//    public final void test1(int i, int j){}//Duplicate Method
//    public final int test1(int i, int j){}//Duplicate Method
//    public final int test1(int i, int j) throws java.io.FileNotFoundException{}//Duplicate Method
	public void test1(int i, byte j) {
	}

	public void test1(int i, short j) {
	}

	public void test1(int i, long j) {
	}

	public static void main(String[] args) {
		ChildA ca = new ChildA();
		try {
			ca.test2(1, 2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
