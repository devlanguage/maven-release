package org.basic.arithmetic.sort.arithmeticlist;

import org.basic.arithmetic.sort.SortIntf;
import org.basic.arithmetic.sort.SortUtil;

public class Insert_DirectInsertSort implements SortIntf {

  public void sort(int[] data) {
    for (int i = 1; i < data.length; i++) { // 从数组第二个元素开始遍历
      if (data[i] < data[i - 1]) { // 当前元素和前一个元素进行对比【此处前面的元素已经排序好了】
        Integer temp = data[i]; // 记录当前要比较的元素值
        int j = i - 1; // 从当前元素往前开始比较
        for (; j >= 0 && data[j] > temp; j--) {// 如果满足前面索引有效并且前面的元素值都是比当前值大的，那就进行元素后移动操作
          data[j + 1] = data[j]; // 元素后移
        }
        data[j + 1] = temp;// 前移操作后，j的索引就是中间那个比前面元素大，比后面元素小的位置索引-1将其要对比的值插进去
      }
    }
  }


}
