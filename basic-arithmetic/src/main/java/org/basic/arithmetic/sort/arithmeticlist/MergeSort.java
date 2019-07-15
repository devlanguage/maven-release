
import org.basic.arithmetic.sort.SortIntf;

public class MergeSort implements SortIntf {

    public void sort(int[] data) {

        mergeSort(data, 0, data.length - 1);
    }

    private void mergeSort(int[] datas, int leftIndex, int rightIndex) {
        if (leftIndex < rightIndex) { // 当分块索引有效时
            int center = (leftIndex + rightIndex) / 2;// 找出中间索引
            mergeSort(datas, leftIndex, center); // 把左边到中点的元素集合继续分堆儿
            mergeSort(datas, center + 1, rightIndex); // 把右边到中点的元素集合继续分堆儿
            merge(datas, leftIndex, center, rightIndex); // 归并
        }
    }

    private static void merge(int[] datas, int left, int center, int right) {
        int[] temp = new int[datas.length]; // 建立一个临时的数组，用于装载排序后的数组
        int mind = center + 1; // 第二队的开始索引位置
        int third = left; // 临时数组从第一队的索引开始
        int tmp = left; // 仅仅记录开始索引位置
        while (left <= center && mind <= right) {// 分队后的数组进行比较
            if (datas[left] <= datas[mind]) {
                temp[third++] = datas[left++]; // 左边的略小，左边索引前进
            } else {
                temp[third++] = datas[mind++]; // 右边的略小，右边索引前进
            }
        }
        while (mind <= right) { // 如果第二队数组还没走完，继续走完，将第二队右边的元素都放到临时数组后面
            temp[third++] = datas[mind++];
        }
        while (left <= center) {// 如果第一队数组还没走完，继续走完，将第一队右边的元素都放到临时数组后面
            temp[third++] = datas[left++];
        }
        while (tmp <= right) { // 将临时数组中的所有元素（排序好的），原样覆盖到原先的数组
            datas[tmp] = temp[tmp++];
        }
    }

}
