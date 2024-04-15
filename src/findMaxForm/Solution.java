package findMaxForm;

/**
 * No.474 一和零
 *
 * 给你一个二进制字符串数组 strs 和两个整数 m 和 n 。
 * 请你找出并返回 strs 的最大子集的长度，该子集中 最多 有 m 个 0 和 n 个 1 。
 * 如果 x 的所有元素也是 y 的元素，集合 x 是集合 y 的 子集 。
 */

public class Solution {


    /**
     * 动态规划 + 三维dp数组，01背包问题
     * 这里的“背包容量”有两个限制条件：0的个数和1的个数，因此原先用dp[i][j]的j作为背包容量，这里需要dp[i][j][k]的j和k同时限制，因此dp数组的维数又增加了
     */
    // // 时间复杂度：O(lmn)，中l为strs数组的长度
    // // 时间复杂度：O(lmn)，中l为strs数组的长度
    // public int findMaxForm(String[] strs, int m, int n) {
    //     int len = strs.length;
    //     // 1. dp[i][j][k]：从strs下标0~i中选择满足最多j个0和k个1的最大子集长度
    //     int[][][] dp = new int[len][m + 1][n + 1];
    //
    //     // 2. 递推公式：dp[i][j][k] = max{dp[i-1][j][k], dp[i-1][j-strs[i].n0][k-strs[i].n1] + 1}
    //
    //     // 3. 初始化
    //     int[] cntStr0 = countNum(strs[0]);
    //     for (int j = cntStr0[0]; j <= m; j++) {
    //         for (int k = cntStr0[1]; k <= n; k++) {
    //             dp[0][j][k] = 1;
    //         }
    //     }
    //
    //
    //     // 4. 遍历顺序
    //     for (int i = 1; i < len; i++) {
    //         int[] cntStrI = countNum(strs[i]);
    //         for (int j = 0; j <= m; j++) {
    //             for (int k = 0; k <= n; k++) {
    //                 dp[i][j][k] = dp[i-1][j][k];    // 最大子集长度至少有这么大（不要strs[i]）
    //                 if (j - cntStrI[0] >= 0 && k - cntStrI[1] >= 0 && dp[i-1][j - cntStrI[0]][k - cntStrI[1]] + 1 > dp[i-1][j][k]) {
    //                     dp[i][j][k] = dp[i-1][j - cntStrI[0]][k - cntStrI[1]] + 1;
    //                 }
    //             }
    //         }
    //     }
    //
    //     return dp[len-1][m][n];
    //
    // }


    /**
     * 统计str中的0，1个数
     * @param str
     * @return cnt[0]为0的个数，cnt[1]为1的个数
     */
    public int[] countNum(String str) {
        int[] cnt = new int[2];
        for (char c : str.toCharArray()) {
            cnt[c - '0']++;
        }
        return cnt;
    }


    /**
     * 动态规划 + 二维滚动数组，空间复杂度优化
     * 这里的“背包容量”有两个限制条件：0的个数和1的个数，因此原先用dp[j]的j作为背包容量，这里需要dp[j][k]的j和k同时限制，因此dp数组的维数又增加了
     */
    // 时间复杂度：O(lmn)，其中l为strs数组长度
    // 时间复杂度：O(mn)
    public int findMaxForm(String[] strs, int m, int n) {
        int len = strs.length;
        // 1. dp[j][k]：从strs中满足最多j个0和k个1的最大子集长度
        int[][] dp = new int[m + 1][n + 1];

        // 2. 递推公式：dp[j][k] = max{dp[j][k], dp[j-strs[i].n0][k-strs[i].n1] + 1}

        // 3. 初始化，全部初始化为0即可，下面的循环从0开始

        // 4. 遍历顺序
        for (int i = 0; i < len; i++) { // 先遍历物品
            int[] cntStrI = countNum(strs[i]);
            for (int j = m; j >= cntStrI[0]; j--) { // 再倒序遍历两个维度的背包容量
                for (int k = n; k >= cntStrI[1]; k--) {
                    dp[j][k] = Math.max(dp[j][k], dp[j - cntStrI[0]][k - cntStrI[1]] + 1);
                }
            }
        }

        return dp[m][n];

    }
}
