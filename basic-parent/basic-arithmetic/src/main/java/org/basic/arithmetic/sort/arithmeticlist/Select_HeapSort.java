package org.basic.arithmetic.sort.arithmeticlist;

import org.basic.arithmetic.sort.SortIntf;
import org.basic.arithmetic.sort.SortUtil;

class MaxHeap {

    void init(int[] data) {

        this.queue = new int[data.length + 1];
        for (int i = 0; i < data.length; i++) {
            queue[++size] = data[i];
            fixUp(size);
        }
    }

    private int size = 0;
    int[] queue;

    public int get() {
        return queue[1];
    }

    public void remove() {

        SortUtil.swap(queue, 1, size--);
        fixDown(1);
    }

    // fixdown
    private void fixDown(int k) {

        int j;
        while ((j = k << 1) <= size) {
            if (j < size && queue[j] < queue[j + 1])
                j++;
            if (queue[k] > queue[j]) // ���ý���
                break;
            SortUtil.swap(queue, j, k);
            k = j;
        }
    }

    private void fixUp(int k) {

        while (k > 1) {
            int j = k >> 1;
            if (queue[j] > queue[k])
                break;
            SortUtil.swap(queue, j, k);
            k = j;
        }
    }
}

public class Select_HeapSort implements SortIntf {

    public void sort(int[] data) {

        MaxHeap h = new MaxHeap();
        h.init(data);
        for (int i = 0; i < data.length; i++)
            h.remove();
        System.arraycopy(h.queue, 1, data, 0, data.length);
    }

}
