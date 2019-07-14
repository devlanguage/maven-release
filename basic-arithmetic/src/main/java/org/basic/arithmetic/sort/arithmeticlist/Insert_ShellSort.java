package org.basic.arithmetic.sort.arithmeticlist;

import org.basic.arithmetic.sort.SortIntf;
import org.basic.arithmetic.sort.SortUtil;

public class Insert_ShellSort implements SortIntf {

    public void sort(int[] data) {

        for (int i = data.length / 2; i > 2; i /= 2) {
            for (int j = 0; j < i; j++) {
                insertSort(data, j, i);
            }
        }
        insertSort(data, 0, 1);
    }

    private void insertSort(int[] data, int start, int inc) {

        for (int i = start + inc; i < data.length; i += inc) {
            for (int j = i; (j >= inc) && (data[j] < data[j - inc]); j -= inc) {
                SortUtil.swap(data, j, j - inc);
            }
        }
    }

}
