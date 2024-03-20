package subsets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * No.78 子集
 * 给你一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。
 * 解集不能包含重复的子集。你可以按任意顺序返回解集。
 */

public class Solution {

    List<List<Integer>> res = new ArrayList<>();
    // 回溯
    public List<List<Integer>> subsets(int[] nums) {
        int n = nums.length;
        res.add(new ArrayList<>()); // 空集也是子集，先加进去

        for (int l = 1; l <= n; l++) {
            // 搜索长度为i的子集
            dfs(l, 0, nums, 0, new ArrayList<>());
        }
        return res;
    }


    /**
     * 找出长度为subLen的子集
     * @param subLen 本轮找的子集的长度
     * @param x 在subLen长的子集集合中正在确定的数的位置下标
     * @param nums nums数组（顺序不要改变）
     * @param first x位置的数可以在nums[first]及之后的元素中选择
     * @param list 暂存subLen子集的查找进度
     */
    public void dfs(int subLen, int x, int[] nums, int first, List<Integer> list) {
        if (x == subLen) {  // list中下标 0~x-1 的长度已经等于要求的子集长度了，找到一个长度为subLen的子集
            res.add(new ArrayList<>(list));
            return;
        }

        // 每个位置只在它后面的元素中选择
        for (int k = first; k < nums.length; k++) {
            list.add(nums[k]);
            dfs(subLen, x + 1, nums, first + 1, list);
            list.remove(list.size()-1); // 撤销操作，继续尝试子集的x放置其他数
        }
    }
}
