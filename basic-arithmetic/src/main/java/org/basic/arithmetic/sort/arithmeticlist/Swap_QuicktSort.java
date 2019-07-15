
import org.basic.arithmetic.sort.SortIntf;
import org.basic.arithmetic.sort.SortUtil;

/**
 * <pre>
 * 所谓快速排序法是从待排序的数组中找一个标本作为分界值（一般是数组的第一个元素），所有比这个值小的值放到它的左边（或者右边），将比它大的值放到它的右边（或者左边），
 *  这样这个分界值左右两边的值要么全部比它大，要么全部比它小。之后再利用递归，将此标本右边、左边的所有元素也按部就班。算法如下
 * </pre>
 * 
 */

public class Swap_QuicktSort implements SortIntf {

    public void sort(int[] data) {
        count.set(0);
        quickSort(data, 0, data.length - 1);
    }

    private void quickSort(int[] data, int low, int high) {

        int pivotIndex = (low + high) / 2;// 用区间的第1个记录作为基准 '
        SortUtil.swap(data, pivotIndex, high);

        int k = partition(data, low - 1, high, data[high]);
        SortUtil.swap(data, k, high);

        if (low < (k - 1)) {
            quickSort(data, low, k - 1);
        }
        if ((k + 1) < high) {
            quickSort(data, k + 1, high);
        }

    }

    private int partition(int[] data, int low, int high, int pivot) {
        do {
            while (data[++low] < pivot) {
                count.incrementAndGet();
                // blank
            }
            while ((high != 0) && data[--high] > pivot) {
                count.incrementAndGet();
                // Blank
            }
            SortUtil.swap(data, low, high);
        } while (low < high);
        SortUtil.swap(data, low, high);
        return low;
    }

    public static void quickSort1(int[] data, int startIndex, int endIndex) {
        if (startIndex < endIndex) {
            Integer startData = data[startIndex];// 标本
            int i = startIndex; // 左边的开始索引
            int j = endIndex + 1; // 右边的开始索引
            while (true) {
                while (i < endIndex && data[++i] > startData) { // 找左边比标本大的下标
                }
                while (j > startIndex && data[--j] < startData) { // 找右边比标本小的下标
                }

                if (i < j) {
                    SortUtil.swap(data, i, j);
                } else {
                    break;
                }
            }
            SortUtil.swap(data, startIndex, j); // 交换开始位置、j的元素为止
            quickSort1(data, startIndex, j - 1);// 递归标本左边
            quickSort1(data, j + 1, endIndex); // 递归标本右边
        }
    }
}

// int Partition(int[] data, int i, int j) {
// // 调用Partition(R，low，high)时，对R[low..high]做划分，
//
// // 并返回基准记录的位置
// int pivot = data[i]; // 用区间的第1个记录作为基准 '
// while (i < j) { // 从区间两端交替向中间扫描，直至i=j为止
// while (i < j && data[j] >= pivot)
// // pivot相当于在位置i上
// j--; // 从右向左扫描，查找第1个关键字小于pivot.key的记录R[j]
// if (i < j) // 表示找到的R[j]的关键字<pivot.key
// data[i++] = data[j]; // 相当于交换R[i]和R[j]，交换后i指针加1
// while (i < j && data[i] <= pivot)
// // pivot相当于在位置j上
// i++; // 从左向右扫描，查找第1个关键字大于pivot.key的记录R[i]
// if (i < j) // 表示找到了R[i]，使R[i].key>pivot.key
// data[j--] = data[i]; // 相当于交换R[i]和R[j]，交换后j指针减1
// } // endwhile
// data[i] = pivot; // 基准记录已被最后定位
// return i;
// } // partition

