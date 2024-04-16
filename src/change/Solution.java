package change;

import java.util.Arrays;

/**
 * No.518 零钱兑换Ⅱ
 *
 * 给你一个整数数组 coins 表示不同面额的硬币，另给一个整数 amount 表示总金额。
 * 请你计算并返回可以凑成总金额的硬币组合数。如果任何硬币组合都无法凑出总金额，返回 0 。
 * 假设每一种面额的硬币有无限个。
 * 题目数据保证结果符合 32 位带符号整数。
 */


/**
 * 在求装满背包有几种方案的时候，认清遍历顺序是非常关键的：
 * - 如果求组合数就是外层for循环遍历物品，内层for遍历背包。
 * - 如果求排列数就是外层for遍历背包，内层for循环遍历物品。
 */


public class Solution {

    /**
     * 完全背包问题
     * 组合不强调元素之间的顺序，排列强调元素之间的顺序。
     * 求组合数，总金额限制（背包容量），每种硬币的面额（重量），硬币不限个数 => 完全背包
     * @param amount 总金额：背包容量
     * @param coins 硬币面额：每种物品的重量
     * @return
     */
    // 时间复杂度：O(nc)，其中n为coins数组的长度，c为amount的大小
    // 空间复杂度：O(c)，其中c为amount的大小
    public static int change(int amount, int[] coins) {
        // 1. dp[j] 表示总金额为j时的硬币组合数
        int[] dp = new int[amount + 1];

        // 2. 递推公式：dp[j] = dp[j] + dp[j-coins[i]]

        // 3. 初始化，这里必须得初始化dp[0]=1，否则dp数组后面根据递推公式算出的都是0
        // 理解：凑成总金额0的货币组合数为1，即一个硬币也不选也是一种组合。事实上后台测试数据是默认amount = 0 的情况，组合数为1的
        dp[0] = 1;

        // 4. 遍历顺序：这里是求排列数，因此必须先遍历物品，再遍历背包
        // 搞不清楚顺序最好还是先写二维dp，再根据递推公式确认压缩成一维后的遍历顺序
        for (int i = 0; i < coins.length; i++) {
            for (int j = coins[i]; j <= amount; j++) {
                dp[j] += dp[j - coins[i]];
            }
        }

        // 下面这种先遍历背包容量，再遍历物品的写法是错的
        // 因为根据二维dp，计算的每一个dp[j]实际上是要用到当前i下左边已经计算出的dp[j-coins[i]]，
        // 但是如果先遍历背包，则当前j前面的dp[k]实际上是最后的i=len-1时计算并覆盖后的，用它们来当作当前i的dp[j-coins[i]]来计算组合数肯定不对
        // for (int j = 0; j <= amount; j++) { // 遍历背包容量
        //     for (int i = 0; i < coins.length; i++) { // 遍历物品
        //         if (j - coins[i] >= 0){
        //             dp[j] += dp[j - coins[i]];
        //         }
        //     }
        // }

        System.out.println(Arrays.toString(dp));

        return dp[amount];
    }


    public static void main(String[] args) {
        change(5, new int[]{1,2,5});
    }
}
