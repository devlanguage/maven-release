
import org.basic.arithmetic.sort.SortIntf;
import org.basic.arithmetic.sort.SortUtil;

/**
 * <pre>
 *  一个长度n的数组，用冒泡法;
 *  将第一个最大/小数冒泡到顶需要    需要比较 n-1 次
 *  第二个 ： n-2次
 *  第三个 ： n-3次
 *  。
 *  。
 *  第k个 :  n-k次
 *  。
 *  。
 *  第n-1个：n-(n-1)=1 次
 *  第n个不需要比，所以
 *  总次数 = { 1+(n-1)}*(n-1)/2 
 *  想象成一个梯形就容易理解了
 * </pre>
 */
public class Swap_BubbleSort implements SortIntf {

    public void sort(int[] data) {
        count.set(0);
        // bubbleSimple(data);
        bubbleSort02(data);
        System.out.println("count=" + count);
    }

    public void bubbleSimple(int[] data) {
        for (int i = 0; i < data.length; ++i) {
            for (int k = 0; k < data.length - i - 1; ++k) {
                if (data[k] > data[k + 1]) {// 由小到大 ， data[k] < data[k + 1] {// 由大到小
                    SortUtil.swap(data, k, k + 1);
                }
                count.incrementAndGet();
            }
        }
    }

    // int lastSwappedPosition = data.length;
    public void bubbleSort01(int[] data) {
        boolean noSwapped = true;
        for (int i = 0; i < data.length; ++i) {
            noSwapped = true;
            for (int j = 0; j < data.length - i - 1; ++j) {
                if (data[j] > data[j + 1]) {
                    SortUtil.swap(data, j, j + 1);
                    noSwapped = false;
                }
                count.incrementAndGet();
            }
            if (noSwapped) {// 本趟排序未发生交换，提前终止算法
                return;
            }
        }
    }

    public void bubbleSort02(int[] data) {
        int n = data.length;
        boolean exchange;
        for (int i = 1; i < n; i++) {// 最多做n-1趟排序
            exchange = false;
            for (int j = n - 1; j > i; j--) {
                if (data[j - 1] > data[j]) {
                    SortUtil.swap(data, j - 1, j);
                    exchange = true;
                }
                count.incrementAndGet();
            }
            if (!exchange) {// 本趟排序未发生交换，提前终止算法
                return;
            }
        }
    }
}
