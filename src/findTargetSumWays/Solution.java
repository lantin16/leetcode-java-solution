package findTargetSumWays;

/**
 * No.494 目标和
 *
 * 给你一个非负整数数组 nums 和一个整数 target 。
 * 向数组中的每个整数前添加 '+' 或 '-' ，然后串联起所有整数，可以构造一个 表达式 ：
 * 例如，nums = [2, 1] ，可以在 2 之前添加 '+' ，在 1 之前添加 '-' ，然后串联起来得到表达式 "+2-1" 。
 * 返回可以通过上述方法构造的、运算结果等于 target 的不同 表达式 的数目。
 */


public class Solution {


    /**
     * 动态规划 + 二维dp数组
     *
     * 思路：
     * 加+-号实质上就是将所有数分成两组，一组是要加起来的数，一组是要减去的数。所以问题转化为将非负整数组nums分成两组，使 加数组的和 - 减数组的和 = target 有多少种分法
     * 设加数组的和为addSum，减数组的和为subSum，所有数的和为sum，有如下关系：
     * addSum + subSum = sum, addSum - subSum = target
     * 其中sum和target都是已知的，联立上面两式可以得出 addSum = (sum + target) / 2，问题进一步转化为从nums数组中划分一组数，它们的和为addSum，总共有几种分法
     * 这就类似于分割等和子集，可以用动态规划来解决
     *
     * 由于nums中都是非负整数，因此addSum也应该为非负整数，如根据公式算出来不是，则代表这种情况符合条件的分法为0种
     */
    // // 时间复杂度：O(nc)，其中n为nums的长度，c为addSum的值
    // // 空间复杂度：O(nc)，其中n为nums的长度，c为addSum的值
    // public int findTargetSumWays(int[] nums, int target) {
    //     int sum = 0;
    //     for (int num : nums) {
    //         sum += num;
    //     }
    //
    //     if ((sum + target) < 0 || (sum + target) % 2 != 0) {  // 如果sum+target是奇数，而nums中都是非负整数，故没有符合条件的答案
    //         return 0;
    //     }
    //
    //     int addSum = (sum + target) / 2;    // 加数和
    //
    //     // 问题转化为：在nums数组中找到一组和为addSum的数，有几种不同的取法（类似于分割等和子集）
    //     int len = nums.length;
    //
    //     // 1. dp[i][j]：从nums数组下标0~i的数中挑选和为j的一组数，共有dp[i][j]种取法
    //     int[][] dp = new int[len][addSum + 1];
    //
    //     // 2. 递推公式：dp[i][j] = dp[i-1][j] + dp[i-1][j-nums[i]]，前面一项代表不选nums[i]的分法数，后面一项代表选择nums[i]的分法数
    //     // 计算每一行需要上一行的数据，因此第0行需要先初始化
    //
    //     // 3. 初始化：第0行
    //     dp[0][0] = 1;   // j=0一定至少有一种取法，即不取nums[0]
    //     if (nums[0] <= addSum) {    // 如果要取nums[0]，则dp[0][nums[0]]还会多1
    //         dp[0][nums[0]]++;
    //     }
    //
    //     // 4. 遍历顺序，先遍历数组元素，再遍历和
    //     for (int i = 1; i < len; i++) {
    //         for (int j = 0; j <= addSum; j++) {
    //             dp[i][j] = dp[i-1][j];
    //             if (j - nums[i] >= 0) { // 保证后面一项不越界，若和j-nums[i]为一个负数也是不可能的
    //                 dp[i][j] += dp[i-1][j-nums[i]];
    //             }
    //         }
    //     }
    //     return dp[len-1][addSum];
    // }





    /**
     * 动态规划 + 一维滚动数组，优化空间复杂度
     */
    // 时间复杂度：O(nc)，其中n为nums的长度，c为addSum的值
    // 空间复杂度：O(c)，其中c为addSum的值
    public int findTargetSumWays(int[] nums, int target) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        if ((sum + target) < 0 || (sum + target) % 2 != 0) {  // 如果sum+target是奇数，而nums中都是非负整数，故没有符合条件的答案
            return 0;
        }

        int addSum = (sum + target) / 2;    // 加数和

        // 问题转化为：在nums数组中找到一组和为addSum的数，有几种不同的取法（类似于分割等和子集，即01背包问题）
        int len = nums.length;

        // 1. dp[j]：从nums数组中挑选和为j的一组数，共有dp[j]种取法
        int[] dp = new int[addSum + 1];

        // 2. 递推公式：dp[j] = dp[j] + dp[j-nums[i]]

        // 3. 初始化：第0行
        dp[0] = 1;   // j=0一定至少有一种取法，即不取nums[0]
        if (nums[0] <= addSum) {    // 如果要取nums[0]，则dp[nums[0]]还会多1
            dp[nums[0]]++;
        }

        // 4. 遍历顺序，先遍历数组元素，再遍历和
        for (int i = 1; i < len; i++) {
            for (int j = addSum; j >= nums[i]; j--) { // 实际上求dp[j]得用到上一行j及j左边的数据，因此j必须倒序遍历才不会覆盖
                dp[j] += dp[j-nums[i]];
            }
        }
        return dp[addSum];
    }
}
