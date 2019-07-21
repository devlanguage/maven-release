package org.basic.arithmetic.sort.arithmeticlist;

import org.basic.arithmetic.sort.SortIntf;
import org.basic.arithmetic.sort.SortUtil;

public class HeapSort implements SortIntf {

    public void buildHeap(int[] data, int lastIndex) {// 构建堆数组
        for (int i = (lastIndex - 1) / 2; i >= 0; i--) {// 从目标的父节点开始遍历
            int maxIndex = i; // 记录父节点位置
            while (maxIndex * 2 + 1 <= lastIndex) { // 当父节点的子节点存在的时候
                int biggerIndex = maxIndex * 2 + 1;// 默认附一个大值为父节点的左子节点的索引
                if (biggerIndex < lastIndex) {// 此处判断是否父节点还有一个右子节点
                    if (data[biggerIndex] < data[biggerIndex + 1]) { // 如果有右子节点判断左右子节点的值大小，记录一个最大的位置，好用于交换
                        biggerIndex++;
                    }
                }
                if (data[maxIndex] < data[biggerIndex]) { // 此处是比较父节点值和左右子节点值，找个最大的做父亲
                    SortUtil.swap(data, maxIndex, biggerIndex);
                    maxIndex = biggerIndex; // 记录一下最大值的索引
                } else {
                    break;
                }
            }
        }
    }

    public void sort(int[] data) {// * 堆排序
        int arrayLength = data.length;
        for (int i = 0; i < arrayLength - 1; i++) { // 遍历数组
            buildHeap(data, arrayLength - 1 - i);// 构建堆
            SortUtil.swap(data, 0, arrayLength - 1 - i);
        }

    }
}
