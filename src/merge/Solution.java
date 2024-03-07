package merge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * leetcode No.56 合并区间
 * 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。请你合并所有重叠的区间，并返回 一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间 。
 */

public class Solution {

    public static int[][] merge(int[][] intervals) {
        int n = intervals.length;
        // 先按照每个区间start值从小到大将区间排序
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n-i-1; j++) {
                if (intervals[j][0] > intervals[j+1][0]) {
                    // 只是将引用互换了，达到了交换两个数组位置的目的（类似于交换两个链表结点）
                    int[] tmp = intervals[j];
                    intervals[j] = intervals[j+1];
                    intervals[j+1] = tmp;
                }
            }
        }

        // 对排好序的intervals从左到右遍历每个区间
        int[] pre = intervals[0];
        List<int[]> res = new ArrayList<>();    // 存放结果
        for (int k = 1; k < n; k++) {
            // todo 包含的情况没做，还得细分
            if (intervals[k][0] <= pre[1]) {    // 两个区间有重叠的情况（右边的左端在左边的右端的左边）
                // 合并
                int[] merged = {pre[0], intervals[k][1]};
                pre = merged;
            } else {    // 两个区间没有重叠的情况（右边的左端在左边的右端的右边）
                // 将前面合并好的区间先加入res
                res.add(pre);
                pre = intervals[k];
            }
        }
        res.add(pre);   // 将最后一个区间加入（无论是合并还是没合并的）
        return res.toArray(new int[res.size()][]);
    }

    public static void main(String[] args) {
        int[][] arr = {
                {2,1},
                {1,2},
                {0,5}
        };

    }
}
