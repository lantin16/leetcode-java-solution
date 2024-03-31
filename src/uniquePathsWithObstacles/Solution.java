package uniquePathsWithObstacles;

/**
 * No.63 不同路径Ⅱ
 *
 * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish”）。
 * 现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？
 * 网格中的障碍物和空位置分别用 1 和 0 来表示。
 */

public class Solution {

    /**
     * 动态规划
     * 遇到障碍物的格子应该保持初始状态0
     */
    // 时间复杂度：O(mn)，其中m，n分别为obstacleGrid 长度和宽度
    // 空间复杂度：O(mn)
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        // 1. dp[i][j]表示从(0,0)出发到达(i,j)格不同路径数
        int[][] dp = new int[m][n];

        // 2. 递推公式：dp[i][j] = dp[i-1][j] + dp[i][j-1]

        // 3. 初始化：将第一行、第一列的前部的非障碍格子的dp元素设为1，障碍格子及之后的都设为0（因为一旦被障碍拦住则后面的格子无法到达）
        // for (int i = 0; i < m; i++) {
        //     if (obstacleGrid[i][0] == 0) {
        //         dp[i][0] = 1;
        //     } else {
        //         break;
        //     }
        // }
        // for (int j = 0; j < n; j++) {
        //     if (obstacleGrid[0][j] == 0) {
        //         dp[0][j] = 1;
        //     } else {
        //         break;
        //     }
        // }

        // 这里初始化应该这么写最简洁，一旦遇到障碍物直接跳出循环
        for (int i = 0; i < m && obstacleGrid[i][0] == 0; i++) {
            dp[i][0] = 1;
        }
        for (int j = 0; j < n && obstacleGrid[0][j] == 0; j++) {
            dp[0][j] = 1;
        }

        // 4. 遍历顺序：第二行第二列开始，从左往右、从上到下逐层遍历
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 0) {
                    dp[i][j] = dp[i-1][j] + dp[i][j-1];
                }
                // 如果当前是障碍物，则dp元素保持为0
            }
        }
        return dp[m-1][n-1];
    }
}
