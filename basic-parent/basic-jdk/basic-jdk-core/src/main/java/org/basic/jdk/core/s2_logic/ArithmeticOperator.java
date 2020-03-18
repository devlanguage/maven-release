package org.basic.jdk.core.s2_logic;

public class ArithmeticOperator {

    public static void main(String[] args) {

        // 加减乘除 +, -, *, /
        System.out.println("======== 加减乘除 +, -, *, /  ===========");
        int number01 = 10, number02 = 2;
        System.out.println("加法测试：" + (number01 + number02));
        System.out.println("减法测试：" + (number01 - number02));
        System.out.println("乘法测试：" + (number01 * number02));
        System.out.println("除法测试：" + (number01 / number02));

        // 自加运算符 ++，自减运算符--
        System.out.println("========  自加运算符 ++，自减运算符--  ===========");
        number01 = 10;
        System.out.print("自加测试(i++)：" + (number01++));
        System.out.println("______结果， number01=" + number01);
        number01 = 10;
        System.out.print("自加测试(++i)：" + (++number01));
        System.out.println("______结果， number01=" + number01);

        number01 = 10;
        System.out.print("自减测试(i--)：" + (number01--));
        System.out.println("______结果， number01=" + number01);
        number01 = 10;
        System.out.print("自减测试(--i)：" + (--number01));
        System.out.println("______结果， number01=" + number01);

        // 求相除之后的余数，乘方
        System.out.println("========  求相除之后的余数，乘方  ===========");        
        System.out.println("10除以5的余数：" + 10 % 5);
        System.out.println("10除以15的余数：" + 10 % 15);
        System.out.println("10除以3的余数：" + 10 % 3);
        System.out.println("2的三次方：" + Math.pow(2, 3));

    }
}
