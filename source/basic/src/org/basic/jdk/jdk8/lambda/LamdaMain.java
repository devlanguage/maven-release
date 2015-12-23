/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 12:36:31 PM Apr 24, 2014
 *
 *****************************************************************************
 */
package org.basic.jdk.jdk8.lambda;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

/**
 * Created on Apr 24, 2014, 12:36:31 PM
 */
public class LamdaMain {
    /**
     * <pre>
     * 什么是SAM？SAM 是单个抽象方法的替代，因此，直接一点，我们可以说SAM==功能性接口
     * 功能性接口呢？一个功能性接口就是一个只含有抽象方法的接口，只是声名了一个函数。在某些场合下，这个唯一的函数可能是一个带有重载因子的的多态函数，这种情况下，所有的函数对外都是一个函数。除了典型的通过新建和初始化一个类来新建一个接口实例，功能性接口实例还可以通过使用一个lambda表达式、方法、或者构造引用来达到新建实例的效果。下面是一个功能性接口的例子：
     * // custom built functional interface
     * public interface FuncInterface {
     *    public void invoke(String s1, String s2);
     * }
     * 下面是来自java api的功能性接口：
     * view sourceprint?
     * java.lang.Comparable
     *  java.lang.Runnable
     *  java.util.concurrent.Callable
     *  java.awt.event.ActionListener
     * </pre>
     */
    public static final void functionInterface1() {
        System.out.println("===========functionInterface1======");

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println(String.format("Message #%d from inside the thread!", i));
            }
        }).start();

        JButton button = new JButton("Click");
        button.addActionListener((e) -> {
            System.out.println("The button was clicked!");
        });

    }

    @FunctionalInterface
    interface Converter<F, T> {
        T convert(F from);
    }

    public static final void functionInterface2() {
        System.out.println("===========functionInterface2======");

        Converter<String, Integer> converter1 = Integer::valueOf;
        Converter<String, Integer> converter2 = (from) -> Integer.valueOf(from);
        Integer converted = converter2.convert("123");
        System.out.println(converted); // 123
    }

    interface ConstructorReference<T> {
        T constructor();
    }

    interface FunctionInterface1 {
        void funcMethod1(String input);
    }

    interface FunctionInterface2 {
        void funcMethod2(String input);
    }

    interface defaultImplementedInterface {
        default void anotherMethod(String input) {
            System.out.println("default implemented by interface: MethodReference.anotherMethod(String)");
        }
    }

    static class A {
    }

    public static class ConstructorClass extends A implements defaultImplementedInterface {
        String value;

        public ConstructorClass() {
            value = "default";
        }

        public void funcInterface1_Replacer(String input) {
            System.out.println(this.getClass() + ".funcInterface1_Replacer(string) is called");
        }

        public void funcInterface2_Replacer(String input) {
            System.out.println(this.getClass() + ".funcInterface2_Replacer(string) is called");
        }

    }

    public static final void aliasMechnism() {
        System.out.println("===========aliasMechnism with functionInterface===========");

        // constructor reference
        ConstructorReference reference = ConstructorClass::new;
        ConstructorClass cc = (ConstructorClass) reference.constructor();
        // static method reference
        FunctionInterface1 mr1 = cc::funcInterface1_Replacer;
        // object method reference
        FunctionInterface2 mr2 = cc::funcInterface2_Replacer;
        System.out.println(cc.value);
        mr1.funcMethod1("test");
        mr2.funcMethod2("test");
        cc.anotherMethod("test3");

    }

    public static final void expression() {
        System.out.println("===========New Expression===========");
        // (a) (int a, int b) -> a + b
        // (b) (int a, int b) -> {
        // if (a > b) {
        // return a;
        // } else if (a == b) {
        // return a * b;
        // } else {
        // return b;
        // }
        // }

    }

    public static final void jcfEnhance() {
        System.out.println("===========New JCF Enhancement===========");
        System.out.println("===Print the content of List ");
        List<LamdaMessage> messages = new ArrayList<LamdaMessage>();
        messages.add(new LamdaMessage("aglover", "red", 56854));
        messages.add(new LamdaMessage("aglover", "yellow", 85));
        messages.add(new LamdaMessage("aglover", "green", 9999));
        messages.add(new LamdaMessage("rsmith", "black", 4564));
        // for (String current : list) {
        // System.out.println(current);
        // }
        // 新方法:
        messages.forEach(x -> System.out.println(x));
        // 新方法：
        messages.stream().filter(ele1 -> (1 == 1)).forEach(ele -> System.out.println(ele.toUpperCase()));
        messages.stream().filter(new java.util.function.Predicate<LamdaMessage>() {
            @Override
            public boolean test(LamdaMessage t) {
                return 1 == 1;
            }

        }).forEach(new java.util.function.Consumer<LamdaMessage>() {
            @Override
            public void accept(LamdaMessage ele) {
                System.out.println(ele.toUpperCase());
            }
        });

        List<String> userNames = new ArrayList<String>();
        messages.stream().forEach(new java.util.function.Consumer<LamdaMessage>() {
            public void accept(LamdaMessage m) {
                userNames.add(m.color);
            }
        });
        Object[] upperUserNames = null;
        // 1. if element is comprised by letters and numeric, add it to array
        upperUserNames = userNames.stream().filter(a -> (a != null && a.matches("[A-Z0-9]*"))).toArray();
        //
        String concat1 = userNames.stream().collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
        System.out.println("concat1=" + concat1);
        
//        <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner);
        StringBuilder concat2 = userNames.stream().collect(new java.util.function.Supplier<StringBuilder>() {
            @Override
            public StringBuilder get() {
                return new StringBuilder("Init:");
            }

        }, new java.util.function.BiConsumer<StringBuilder, String>() {// accumulator,
                    @Override
                    public void accept(StringBuilder t, String u) {
                        System.out.println("accumulator");
                        t.append(u);
                    }
                }, new java.util.function.BiConsumer<StringBuilder, StringBuilder>() { // // combiner)
                    @Override
                    public void accept(StringBuilder t, StringBuilder u) {
                        System.out.println("Combiner:");
                    }
                });
        System.out.println("concat2=" + concat2);
        // 2.假如你想要把所有提取的字符转换成小写字母。用JDK8的方法做将会像如下这样做：
        upperUserNames = userNames.stream().filter(x -> (x != null && x.matches("[A-Z0-9]*"))).map(String::toLowerCase).toArray();
        // c. 如何从选定的集合中找出 字符 的 数量：
        int sumX = userNames.stream().filter(x -> (x != null && x.matches("[A-Z0-9]*"))).map(String::length).reduce(0, Integer::sum);
        int messageSizeWithName = messages.stream().filter(new java.util.function.Predicate<LamdaMessage>() {
            @Override
            public boolean test(LamdaMessage t) {
                return t.name.equals("aglover");
            }
        }).map(new java.util.function.Function<LamdaMessage, Integer>() {
            @Override
            public Integer apply(LamdaMessage t) {
                return 1;
            }

        }).reduce(0, new java.util.function.BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer t, Integer u) {
                return t + u;
            }
        });
        System.out.println("messageSizeWithName=" + messageSizeWithName);

    }

    public static void main(String[] args) {
        // expression();
        // functionInterface1();
        // functionInterface2();
        // aliasMechnism();
        jcfEnhance();
    }
}
