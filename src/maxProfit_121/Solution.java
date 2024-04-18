package maxProfit_121;

/**
 * No.121 买卖股票的最佳时机
 *
 * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
 * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
 * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
 */

public class Solution {

    /**
     * 暴力遍历（超时）
     * @param prices
     * @return
     */
    // // 时间复杂度：O(n^2)
    // // 空间复杂度：O(1)
    // public int maxProfit(int[] prices) {
    //     int max = 0;
    //     for (int i = 0; i < prices.length; i++) {   // 买入时机
    //         for (int j = i + 1; j < prices.length; j++) {   // 卖出时机
    //             max = Math.max(max, prices[j] - prices[i]);
    //         }
    //     }
    //     return max;
    // }


    /**
     * 动态规划 + 一维dp
     * @param prices
     * @return
     */
    // // 时间复杂度：O(n)，n为总共天数，即prices数组的长度
    // // 空间复杂度：O(n)，n为总共天数，即prices数组的长度
    // public int maxProfit(int[] prices) {
    //     int n = prices.length;
    //     // dp[j]：在第j天及之前卖出股票能获取的最大利润（这里的j指下标，从0开始）
    //     int[] dp = new int[n];
    //
    //     // 在第j天及之前卖出股票能获取的最大利润分两种情况：
    //     // 1. 在第j天之前就卖出了，即dp[j-1]
    //     // 2. 在第j天卖出了，这种情况的最大利润为 第j天的卖出价格 - 前面天数中的最低买入价格
    //     // 递推公式：dp[j] = max{dp[j-1], prices[j] - preMin}，其中preMin为第j天之前的最小价格
    //
    //     // 初始化，由于买入和卖出不能是同一天，因此卖出只能从第2天开始，故dp[0]=0
    //
    //     int preMin = prices[0]; // 记录第j天之前的最小价格，初始为第0天的价格
    //     // 遍历顺序：从前往后
    //     for (int j = 1; j < n; j++) {
    //         dp[j] = Math.max(dp[j-1], prices[j] - preMin);
    //         preMin = Math.min(preMin, prices[j]);   // 更新preMin
    //     }
    //
    //     return dp[n-1];
    // }



    /**
     * 优化空间复杂度：每个dp[j]实际上只会用一次，因此只用一个变量preMaxProfit记录即可
     * @param prices
     * @return
     */
    // 时间复杂度：O(n)，n为总共天数，即prices数组的长度
    // 空间复杂度：O(1)
    public int maxProfit(int[] prices) {
        int preMaxProfit = 0;   // 记录第j天之前卖出的最大利润
        int preMin = prices[0]; // 记录第j天之前的最小价格，初始为第0天的价格
        // 遍历顺序：从前往后
        for (int j = 1; j < prices.length; j++) {   // 第0天不能卖出，因此j从1开始
            if (prices[j] > preMin) { // 只有今天的价格比之前的最低价高的时候才考虑今天卖出，否则一定不赚钱
                preMaxProfit = Math.max(preMaxProfit, prices[j] - preMin);
            } else {    // 今天的价格更低，需要更新preMin
                preMin = prices[j];
            }
        }
        return preMaxProfit;
    }
}
