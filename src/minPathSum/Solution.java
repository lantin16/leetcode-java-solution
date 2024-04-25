package minPathSum;

import java.util.Arrays;

/**
 * No.64 最小路径和
 *
 * 给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
 * 说明：每次只能向下或者向右移动一步。
 */

public class Solution {

    /**
     * 动态规划 + 二维dp
     * @param grid
     * @return
     */
    // 时间复杂度：O(mn)，其中m，n分别为网格的长宽
    // 空间复杂度：O(mn)，其中m，n分别为网格的长宽
    public int minPathSum(int[][] grid) {
        int m = grid.length, n = grid[0].length;

        // dp[i][j]：从起点(0,0)走到(i,j)的最小路径和
        int[][] dp = new int[m][n];

        // 递推公式
        // dp[i][j] = min{dp[i-1][j], dp[i][j-1]} + grid[i][j]

        // 初始化
        dp[0][0] = grid[0][0];
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i-1][0] + grid[i][0];
        }
        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j-1] + grid[0][j];
        }

        // 遍历顺序，从上往下，从左往右
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1]) + grid[i][j];
            }
        }

        return dp[m-1][n-1];
    }

}
