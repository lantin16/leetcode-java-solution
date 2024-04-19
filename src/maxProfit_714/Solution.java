package maxProfit_714;

/**
 * No.714 买卖股票的最佳时机含手续费
 *
 * 给定一个整数数组 prices，其中 prices[i]表示第 i 天的股票价格 ；整数 fee 代表了交易股票的手续费用。
 * 你可以无限次地完成交易，但是你每笔交易都需要付手续费。如果你已经购买了一个股票，在卖出它之前你就不能再继续购买股票了。
 * 返回获得利润的最大值。
 * 注意：这里的一笔交易指买入持有并卖出股票的整个过程，每笔交易你只需要为支付一次手续费。
 *
 * 本题特点：
 * 1. 买卖次数不限制，可以尽可能多次交易，但想再次购买前必须出售掉之前购买的股票
 * 2. 每次交易需要支付一次手续费
 * 与No.122类似，加入了手续费的限制，如果交易利润连手续费都不够则是不划算的
 */

public class Solution {


    /**
     * 动态规划 + 二维dp
     *
     * 统一在买入的时候交手续费，卖出的时候就不用交了
     *
     * @param prices
     * @param fee
     * @return
     */
    // // 时间复杂度：O(n)，其中n为prices数组的长度
    // // 空间复杂度：O(n)，其中n为prices数组的长度。分析：dp数组总大小为2n
    // public int maxProfit(int[] prices, int fee) {
    //     int n = prices.length;
    //
    //     // dp[i][0]：第i天结束时持有股票的情况下手里最多的现金
    //     // dp[i][1]：第i天结束时不持有股票的情况下手里最多的现金
    //     int[][] dp = new int[n][2];
    //
    //     // 递推公式
    //     // dp[i][0] = max{dp[i-1][0], dp[i-1][1] - prices[i] - fee}，买的时候要交钱
    //     // dp[i][1] = max{dp[i-1][1], dp[i-1][0] + prices[i]}，卖的时候不交钱
    //
    //     // 初始化
    //     dp[0][0] = -prices[0] - fee;
    //     dp[0][1] = 0;
    //
    //     // 遍历顺序：从前往后
    //     for (int i = 1; i < n; i++) {
    //         dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] - prices[i] - fee);
    //         dp[i][1] = Math.max(dp[i-1][1], dp[i-1][0] + prices[i]);
    //     }
    //
    //     return dp[n-1][1];
    // }


    /**
     * 动态规划 + 滚动变量
     * 优化空间复杂度
     * @param prices
     * @param fee
     * @return
     */
    // 时间复杂度：O(n)，其中n为prices数组的长度
    // 空间复杂度：O(1)
    public int maxProfit(int[] prices, int fee) {
        int n = prices.length;

        // dp[0]：某天结束时持有股票的情况下手里最多的现金
        // dp[1]：某天结束时不持有股票的情况下手里最多的现金
        int[] dp = new int[2];
        int[] pre = new int[2];

        // 递推公式
        // dp[0] = max{pre[0], pre[1] - prices[i] - fee}，买的时候要交钱
        // dp[1] = max{pre[1], pre[0] + prices[i]}，卖的时候不交钱

        // 初始化
        pre[0] = -prices[0] - fee;
        pre[1] = 0;

        // 遍历顺序：从前往后
        for (int i = 1; i < n; i++) {
            dp[0] = Math.max(pre[0], pre[1] - prices[i] - fee);
            dp[1] = Math.max(pre[1], pre[0] + prices[i]);

            pre[0] = dp[0];
            pre[1] = dp[1];
        }

        // 这里返回dp[1]答案也是对的，因为当n=1时不交易是最大利润0，此时dp[1]是默认值0，恰好等于结果。不过pre[1]才是意义上最正确的，因为它是从n=1开始考虑的
        // 股票问题其他题最后返回dp[1]正确也是因为默认值0恰好等于n=1情况最后不持有的最大利润。但是在No.309中直接返回dp[0]就会在n=2时报错，所以那题只能返回pre1[1]
        return pre[1];
    }
}
