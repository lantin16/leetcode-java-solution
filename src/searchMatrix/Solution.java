package searchMatrix;

/**
 * No.240 搜索二维矩阵Ⅱ
 * 编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target 。该矩阵具有以下特性：
 *
 * 每行的元素从左到右升序排列。
 * 每列的元素从上到下升序排列。
 */

public class Solution {
    // 蛇形走位搜索法
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length;

        int i = 0, j = 0;
        while (i < m) {
            // 找到target
            if (matrix[i][j] == target) {
                return true;
            }

            // 当前元素小于target
            if (matrix[i][j] < target) {
                while (j < n - 1 && matrix[i][j] < target) {
                    j++;    // 只有当到达最后一列或者当前元素不再小于target才停止在该行向右搜索
                }
                if (matrix[i][j] == target) {
                    return true;
                } else {
                    i++;    // 换到下一行接着搜索
                    continue;
                }
            }

            // 当前元素大于target
            if (matrix[i][j] > target) {
                while (j > 0 && matrix[i][j] > target) {
                    j--;    // 只有当到达第一列或者当前元素不再大于target才停止在该行向左搜索
                }
                if (matrix[i][j] == target) {
                    return true;
                } else {
                    i++;    // 换到下一行接着搜索
                    continue;
                }
            }
        }

        return false;
    }
}
