package coinChange;

import java.util.Arrays;

/**
 * No.322 零钱兑换
 *
 * 给你一个整数数组 coins ，表示不同面额的硬币；以及一个整数 amount ，表示总金额。
 * 计算并返回可以凑成总金额所需的 最少的硬币个数 。如果没有任何一种硬币组合能组成总金额，返回 -1 。
 * 你可以认为每种硬币的数量是无限的。
 */

public class Solution {


    /**
     * 直接用回溯法自顶向下深搜遍历所有情况找到最少硬币数会超时，因为含有很多重复的计算
     * 为了避免重复的计算，我们将每个子问题的答案存在一个数组中进行记忆化
     * 即使用记忆化搜索优化，将计算出的状态（凑出某个金额的最少硬币数）用一个数组来保存其值，在进行重复的计算的时候，就可以直接调用数组中的值，减少了不必要的递归。
     *
     */
    // 时间复杂度：O(nc)，其中n为coins数组长度，即面额数，c为amount金额数。分析：一共需要计算c个状态的答案，且每个状态的答案memo[c]由于上面的记忆化的措施只计算了一次，
    // 而计算一个状态的答案需要枚举n个面额值，所以一共需要 O(nc) 的时间复杂度。
    // 空间复杂度：O(c)，其中c为amount金额数。分析：递归深度为O(c)，记忆数组的空间复杂度也为O(c)
    // int[] memo; // 记忆数组
    // public int coinChange(int[] coins, int amount) {
    //     memo = new int[amount + 1]; // memo[i]表示凑出金额i所需的最少硬币数，凑不出来就为-1
    //     return findWay(coins, amount);
    // }
    //
    // /**
    //  * 返回中coins中选择凑成金额amount的最少硬币数
    //  * @param coins
    //  * @param amount
    //  * @return
    //  */
    // public int findWay(int[] coins, int amount) {
    //     if (amount < 0) {   // 这种凑法凑不出来，走到头了
    //         return -1;
    //     }
    //
    //     if (amount == 0) {
    //         return 0;   // 这一步其实是结束步，回溯到上一层就是刚好凑够的情况
    //     }
    //
    //     // 先看记忆数组中是否已经计算过了凑amount金额的最少硬币数
    //     if (memo[amount] != 0) {    // 如果该位置已经不是初始值0了，代表该金额之前已经被计算过并保存到了记忆数组
    //         return memo[amount];    // 直接返回记忆数组中保存的结果
    //     }
    //
    //     // 如果凑amount金额的之前没有计算过，则开始向下递归
    //     int min = Integer.MAX_VALUE;
    //     for (int coin : coins) {    // 尝试在这里放入每一种硬币
    //         int res = findWay(coins, amount - coin);    // 下面只需要凑 amount - coin
    //         if (res >= 0) { // 凑的出amount的情况
    //             min = Math.min(min, res + 1);   // res是凑 amount - coin需要的最少硬币数，加上本轮放入的coin的1才是凑amount的最少硬币数
    //         }
    //         // 对于凑不出amount的情况（返回-1）则跳过
    //     }
    //
    //     // 无论凑不凑得出amount，都在记忆数组中保存结果，凑不出就保存-1
    //     // 为什么min == Integer.MAX_VALUE代表凑不出呢？
    //     // ——因为上面for循环尝试了放入每一种硬币，只要有一种硬币向下递归证明凑的出来，min就会被设为考虑了该种情况的最少硬币数，就一定比MAX_VALUE小
    //     // 只有当所有尝试都凑不出，才代表这个amount真的凑不出，min也就不会被改写，仍为MAX_VALUE
    //     memo[amount] = (min == Integer.MAX_VALUE ? -1 : min);
    //     return memo[amount];
    // }






    /**
     * 完全背包问题 + 二维dp
     * 空间复杂度高，初始化比较麻烦，建议写下面的滚动数组优化版本
     * @param coins
     * @param amount
     * @return
     */
    // public int coinChange(int[] coins, int amount) {
    //     int max = amount + 1;   // 币值最小是1，因此凑amount最多需要amount个币，将最大值就设为amount+1，用作初始化
    //     int len = coins.length;
    //
    //     // dp[i][j]：表示从下标0~i中挑选硬币能凑成总金额j的最少硬币数
    //     int[][] dp = new int[len][amount + 1];
    //
    //     // 递推公式：dp[i][j] = min{dp[i-1][j], dp[i][j-coins[i]] + 1}
    //     // 如何理解：
    //     // 1.前面一项 dp[i-1][j]：不加入第i个面值的硬币，只用前面0~i-1下标面值的硬币就能凑出金额j的最少硬币数
    //     // 2.后面一项 dp[i][j-coins[i]] + 1：最后一个硬币一定是第i个面值的，即一定要加入第i个面值的硬币凑成金额j，那么前面还需要凑出金额j-coins[i]，
    //     // 这样加上最后一个coins[i]硬币刚好凑到j，既然一定要有coins[i]，那么前面凑j-coins[i]时也可以用coins[i]，因为每种硬币是无限的，所以是在0~i中凑j-coins[i]，
    //     // 最后加的1是算是最后一个硬币coins[i]。
    //
    //     // 初始化，计算dp[i][j]需要用到dp[i-1][j]，因此第0行需要先初始化
    //     // 先全部初始化为max，防止计算dp时被覆盖
    //     Arrays.fill(dp[0], max);
    //     dp[0][0] = 0;   // 凑总金额0最少只需要0枚硬币
    //     for (int j = coins[0]; j <= amount ; j++) {
    //         if (j % coins[0] == 0) {    // 如果j是coins[0]的整数倍
    //             dp[0][j] = j / coins[0];
    //         }
    //     }
    //
    //     // 遍历顺序，都可以，但是j得顺序遍历
    //     for (int i = 1; i < len; i++) { // 先物品
    //         for (int j = 0; j <= amount; j++) { // 再背包
    //             dp[i][j] = dp[i-1][j];  // 起码有dp[i-1][j]个硬币（可能是无效max值，不影响，因为在最后返回的时候会判断）
    //             if(j >= coins[i]) {
    //                 // 这里即使遇到dp[i][j-coins[i]]是大于等于max的无效值也可以，因为dp[i-1][j]有效就会被选，如果也无效就两个无效里面选个无效没影响，都代表找不到拼凑方案
    //                 dp[i][j] = Math.min(dp[i-1][j], dp[i][j-coins[i]] + 1);
    //             }
    //         }
    //     }
    //
    //     return dp[len-1][amount] >= max ? -1 : dp[len-1][amount];
    // }



    /**
     * 完全背包问题 + 一维滚动数组优化
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange(int[] coins, int amount) {
        int max = Integer.MAX_VALUE;
        int len = coins.length;
        // dp[j]：表示挑选硬币能凑成总金额j的最少硬币数
        int[] dp = new int[amount + 1];

        // 递推公式：dp[j] = min{dp[j], dp[j-coins[i]] + 1}

        // 初始化
        Arrays.fill(dp, max); // 先全部初始化为最大值，以免下面计算dp时被初始值覆盖。也可以理解为max代表没有对应的拼凑方案，是无效的。
        dp[0] = 0;  // 凑总金额0最少只需要0枚硬币，如果不初始化 dp[0] = 0后面递推就是错的


        // 遍历顺序：本题求钱币最小个数，那么钱币有顺序和没有顺序都可以，都不影响钱币的最小个数。所以本题并不强调集合是组合还是排列。
        // 所以本题 求组合数的外层for循环遍历物品，内层for遍历背包 或者 求排列数的外层for遍历背包，内层for循环遍历物品 都可以
        // 这里采用先遍历物品，再遍历背包
        for (int i = 0; i < len; i++) {
            for (int j = coins[i]; j <= amount; j++) {
                // 至少一定可以维持上一轮的dp[j]，另外如果 dp[j-coins[i]] 有效（即不是MAX，是真正能够凑出 j-coins[i] 金额的），再取较小值
                // 只有dp[j-coins[i]]不是初始最大值时，第二项才有考虑的必要
                if (dp[j - coins[i]] != max) {
                    dp[j] = Math.min(dp[j], dp[j - coins[i]] + 1);
                }
            }
        }

        return dp[amount] == max ? -1 : dp[amount];
    }
}
