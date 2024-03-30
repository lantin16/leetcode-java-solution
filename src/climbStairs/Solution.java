package climbStairs;

/**
 * No.70 爬楼梯
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 *
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 */

public class Solution {

    /**
     * 动态规划
     * @param n
     * @return
     */
    // // 时间复杂度：O(n)
    // // 空间复杂度：O(n)
    // public int climbStairs(int n) {
    //     // 1. dp数组及下标的含义：dp[i]表示到第i级阶梯有dp[i]种不同的方法
    //     int[] dp = new int[n + 1];
    //     // 2. 递推公式：dp[i] = dp[i - 2] + dp[i - 1]
    //     // 3. 如何初始化：将到第0级阶梯的方法设为1种，到第1级的设为1种
    //     dp[0] = 1;
    //     dp[1] = 1;
    //
    //     // 4. 遍历顺序：从前往后
    //     for (int i = 2; i <= n; i++) {
    //         dp[i] = dp[i - 2] + dp[i - 1];
    //     }
    //
    //     return dp[n];
    // }


    /**
     * 动态规划，优化空间复杂度，只用两个变量记录前两个级状态即可
     * @param n
     * @return
     */
    // 时间复杂度：O(n)
    // 空间复杂度：O(1)
    public int climbStairs(int n) {
        // 1. dp数组及下标的含义：dp[i]表示到第i级阶梯有dp[i]种不同的方法
        int[] dp = new int[2];
        // 2. 递推公式：dp[i] = dp[i - 2] + dp[i - 1]
        // 3. 如何初始化：将到第0级阶梯的方法设为1种，到第1级的设为1种
        dp[0] = 1;
        dp[1] = 1;

        // 4. 遍历顺序：从前往后
        for (int i = 2; i <= n; i++) {
            int cur = dp[0] + dp[1];
            dp[0] = dp[1];
            dp[1] = cur;
        }

        return dp[1];
    }
}
