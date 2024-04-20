package lengthOfLIS;

import java.util.Arrays;

/**
 * No.300 最长递增子序列
 *
 * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
 * 子序列 是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。
 * 子序列 理解：并不要求这个序列在原数组中连续，但是其中元素的相对顺序要和在原数组中保持一致。
 *
 * 子序列问题是动态规划解决的经典问题。
 */

public class Solution {

    /**
     * 动态规划 + 一维dp（好理解）
     * @param nums
     * @return
     */
    // // 时间复杂度：O(n^2)，其中n为nums的长度。分析：求每个dp[i]都要将i之前的所有j都遍历一遍。
    // // 空间复杂度：O(n)，其中n为nums的长度
    // public int lengthOfLIS(int[] nums) {
    //     int n = nums.length;
    //
    //     // dp[i]：从下标0~i中挑选的以nums[i]结尾的最长严格递增子序列的长度
    //     // 为什么强调以nums[i]结尾而不是直接设为0~i中的最长严格递增子序列的长度？
    //     // —— 因为递增比较比的是两个元素，来判断是一个元素是否能接在另一个元素之后严格递增，如果是递增的，那么后一个元素结尾的最长递增序列就要考虑接在较小元素后面的情况
    //     int[] dp = new int[n];
    //
    //     // 递推公式：dp[i] = max{dp[j] + 1}，j < i且nums[i] > nums[j]
    //     // 考察i前面的每一个满足 nums[j] < nums[i] 的情况，这表示nums[i]可以接在nums[j]的后面，那么这种情况的最长递增序列长度就等于dp[j] + 1，1就是nums[i]
    //
    //     // 初始化
    //     Arrays.fill(dp, 1); // 以每个元素为结尾的严格递增子序列至少是它自己
    //
    //     int maxLen = 1;
    //
    //     // 遍历顺序：从前往后
    //     for (int i = 1; i < n; i++) {
    //         for (int j = 0; j < i; j++) {
    //             if (nums[i] > nums[j]) {
    //                 dp[i] = Math.max(dp[i], dp[j] + 1);
    //             }
    //         }
    //
    //         if (dp[i] > maxLen) {
    //             maxLen = dp[i];
    //         }
    //     }
    //
    //     return maxLen;  // 最后nums数组的最长严格递增子序列可能出现在以其中任一个元素结尾，因此结果为max{dp[i]}，这里用maxLen变量在计算每个dp[i]的同时维护好了
    // }





    /**
     * 贪心 + 二分查找（比较难想到和理解）
     * 讲解见：https://writings.sh/post/longest-increasing-subsequence-revisited#footnote-1
     * 直觉上， 要让序列上升的更慢，才能找到更长的递增子序列 。
     *
     * 优化时间复杂度到 O(nlogn)
     * 从哪个角度优化呢？
     * 外循环计算每一个dp[i]无法优化，因为必须都要计算
     * 内循环需要遍历[0,i)的所有j找到所有小于nums[i]的nums[j]，本来由于nums[i]前面的数是无序的，所以必须都遍历一遍才能找全
     * 那么能否通过重新设计状态定义，使整个 dp 为一个排序列表；这样在计算每个 dp[i] 时，就可以通过二分法遍历 [0,i) 区间元素，将此部分复杂度由 O(n) 降至 O(logn)。
     *
     * @param nums
     * @return
     */
    // 时间复杂度：O(nlogn)，其中n为nums的长度。
    // 空间复杂度：O(n)，其中n为nums的长度
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;

        // tails[i]：所有长度为i+1的递增子序列的末尾元素的最小值
        // 数组tails是严格递增的，反证法可证
        // 理解：将各种长度的上升子序列的增长最慢的情况记录下来
        int[] tails = new int[n];
        tails[0] = nums[0];
        int len = 1;    // 记录目前最长上升子序列的长度


        // 从前往后遍历每一个元素，并维护tails数组的递增性
        for (int i = 1; i < n; i++) {
            // 如果遇到一个比 tails 的尾部元素更大的值，说明形成了一个更长的上升序列。 则把它追加到 tails 的尾部。
            if (nums[i] > tails[len-1]) {   // 长度len对于tails数组的下标len-1
                len++;
                tails[len - 1] = nums[i];
            } else {    // 如果遇到一个比 tails 的尾部元素更小的值，说明发现了某个上升子序列的、更小的末尾元素，需要更新它（如果相等的话按照下面的代码会更新同样的值，不影响结果）
                // 在 tails 数组中二分查找，找到第一个比nums[i]小的数 tails[pos] ，并更新 tails[pos+1]=nums[i]
                int l = 0, r = len-1, pos = -1;    // pos指向tails中第一个比nums[i]小的数。如果找不到比nums[i]小的tails元素，则应该更新tails[0]，故pos初始化为-1
                while (l <= r) {
                    int mid = l + (r-l)/2;  // 中间或偏左
                    if (nums[i] > tails[mid]) { // 至少找到一个比nums[i]的了，先更新pos到这里
                        pos = mid;
                        l = mid + 1;
                    } else {
                        r = mid - 1;
                    }
                }
                tails[pos + 1] = nums[i];   // 更新的是pos+1
            }
        }
        return len;
    }
}
