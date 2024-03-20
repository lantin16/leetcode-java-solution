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
     *
     * 例如：nums = [1,2,3,4]
     * 求其中长度为3的子集（subLen=3）
     * 1. 确定长度为3的子集的下标0处的元素（x=0），first=0，下标0处可以选择nums[0]~nums[len-1]的任意一个元素，假设按顺序选了nums[0] =1，目前 list={1}
     * 2. 递归开始确定长度为3的子集的下标1处的元素（x+1=1），first=1（k+1），下标1处可以选择nums[1]~nums[len-1]的任意一个元素，假设按顺序选了nums[1] =2，目前 list={1，2}
     * 3. 类似的选择找到了{1,2,3}和{1,2,4}两个以1，2开头的子集
     * 4. 然后回溯到选择下标1为nums[1]=2的函数中了，会将选择2的操作撤销，此时list={1}。
     * 然后for循环就会选择下一个k=2，nums[2]=3放在list的下标1处，此时递归搜索下标为2的位置就只能选nums[2]之后的元素，只有4，因此以1，3开头的长度为3的子集只有{1,3,4}
     * 这里每挑选nums的一个元素放到对应位置后，下一个位置的元素就只能从nums中该元素之后的元素中选择，这样就可以保证加入{1,2,3}后不会出现{1,3,2}的情况
     *
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

        for (int k = first; k < nums.length; k++) {
            list.add(nums[k]);
            dfs(subLen, x + 1, nums, k + 1, list);  // x+1位置的数只能从这次选的nums[k]后面的元素中选择
            list.remove(list.size()-1); // 撤销操作，继续尝试子集的x放置其他数
        }
    }


    // TODO 二进制和位运算的方法了解一下，用chatGPT解释下题解代码
}
