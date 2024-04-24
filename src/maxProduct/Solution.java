package maxProduct;

/**
 * No.152 乘积最大子数组
 *
 * 给你一个整数数组 nums ，请你找出数组中乘积最大的非空连续子数组
 * （该子数组中至少包含一个数字），并返回该子数组所对应的乘积。
 *
 * 测试用例的答案是一个 32-位 整数。
 */

public class Solution {

    /**
     * 动态规划 + 二维dp（掌握）
     *
     * 本题不能像求最大和那样简单地用max来选择是否将当前元素加入到前面地非空连续子数组中
     *
     * 由于负数的存在，前面可能有一个很大的正数乘积但由于加入当前负数元素后变成了很小的复制乘积（变号）导致被max抛弃
     * 举例：2，3，-1，-2
     * 前两个2，3相乘为6，但考虑第3个元素nums[2] = -1时，如果按照类似求最大和的子数组那样dp[2] = max{dp[1] * nums[2], nums[2]} = max{-6,-1} = -1
     * 会由于前面的正数乘积由于乘上-1后变成更小的负数而被max抛弃最终留下dp[2] = -1，
     * 但是到dp[3] = max{-1 * -2, -2} = 2显然就不对了，因为本来保留下-6再经过与-2相乘变号又可以翻身做最大的正数乘积，这样的话才是正确的。
     *
     * 所以负数的存在使得不能仅仅用max来选择才由哪种状态，而是要把绝对值最大的正负乘积都记录下来（负乘积也可能由于后面加入负数翻身变成更大的正乘积）
     * 当前元素为负数时就可以考虑跟前面的负乘积相乘变成可能的更大乘积，当前元素为正数时就可以考虑跟前面的正乘积相乘变成可能的更大乘积
     *
     * @param nums
     * @return
     */
    // // 时间复杂度：O(n)，其中n为nums的长度
    // // 空间复杂度：O(n)，其中n为nums的长度
    // public int maxProduct(int[] nums) {
    //     int n = nums.length;
    //     if (n == 1) {
    //         return nums[0];
    //     }
    //
    //     // dp[j][0]：以nums[j]结尾的非空连续子数组的绝对值最大的负乘积，如果没有负乘积则为0
    //     // dp[j][1]：以nums[j]结尾的非空连续子数组的绝对值最大的正乘积，如果没有正乘积则为0
    //     int[][] dp = new int[n][2];
    //
    //     // 递推公式
    //     // 如果nums[j] > 0，则dp[j][1] = max{dp[j-1][1] * nums[j], nums[j]}，dp[j][0] = dp[j-1][0] * nums[j]
    //     // 如果nums[j] = 0，则dp[j][1] = 0, dp[j][0] = 0
    //     // 如果nums[j] < 0，则dp[j][1] = dp[j-1][0] * nums[j]，dp[j][0] = min{dp[j-1][1] * nums[j], nums[j]}
    //
    //     // 初始化dp[0]
    //     if (nums[0] > 0) {
    //         dp[0][1] = nums[0];
    //     } else if (nums[0] < 0) {
    //         dp[0][0] = nums[0];
    //     }
    //
    //     // 为什么max只收集最大正乘积，最大乘积不可能为负数吗？
    //     // ——最大乘积为负数只可能出现在nums只有一个元素且为负数的情况下（该情况已在前面n==1的时候返回了）
    //     // 若nums中存在非负数（0或正数），则最大乘积必然非负（至少可以选择单一非负元素作为子数组，乘积至少为非负元素本身）
    //     // 若nums中只有负数且负数至少有2个，则最大乘积必然为正数（至少可以选择连续的两个负数作为子数组，乘积为正数）
    //     int max = dp[0][1];
    //
    //     // 遍历顺序：正序遍历
    //     // 注意nums的数只要不是0就是绝对值大于等于1的整数，因此乘非零的nums[j]绝对值一定变大
    //     for (int j = 1; j < n; j++) {
    //         if (nums[j] > 0) {  // 如果当前元素为正数
    //             dp[j][1] = Math.max(dp[j-1][1] * nums[j], nums[j]); // 可能加入前面的正数乘积更大，但如果dp[j-1][1]为0则应该选nums[j]本身这个正数而不是加入前面
    //             dp[j][0] = dp[j-1][0] * nums[j];    // 一个大于等于1的正整数nums[j]乘上前面的绝对值最大的负数乘积dp[j-1][0]将变成绝对值更大的负数乘积
    //         } else if (nums[j] < 0) {   // 如果当前元素为负数
    //             dp[j][1] = dp[j-1][0] * nums[j];    // 一个小于等于-1的负整数nums[j]乘上前面的绝对值最大的负数乘积dp[j-1][0]将变成绝对值更大的正数乘积（变号）
    //             dp[j][0] = Math.min(dp[j-1][1] * nums[j], nums[j]); // 负数nums[j]可能乘上前面的最大正数乘积能够变成比dp[j-1][1]绝对值更大的负乘积，但如果dp[j-1][1]为0则应该选nums[j]本身这个负数而不是加入前面变成0
    //         }
    //         // 如果当前元素为0，则dp[j][0]=0,dp[j][1]=0，因为以它结尾的子数组乘积肯定都变成了0，所以保持默认值0即可
    //         max = Math.max(max, dp[j][1]);  // 收集最大正乘积维护max
    //     }
    //
    //     return max;
    // }


    /**
     * 动态规划 + 滚动变量
     * 优化空间复杂度
     * @param nums
     * @return
     */
    // 时间复杂度：O(n)，其中n为nums的长度
    // 空间复杂度：O(1)
    public int maxProduct(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }

        int preMaxPos = 0, preMinNeg = 0;   // 记录以前一个元素结尾的非空连续子数组的最大正数乘积和最小负数乘积

        if (nums[0] > 0) {
            preMaxPos = nums[0];
        } else if (nums[0] < 0) {
            preMinNeg = nums[0];
        }

        int max = preMaxPos;

        // 遍历顺序：正序遍历
        for (int j = 1; j < n; j++) {
            int curMaxPos = 0, curMinNeg = 0;
            if (nums[j] > 0) {  // 如果当前元素为正数
                curMaxPos = Math.max(preMaxPos * nums[j], nums[j]); // 可能加入前面的正数乘积更大，但如果dp[j-1][1]为0则应该选nums[j]本身这个正数而不是加入前面
                curMinNeg = preMinNeg * nums[j];    // 一个大于等于1的正整数nums[j]乘上前面的绝对值最大的负数乘积dp[j-1][0]将变成绝对值更大的负数乘积
            } else if (nums[j] < 0) {   // 如果当前元素为负数
                curMaxPos = preMinNeg * nums[j];    // 一个小于等于-1的负整数nums[j]乘上前面的绝对值最大的负数乘积dp[j-1][0]将变成绝对值更大的正数乘积（变号）
                curMinNeg = Math.min(preMaxPos * nums[j], nums[j]); // 负数nums[j]可能乘上前面的最大正数乘积能够变成比dp[j-1][1]绝对值更大的负乘积，但如果dp[j-1][1]为0则应该选nums[j]本身这个负数而不是加入前面变成0
            }

            max = Math.max(max, curMaxPos);  // 收集最大正乘积维护max

            // 更新pre
            preMaxPos = curMaxPos;
            preMinNeg = curMinNeg;
        }

        return max;
    }
}
