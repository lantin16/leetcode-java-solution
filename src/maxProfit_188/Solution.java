package maxProfit_188;

/**
 * No.188 买卖股票的最佳时机Ⅳ
 *
 * 给你一个整数数组 prices 和一个整数 k ，其中 prices[i] 是某支给定的股票在第 i 天的价格。
 * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 k 笔交易。也就是说，你最多可以买 k 次，卖 k 次。
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 *
 * 与No.123的区别：可以最多交易k次二不止固定的2次。
 * 原理相同，扩展：
 * 每天结束时的状态可能有 2k+1 种：
 * -0 未进行过任何操作；
 * -1 只完成了第一次买
 * -2 完成了第一次买和卖，即完成了一笔交易
 * -3 只完成了第二次买
 * -4 完成了第二次买和卖，即完成了二笔交易
 * ...
 * -2k-1 只完成了第k次买
 * -2k 完成了第k次买和卖，即完成了k笔交易
 */

public class Solution {


    /**
     * 动态规划 + 二维dp
     * 将每天的所有可能状态定义存储在第二维
     * @param k
     * @param prices
     * @return
     */
    // // 时间复杂度：O(nk)，其中n为prices数组的长度
    // // 空间复杂度：O(nk)，其中n为prices数组的长度
    // public int maxProfit(int k, int[] prices) {
    //     int n = prices.length;
    //
    //     // dp[i][j]：第i天结束时处于状态j的情况下手里的现金最多为dp[i][j]
    //     int[][] dp = new int[n][k * 2 + 1];
    //
    //     // 递推公式
    //     // dp[i][2r-1] = max{dp[i-1][2r-1], dp[i-1][2r-2] - prices[i]}
    //     // dp[i][2r] = max{dp[i-1][2r], dp[i-1][2r-1] + prices[i]}
    //
    //     // 初始化
    //     // 将第0天买入但没卖出的所有状态设为-prices[0]
    //     for (int r = 1; r <= k; r++) {
    //         dp[0][r * 2 - 1] = -prices[0];
    //     }
    //
    //     // 遍历顺序：从前往后
    //     for (int i = 1; i < n; i++) {
    //         // 计算2k+1种状态（其中0状态不用算，保持默认值0即可）
    //         for (int r = 1; r <= k; r++) {  // 对于每一次交易状态
    //             dp[i][2*r-1] = Math.max(dp[i-1][2*r-1], dp[i-1][2*r-2] - prices[i]);
    //             dp[i][2*r] = Math.max(dp[i-1][2*r], dp[i-1][2*r-1] + prices[i]);
    //         }
    //     }
    //
    //     // 最后处于状态2k，即完成了k笔交易时利润最大
    //     // 完成少于k笔交易时利润最大的情况也会转移到dp[n-1][2k]，因为可以看作把没花完的交易次数在同一天买入又卖出多次，不影响结果
    //     return dp[n-1][2*k];
    // }





    /**
     * 动态规划 + 一维滚动数组
     * 空间复杂度优化
     * @param k
     * @param prices
     * @return
     */
    // 时间复杂度：O(nk)，其中n为prices数组的长度
    // 空间复杂度：O(k)。分析：只需要用到两个长度为2k+1的滚动数组。
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;

        // dp[j]：某天结束时处于状态j的情况下手里的现金最多为dp[j]
        int[] dp = new int[k * 2 + 1];
        // 记录前一天的状态
        int[] pre = new int[k * 2 + 1];

        // 递推公式
        // dp[2r-1] = max{pre[2r-1], pre[2r-2] - prices[i]}
        // dp[2r] = max{pre[2r], pre[2r-1] + prices[i]}

        // 初始化
        // 将第0天买入但没卖出的所有状态设为-prices[0]
        for (int r = 1; r <= k; r++) {
            pre[r * 2 - 1] = -prices[0];
        }

        // 遍历顺序：从前往后
        for (int i = 1; i < n; i++) {
            // 计算2k+1种状态（其中0状态不用算，保持默认值0即可）
            for (int r = 1; r <= k; r++) {  // 对于每一次交易状态
                dp[2*r-1] = Math.max(pre[2*r-1], pre[2*r-2] - prices[i]);
                dp[2*r] = Math.max(pre[2*r], pre[2*r-1] + prices[i]);
            }

            // 更新pre数组
            System.arraycopy(dp, 0, pre, 0, dp.length);
        }

        // 最后处于状态2k，即完成了k笔交易时利润最大
        // 完成少于k笔交易时利润最大的情况也会转移到dp[2k]，因为可以看作把没花完的交易次数在同一天买入又卖出多次，不影响结果
        return dp[2*k];
    }
}
