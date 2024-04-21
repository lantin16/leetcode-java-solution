package findLengthOfLCIS;

import java.util.Arrays;

/**
 * No.674 最长连续递增序列
 *
 * 给定一个未经排序的整数数组，找到最长且 连续递增的子序列，并返回该序列的长度。
 *
 * 连续递增的子序列 可以由两个下标 l 和 r（l < r）确定，如果对于每个 l <= i < r，都有 nums[i] < nums[i + 1] ，那么子序列 [nums[l], nums[l + 1], ..., nums[r - 1], nums[r]] 就是连续递增子序列。
 *
 * 理解：子序列可以在原数组中不连续，只要保证相对顺序正确即可
 * 但本题还要求在原数组中也是连续的
 */

public class Solution {

    /**
     * 贪心
     * 空间复杂度相较于动态规划的解法更优
     * @param nums
     * @return
     */
    // 时间复杂度：O(n)，其中n为nums数组的长度
    // 空间复杂度：O(1)
    public int findLengthOfLCIS(int[] nums) {
        int maxLen = 1; // 记录当前最长连续递增序列的长度
        int curLen = 1; // 记录当前正在遍历的连续递增序列的长度

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i-1]) {  // 当前仍处于递增序列
                curLen++;
            } else {    // 当前元素已经不递增了
                maxLen = Math.max(maxLen, curLen);  // 维护maxLen
                curLen = 1; // 开始重新记录一个新的递增序列
            }
        }
        maxLen = Math.max(maxLen, curLen);  // 防止最后一个递增序列一直到结束而漏掉比较它的长度
        return maxLen;
    }



    /**
     * 动态规划
     * @param nums
     * @return
     */
    // // 时间复杂度：O(n)，其中n为nums数组的长度
    // // 空间复杂度：O(n)
    // public int findLengthOfLCIS(int[] nums) {
    //     int n = nums.length;
    //
    //     // dp[i]：以nums[i]结尾的最长连续递增子序列的长度
    //     int[] dp = new int[n];
    //
    //     // 递推公式：dp[i] = max{1, dp[i-1] + 1}，如果nums[i] > nums[i-1]才考虑第二项
    //
    //     // 初始化
    //     Arrays.fill(dp, 1); // 以每个元素结尾的最长连续递增子序列最少是它自己
    //     int maxLen = 1; // 记录当前最长连续递增序列的长度
    //
    //     for (int i = 1; i < n; i++) {
    //         if (nums[i] > nums[i-1]) {  // 当前仍处于递增序列
    //             dp[i] = dp[i-1] + 1;
    //         }
    //         maxLen = Math.max(maxLen, dp[i]);
    //     }
    //
    //     return maxLen;
    // }
}
