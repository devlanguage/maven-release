package org.basic.arithmetic.sort.arithmeticlist;

import org.basic.arithmetic.sort.SortIntf;
import org.basic.arithmetic.sort.SortUtil;

public class Insert_BinaryInsertSort implements SortIntf {

    public void sort(int[] data) {
        binaryInsertSort(data);
    }

    public static void binaryInsertSort(int[] data) {// 折半排序
        for (int i = 1; i < data.length; i++) { // 从数组第二个元素开始遍历
            Integer temp = data[i]; // 记录当前要比较的元素值
            int low = 0; // 低位开始
            int hight = i - 1; // 高位开始
            while (low <= hight) {// 位置有效，低位、高位
                int mind = (low + hight) / 2; // 中间位置
                if (temp > data[mind]) { // 被比较元素大于中间元素
                    low = mind + 1; // 低位调整在中点之后
                } else {// 被比较元素小于中间元素
                    hight = mind - 1; // 高位在中点之前
                }
            }
            for (int j = i; j > low; j--) { // 低高位调整完毕后，将中点元素依次往后移动
                data[j] = data[j - 1]; // 元素后移
            }
            // 前移操作后，low的索引就是中间那个比前面元素大，比后面元素小的位置索引low将其要对比的值插进去
            data[low] = temp;
        }
    }

}
