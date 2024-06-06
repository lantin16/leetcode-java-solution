package firstMissingPositive;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * No.41 缺失的第一个正数
 *
 * 给你一个未排序的整数数组 nums ，请你找出其中没有出现的最小的正整数。
 * 请你实现时间复杂度为 O(n) 并且只使用常数级别额外空间的解决方案。
 */

public class Solution {

    /**
     * 用哈希表记录出现过的数
     * @param nums
     * @return
     */
    // 时间复杂度：O(n)。分析：最多遍历两次数组中的所有数
    // 空间复杂度：O(n)
    // public int firstMissingPositive(int[] nums) {
    //     Set<Integer> hs = new HashSet<>();
    //     for(int num : nums) {
    //         hs.add(num);
    //     }
    //
    //     // 从1开始往后挨个找第一个缺失的正数
    //     int res = 1;
    //     while(hs.contains(res)) {
    //         res++;
    //     }
    //     return res;
    // }


    /**
     * 原地哈希（巧妙）
     * 参考https://leetcode.cn/problems/first-missing-positive/solutions/7703/tong-pai-xu-python-dai-ma-by-liweiwei1419
     *
     * 明确：
     * 1. 设 N 是数组的长度，那么要找的数一定在 [1, N + 1] 这个区间里
     * 2. 可以就把原始的数组当做哈希表来使用，映射规则是数值为 i 的数映射到下标为 i - 1 的位置。
     *
     * @param nums
     * @return
     */
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;

        // 按照自定义的哈希规则将数组尽可能归位
        for (int i = 0; i < n; i++) {
            // 这里用while循环而不是if是因为可能换过来的数仍然需要归位，因此需要不断交换直到换过来的数不在[1,n+1]范围内（流浪者）或换过来的数刚好就应该在这（归位）
            // 解释下这个循环条件：
            // 1. 首先，它得是[1, n]范围内的数才在nums中有对应的位置，才能够归位
            // 2. nums[i]不是索引i应该放的数（i+1），因此nums[i]需要回到自己的位置（自己的正确位置应该为索引nums[i]-1）
            // 3. 为了防止nums[i]的正确位置上已经有了一个nums[i]，即出现重复数的情况，故交换前还需要判断自己的位置上是否已经有了nums[i]，如果有也不用换了，因为换了也是自己换自己
            while ((nums[i] >= 1 && nums[i] <= n) && nums[i] != i + 1 && nums[nums[i] - 1] != nums[i]) {
                swap(nums, i, nums[i] - 1); // 将nums[i]换到自己的正确位置
            }
        }

        // 从前往后遍历，找到第一个索引 i 位置不是数 i+1 的，则第一个缺失的正数就是i+1
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        return n + 1;   // 如果n个数恰好为1~n，那么它们恰好能全部归位，则结果为n+1
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

}
