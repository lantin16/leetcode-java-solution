package searchMatrix_74;

/**
 * No.74 搜索二维矩阵
 *
 * 给你一个满足下述两条属性的 m x n 整数矩阵：
 *
 * 每行中的整数从左到右按非严格递增顺序排列。
 * 每行的第一个整数大于前一行的最后一个整数。
 * 给你一个整数 target ，如果 target 在矩阵中，返回 true ；否则，返回 false 。
 */

public class Solution {

    // 二分查找，将矩阵元素从左到右、从上到下连起来是一个非递减序列
    // 重点：矩阵元素的坐标(i,j)与其在非递减一维数组中的下标的对应关系：(i,j) <-> id = n * i + j，其中n为矩阵的列数，并且是一一对应的
    // 要想从一维数组下标中解析出矩阵二维坐标：row = id / n， col = id % n
    // 时间复杂度：O(log n)
    // 空间复杂度：O(1)
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length;
        int start = 0, end = n * (m-1) + n-1;   // 左上角元素坐标(0,0)，右下角元素坐标(m-1,n-1)

        while (start <= end) {
            int mid = start + (end - start) / 2;
            // 解析mid为行列坐标
            int r = mid / n;
            int c = mid % n;
            if (matrix[r][c] == target) {
                return true;
            } else if (matrix[r][c] > target) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return false;
    }

}
