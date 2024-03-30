package minCostClimbingStairs;

/**
 * No.746 使用最小花费爬楼梯
 *
 * 给你一个整数数组 cost ，其中 cost[i] 是从楼梯第 i 个台阶向上爬需要支付的费用。一旦你支付此费用，即可选择向上爬一个或者两个台阶。
 * 你可以选择从下标为 0 或下标为 1 的台阶开始爬楼梯。
 * 请你计算并返回达到楼梯顶部的最低花费。
 */

public class Solution {

    /**
     * 动态规划
     */
    // // 时间复杂度：O(n)
    // // 空间复杂度：O(n)
    // public int minCostClimbingStairs(int[] cost) {
    //     int n = cost.length;
    //     // 1. dp[i]表示爬到下标为0的台阶的最低花费
    //     int[] dp = new int[n + 1];    // 下标为 n 的台阶才是楼顶
    //     // 2. dp[i] = min{dp[i-2] + cost[i-2], dp[i-1] + cost[i-1]}
    //     // 3. 初始化：从下标为0或1的阶梯开始，意味着到0或1阶梯不需要花费，其余元素初始时还没到到，因此也初始化为0
    //     // 4. 遍历顺序：单层循环，从前往后
    //     for (int i = 2; i <= n; i++) {
    //         dp[i] = Math.min(dp[i - 2] + cost[i - 2], dp[i - 1] + cost[i - 1]);
    //     }
    //
    //     return dp[n];
    // }



    /**
     * 动态规划，空间复杂度优化
     */
    // 时间复杂度：O(n)
    // 空间复杂度：O(1)
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        int[] dp = new int[2];
        dp[0] = 0;
        dp[1] = 0;
        for (int i = 2; i <= n; i++) {
            int min = Math.min(dp[0] + cost[i - 2], dp[1] + cost[i - 1]);
            dp[0] = dp[1];
            dp[1] = min;
        }

        return dp[1];
    }
}
