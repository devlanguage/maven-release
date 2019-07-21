package org.basic.grammar.s1_type_var;

public class ArrayTest {

    public static void main(String[] args) {
        int i1, ia2[] = new int[2], ia3[] = new int[3];
        int[] i4 = new int[4], i5 = new int[5], i6;
        // int ia4[3];//Syntax error on token "3", delete this token

        // 基本类型数组， 以整数类型 int为例
        System.out.println("第一个 int类型数组==================");
        int[] intArray_01 = new int[] { 10, 23, 453 };
        for (int i = 0; i < intArray_01.length; i++) {
            System.out.println(intArray_01[i]);
        }

        // 字符串数组 字符串对象java.lang.String
        String[] userNameArray_01 = new String[5];
        System.out.println("第一个字符串数组==================");
        for (int i = 0; i < userNameArray_01.length; i++) {
            System.out.println("   " + userNameArray_01[i]);
        }

        System.out.println("第二个字符串数组==================");
        String[] userNameArray_02 = new String[] { "ZhangSan-01", "ZhangSan-02" };
        for (int i = 0; i < userNameArray_02.length; i++) {
            System.out.println(userNameArray_02[i]);
        }

        // 对象数组， 以java.util.Date为例
        System.out.println("第一个 Date对象数组==================");
        java.util.Date[] dateArray_01 = new java.util.Date[] { new java.util.Date() };
        for (int i = 0; i < dateArray_01.length; i++) {
            System.out.println(dateArray_01[i]);
        }

    }
}
