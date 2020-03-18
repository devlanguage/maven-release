package org.basic.jdk.core.jdk.jdk5.generictype;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * 
 * class GenericClass&lt;T&gt; {
 * 
 *     public void method1(T t) {
 *         T t1 = new T(); // 编译错误，T不能与new联用
 *         if (t1 instanceof T) {
 *         } // 编译错误，T不能与instanceof联用
 *     };
 * 
 *     static T t2; // 编译错误，静态字段不能使用T
 * 
 *     public static void method2(T t) {// 编译错误，T 是non-static type,不能被静态方法使用, 可以申明为static type，然后这样使用
 *         // public static &lt;KK&gt; void m1(KK k) { }
 *     };
 * 
 * }
 * </pre>
 */

public class ExtendSuperTest<K> {

    // Cannot make a static reference to the non-static type K
    // public static void m1(K k) {
    // }
    public static <KK> void m1(KK k) {

    }

    /****
     * <pre>
     *  上界: extend 
     *    上界用extends关键字声明，表示参数化的类型可能是所指定的类型，或者是此类型的子类。如下面的代码：
     * </pre>
     * 
     */
    public void upperBound(List<? extends Date> list, Date date) {

        Date now = list.get(0); // 相反，读取数据时，不管实际的list是什么类型，但可以知道它至少会返回一个Date类型，所以用foreach，get等没有问题。
        System.out.println("now==>" + now);
        // The method add(capture#2-of ? extends Date) in the type List<capture#2-of ? extends Date> is not applicable
        // for the arguments (Date)
        // list.add(date); // 这句话无法编译: 实际调用时传入的list可能是java.util.Date的某个子类的参数化类型 ,比如 List<java.sql.Timestamp>
        list.add(null);// 这句可以编译，因为null没有类型信息
    }

    // Enhancement
    // 这里方法声明中的T作为一种参数化信息，会存储在java字节码中，T的实际类型由调用时的参数决定的
    public <T extends Date> void upperBound2(List<T> list, T date) {

        list.add(date);
    }

    public void testUpperBound2() {

        List<Timestamp> list = new ArrayList<Timestamp>();
        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());
        upperBound2(list, time);

        // The method upperBound2(List<T>, T) in the type ExtendSuperTest is not applicable for the
        // arguments(List<Timestamp>, Date)
        // upperBound2(list,date);//这句同样无法编译
        // 上面代码中的list的类型参数决定了方法中T的类型，所以会看到注释掉的内容不能编译。而换成这样：
        List<Date> list2 = new ArrayList<Date>();
        upperBound2(list2, date);
        // 编译就没有任何问题了。

    }

    /****
     * <pre>
     * 下界  super
     *     下界用super进行声明，表示参数化的类型可能是所指定的类型，或者是此类型的父类型，直至Object。如下面的代码：
     * </pre>
     * 
     ***/
    // Syntax error on token "super", , expected
    // public <T super Timestamp> void lowerBound(List<T> list, int i) { }
    public void lowerBound(List<? super Timestamp> list) {

        Timestamp now = new Timestamp(System.currentTimeMillis());
        list.add(now);

        // Timestamp time = list.get(0); //不能编译
        // Reason as follows
        /**
         * <pre>
         * lowerBound方法中的List<? super Timestamp>表示这个list的参数类型可能是Timestamp或Timestamp的父类，如后面测试代码里，
         * 实际传入的是一个List<Date>类型。向List<Date>中add一个Timestamp肯定是没有问题的，但list.get()方法返回的对象类型
         * 可能是Date甚至是Object，你不能说list.get(0)返回的就是一个Timestamp，这里是向下类型转换了，编译器无法处理，所以这里不能编译。
         * 用java泛型实现的擦拭法解释，编译后会是如下的伪代码：
         *  
         *         Java代码  
         *         public void lowerBound(List list)   
         *         {   
         *             Timestamp now = new Timestamp(System.currentTimeMillis());   
         *             list.add(now);   
         *             Timestamp time = (Timestamp)list.get(0); //①   
         *         }   
         *         public void testLowerBound()   
         *         {   
         *             List list = new ArrayList();   
         *             list.add(new Date());   
         *             lowerBound(list);   
         *         }  
         * 
         *         public void lowerBound(List list)
         *         {
         *             Timestamp now = new Timestamp(System.currentTimeMillis());
         *             list.add(now);
         *             Timestamp time = (Timestamp)list.get(0); //①
         *         }
         *         public void testLowerBound()
         *         {
         *             List list = new ArrayList();
         *             list.add(new Date());
         *             lowerBound(list);
         *         }
         * 
         *         代码①进行了强制类型转换，但实际添加进去的是一个Date类型，肯定会报ClassCastException，编译器无法保证向下类型转换的安全，所以这一句自然就无法编译了。
         * </pre>
         **/
    }
}
