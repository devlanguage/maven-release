package org.basic.jdk.core.s2_logic;

public class if_switch {

    public static void main(String[] args) {
        // Math.random(): 这个函数属于类 java.lang.Math. 用于随机生成一个大于0并且小于1的小数，每次生成都不一样
        // Math.random() * 10: 随机生成一个位于 10*(0...1)，即 (0...10)， 生成一个大于0,小于10的小数
        // (int) (Math.random() * 10)： 随机生成一个0..10之间的小数，并强制转换成整数，即去掉小数部分
        int random_任意数 = (int) (Math.random() * 10);

        test_if_语句(random_任意数);
        test_switch_语句(random_任意数);

    }

    private static void test_switch_语句(int random_任意数) {
        System.out.println("=============switch 语句测试===========");
        switch (random_任意数) {
            case 1: {
                System.out.println("当前整数是：" + random_任意数);
                break;
            }
            case 3: {
                System.out.println("当前整数是：" + random_任意数);
                break;
            }
            case 4: {
                System.out.println("当前整数是：" + random_任意数);
                break;
            }
            case 5: {
                System.out.println("当前整数是：" + random_任意数);
                break;
            }
            case 8: {
                System.out.println("当前整数是：" + random_任意数);
                break;
            }
            default: {
                System.out.println("default, 没有匹配的case语句. 但前整数：" + random_任意数);
            }
        }
    }

    private static void test_if_语句(int random_任意数) {
        System.out.println("=============if 语句测试===========");
        if (random_任意数 < 3) {
            System.out.println("小于3");
        } else if (random_任意数 < 6) {
            System.out.println("大于3并且小于6");
        } else if (random_任意数 < 8) {
            System.out.println("大于6并且小于8");
        } else {
            System.out.println("大于8");
        }

    }
}
