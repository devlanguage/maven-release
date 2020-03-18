package org.basic.jdk.core.jdk.jdk7.e1_coin_jsr344;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * <pre>
 * A set of small language changes intended to simplify common, day-to-day programming tasks: Strings in switch
 * statements, try-with-resources statements, improved type inference for generic instance creation ("diamond"),
 * simplified varargs method invocation, better integral literals, and improved exception handling (multi-catch)
 * </pre>
 *
 */
@SuppressWarnings("unused")
public class CoinJs4344Test {
    enum EnumSex {
        \u7537, \u5973
    };

    @org.junit.Test
    public void e1_switchCase() {
        byte b1 = 1;
        Byte b2 = new Byte((byte) 2);

        short s1 = 1;
        Short s2 = new Short((short) 2);

        int i1 = 1;
        Integer i2 = new Integer("3");

        char c1 = 1;
        Character c2 = new Character((char) 3);

        // Only convertible int values(byte,char,short,int), strings or enum
        // variables are permitted
        switch (b2) { // b1,b2,s1,s2,i1,i2,c1,c2
            case 2:
                break;
        }

        // Only convertible int values(byte,char,short,int), strings or enum
        // variables are permitted
        switch (b2) { // b1,b2,s1,s2,i1,i2,c1,c2
            case 2:
                break;
        }

        EnumSex sex1 = EnumSex.\u5973;
        switch (sex1) {
            case 男:
                break;
            case 女:
                break;
        }

        String str1 = "xx";
        // Only convertible int values(byte,char,short,int), strings or enum
        // variables are permitted
        switch (str1) {
            case "TS":
                break;
            case "男":
                // break;

        }
    }

    @org.junit.Test
    public void constructCollection() {
        // Build List
        List<String> list1 = new ArrayList<String>();
        // List<String> list2=[""];
        // String le1 = list1[0];

        // Set<String> set1 = {""};
        // Map<String,String> map1 = {"Key1":""};

    }

    @Test
    public void e2_constructNumeric() {
        byte b1 = 0b0001;
        int i1 = 0B1000_001; // "_" is placed only among number.
        int oneMillion = 1_000_000;
    }

    /**
     * <pre>
     * 1.4 Catch多个异常 说明：Catch异常类型为final； 生成Bytecode 会比多个catch小； Rethrow时保持异常类型 
     * 1.3 Try-with-resource语句 
     *   注意：实现java.lang.AutoCloseable接口的资源都可以放到try中，跟final里面的关闭资源类似； 按照声明逆序关闭资源 ;Try块抛出的异常通过Throwable.getSuppressed获取
     *   1.8 信息更丰富的回溯追踪 就是上面try中try语句和里面的语句同时抛出异常时，异常栈的信息
     * 
     * </pre>
     */
    @Test
    public void e2_tryCatchFinally() {
        // checked exception
        // unchecked exception: java.lang.RuntimeException, java.lang.Error and
        // subclass of them

        // 支持在一个catch子句中同时捕获多个异常: 对parent-child异常，只有parent allowed(Compiler will
        // convert them to multiple catch)，
        try {
            Class.forName("").newInstance();
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException ab) {
            //
        }

        // 另外一个是在捕获并重新抛出异常时的异常类型更加精确
        class ExceptionA extends Exception {
        }
        class ExceptionASub1 extends ExceptionA {
        }
        class ExceptionASub2 extends ExceptionA {
            void test() throws ExceptionA {

                try {
                    throw new ExceptionASub2();
                } catch (ExceptionA exA) {
                    try {
                        throw exA;
                    } catch (ExceptionA e1) {

                    }
                    /*
                     * catch (ExceptionASub1 e2) { // Compile OK in JDK6: // 对于JDK6 Compiler, //
                     * 第二个try子句中抛出的异常类型是前一个catch子句中声明的ExceptionA类型. //
                     * 因此在第二个catch子句中尝试捕获ExceptionA的子类型ExceptionASub1是合法的
                     * 
                     * // Compile Error in JDK7: // Unreachable catch block for // ExceptionASub1. This exception is
                     * never thrown from // the try statement body // Java编译器可以准确知道变量e表示的异常类型是ExceptionASub2
                     * ,接下来的第二个catch子句试图捕获ExceptionASub1类型的异常 }
                     */
                }

            }
        }

        // close resource automatical if resource is subclass of java.lang.AutoCloseable
        // INVOKEVIRTUAL java/io/BufferedReader.close ()V
        try (BufferedReader reader1 = new BufferedReader(new FileReader("C:\test.txt"));
                BufferedReader reader2 = new BufferedReader(new FileReader("C:\test.txt"));) {

            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader1.readLine()) != null) {
                builder.append(line);
                builder.append(String.format("%n"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @SafeVarargs
    // @SafeVarargs注解只能用在参数长度可变的方法或构造方法上，且方法必须声明为static或final，否则会出现编译错误。
    // 一个方法使用@SafeVarargs注解的前提是，开发人员必须确保这个方法的实现中对泛型类型参数的处理不会引发类型安全问题。
    private static <T> T useVarargs(T... args) {
        return args.length > 0 ? args[0] : null;
    }

    /**
     * <pre>
     * warning: [varargs] Possible heap pollution from parameterized vararg type
     *     要消除警告，可以有三种方式
     *     1.加 annotation @SafeVarargs 
     *     2.加 annotation @SuppressWarnings({"unchecked", "varargs"})
     *     3.使用编译器参数 –Xlint:varargs;
     * </pre>
     */
    @Test
    public void e4_Varg_enhancedUncheckWarning() {
        // 原因:
        // 可变长度的方法参数的实际值是通过数组来传递的，而数组中存储的是不可具体化的泛型类对象，自身存在类型安全问题。因此编译器会给出相应的警告信息
        // Type safety: A generic array of ArrayList<String> is created for a
        // varargs parameter
        useVarargs(new ArrayList<String>());
    }

    @Test
    public void e5_genericInfer() {
        List<String> list = new ArrayList<>();

    }

    public static void main(String[] args) {

    }
}
