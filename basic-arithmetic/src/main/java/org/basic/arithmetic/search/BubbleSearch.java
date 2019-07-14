/**
 * The file org.java.arithmetic.BubbleSearch.java was created by yongjie.gong on 2008-6-2
 */
package org.basic.arithmetic.search;

/**
 * @author feiye
 * 
 */
public class BubbleSearch {

    
void BubbleSort() { // R（l..n)是待排序的文件，采用自下向上扫描，对R做冒泡排序
    class KeyValue {
        public int key;            
    }
    KeyValue[] R = new KeyValue[] {};
    int i, j, n = R.length;
    boolean exchange; // 交换标志

    for (i = 1; i < n; i++) { // 最多做n-1趟排序
        exchange = false; // 本趟排序开始前，交换标志应为假
        for (j = n - 1; j >= i; j--)
            // 对当前无序区R[i..n]自下向上扫描
            if (R[j + 1].key < R[j].key) {// 交换记录
                R[0] = R[j + 1]; // R[0]不是哨兵，仅做暂存单元
                R[j + 1] = R[j];
                R[j] = R[0];
                exchange = true; // 发生了交换，故将交换标志置为真
            }
        if (!exchange) // 本趟排序未发生交换，提前终止算法
            return;
    } // endfor(外循环)
} // BubbleSort

    /**
     * @param args
     */
    public static void main(String[] args) {

        //

    }

}
