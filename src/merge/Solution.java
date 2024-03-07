package merge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * leetcode No.56 合并区间
 * 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。请你合并所有重叠的区间，并返回 一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间 。
 */

public class Solution {

    // 先按照区间的左端点排序，那么在排完序的列表中，可以合并的区间一定是连续的
    public int[][] merge(int[][] intervals) {
        int n = intervals.length;

        // 先按照每个区间start值从小到大将区间排序
        // 自定义二维数组的排序：实现Comparator接口，将其传入Arrays.sort(排序的内容，比较器)）
        // 这种方式比起手动冒泡排序更快

        // 写法一： Lambda表达式的写法更简洁
        Arrays.sort(intervals, (interval1, interval2) -> {return interval1[0]-interval2[0];});

        // 写法二：比较复杂
//        Arrays.sort(intervals, new Comparator<int[]>() {    // int[] 不是基本数据类型，所以在使用map集合等等泛型中，int[]直接写就好，不能写Integer[]
//            // compare方法根据其返回值确定比较对象的大小，如果返回值为正，认为o1>o2；返回值为负，认为o1<o2；返回值为0，认为两者相等；
//            // 也可以这么理解：返回值>0交换会交换前后两个元素的位置
//            public int compare(int[] interval1, int[] interval2) {
//                return interval1[0] - interval2[0]; // Arrays.sort默认升序
//            }
//        });

        // 对排好序的intervals从左到右遍历每个区间
        int[] pre = intervals[0];
        List<int[]> res = new ArrayList<>();    // 存放结果
        for (int k = 1; k < n; k++) {
            if (intervals[k][0] <= pre[1]) {    // 两个区间有重叠的情况（右边的左端在左边的右端的左边）
                // 合并
                if (intervals[k][1] > pre[1]) { // 有重叠但不包含
                    pre[0] = pre[0];
                    pre[1] = intervals[k][1];
                }
                // 有重叠且后面的区间被前一个包含时合并后的区间仍为pre
            } else {    // 两个区间没有重叠的情况（右边的左端在左边的右端的右边）
                // 将前面合并好的区间先加入res
                res.add(pre);
                pre = intervals[k];
            }
        }
        res.add(pre);   // 将最后一个区间加入（无论是合并还是没合并的）
        return res.toArray(new int[res.size()][]);
    }
}
