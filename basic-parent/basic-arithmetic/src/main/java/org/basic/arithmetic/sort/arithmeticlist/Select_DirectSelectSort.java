package org.basic.arithmetic.sort.arithmeticlist;

import org.basic.arithmetic.sort.SortIntf;
import org.basic.arithmetic.sort.SortUtil;

public class Select_DirectSelectSort implements SortIntf {

    public void sort(int[] data) {

        for (int i = 0; i < data.length; i++) {
            int lowIndex = i;
            for (int j = data.length - 1; j > i; j--) {
                if (data[j] < data[lowIndex]) {
                    lowIndex = j;
                }
            }
            SortUtil.swap(data, i, lowIndex);
        }
    }

    // 选择排序法
    public static void selectSort(Integer[] data) {
        for (int i = 0; i < data.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < data.length; j++) {
                if (data[i] < data[j] && data[minIndex] < data[j]) {
                    // 只记住标记
                    minIndex = j;
                }
            }
            // 每次只交换一次即可
            if (minIndex != i) {
                Integer temp = data[i];
                data[i] = data[minIndex];
                data[minIndex] = temp;
            }
        }
    }

    public static void main(String[] args) {
        Integer a = 2 + 1;
        Integer b = 3;
        a = 3 - 0;
        System.out.println(a == b);
    }

}
