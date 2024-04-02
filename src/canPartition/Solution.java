package canPartition;

import java.util.Arrays;

/**
 * No.416 分割等和子集
 *
 * 给你一个 只包含正整数 的 非空 数组 nums 。请你判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
 */

public class Solution {

    /**
     * 暴力回溯：指数级时间复杂度，超时
     */


    /**
     * 动态规划
     * 只有确定了如下四点，才能把01背包问题套到本题上来。
     * 1. 背包的容量为sum / 2
     * 2. 背包要放入的商品（集合里的元素）重量为 元素的数值，价值也为元素的数值（重点）
     * 3. 背包如果正好装满，说明找到了总和为 sum / 2 的子集。
     * 4. 背包中每一个元素是不可重复放入。
     *
     * 问题就转化为：如果背包容量为 target = sum/2，物品价值与重量等值，当dp[target]就是尽量装满背包后的价值/重量，当dp[target] == target时，背包就装满了
     */
    // 时间复杂度：O(nc)，其中n为数组元素的个数，c为数组元素和的一半
    // 空间复杂度：O(c)，c为数组元素和的一半。因为采用一维滚动数组优化了空间复杂度
    public boolean canPartition(int[] nums) {
        int len = nums.length;
        int sum = 0;
        for (int x : nums) {
            sum += x;
        }

        // 如果总和不是偶数，则必不可能分成两个相等和的子集
        if (sum % 2 != 0) {
            return false;
        }

        int target = sum / 2;
        // 转换成，nums中是否可以找到一个子集，使得子集的元素和等于target

        // 1. dp[j]代表容量为j的背包，能装物品的最大价值/重量为dp[j]
        int[] dp = new int[target + 1];

        // 2. 递推公式：dp[j] = max{dp[j], dp[j-nums[i]] + nums[i]}

        // 3. 初始化：全初始化为0，下面i从0开始遍历

        // 遍历顺序，先遍历物品，再遍历背包
        for (int i = 0; i < len; i++) {
            for (int j = target; j >= nums[i]; j--) {   // j倒序遍历
                dp[j] = Math.max(dp[j], dp[j-nums[i]] + nums[i]);
            }
        }

        return dp[target] == target;    // 最后 dp[target] == target 就代表背包刚好装满
    }
}
