package org.basic.grammar.s1_type_var;

/**
 * <pre>
 * 定义一个类
 *     [范围] class 类名
 *  
 *  范围：
 *      有效值为: public, 或者什么都不写
 *     public:    其他所有类都可以使用这个类
 *     什么都不写:  只有与这个类在同一个文件夹或者包(package)类才可以引用
 *  类名: 
 *      任意字符串, 第一个字母必须为字母{a,b,c,d....z, A,B,C...Z}或者下划线(_)
 *    ==注意，一般在java中，类名的第一个字母应当大写，如果类名包含多个单词，则每一个单词的第一个字母应当大写
 *   比如: Person, Student, PersonManager
 *     
 *  
 * </pre>
 */
public class HelloWorld {

    /***************************************************************************
     * 声明类的属性(attribute)或者字段(field)，
     * 
     * <pre>
     *  这是模版:  
     *         [范围] [static] 类型 字段名称;
     *  
     * 范围： 有效的值为: private, protected, public, 或者什么都不写
     * static:
     *   表示该字段直接用类来引用，而不是对象
     * 类型： 
     *      基本类型[boolean, short, int, long, float, double, char]
     *      系统定义的类， 比如String, Date, 
     *      自定义的类
     * 字段名称：
     *      任意字符串, 第一个字母必须为字母{a,b,c,d....z, A,B,C...Z}或者下划线(_)
     * 
     * </pre>
     **************************************************************************/
    private String field01 = "field01";
    String field02 = "field02";
    protected String field03 = "field03";
    public String field04 = "field04";

    String field05 = "field05";
    public static String field06 = "field06";

    /***************************************************************************
     * 声明类的方法(method)或者函数(function)
     * 
     **************************************************************************/

    // 非静态函数
    public void hello_03() {
        System.out.println(" 第三个函数： 静态函数，只有这个类的对象才可以引用，类不能引用");
    }

    // 无参函数
    public static void hello() {
        System.out.println(" 第一个函数： 没有人很参数");
    }

    // 带参数的函数， 参数 (String userName, int age) 可以有任意多个,
    public static void hello(String userName, int age) {
        System.out.println(" 第二个函数： 带参数函数");
        System.out.println("第一个参数, 用户名称=" + userName + ", 第二个参数, 年龄=" + age);
    }

    public static void main(String args[]) {
        System.out.println("========================");
        System.out.println("你好，朋友！");

        System.out.println("========================");
        hello();

        System.out.println("========================");
        hello("巩永亮", 24);

        // static 字段或者函数
        System.out.println(HelloWorld.field06);
        // 非static 字段或者函数
        HelloWorld smp对象_01 = new HelloWorld();
        HelloWorld smp对象_02 = new HelloWorld();
        System.out.println(smp对象_01.field06);
        System.out.println(smp对象_02.field06);

        smp对象_01.hello_03();

        // smp11.hello_03(); smp11是类，不能引用非静态的方法或者属性

    }
}