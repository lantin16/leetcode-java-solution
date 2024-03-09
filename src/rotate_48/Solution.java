package rotate_48;

/**
 * No.48 旋转图像
 * 给定一个 n × n 的二维矩阵 matrix 表示一个图像。请你将图像顺时针旋转 90 度。
 *
 * 你必须在 原地 旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要 使用另一个矩阵来旋转图像。
 */

public class Solution {

    // 方法二：先上下水平翻转，再对角线翻转

    // 方法一：由外向内逐层旋转
    public static void rotate(int[][] matrix) {
        int n = matrix.length;

        // 需要旋转的外圈的个数(轮数)：n / 2
        for (int it = 0; it < n / 2; it++) {
            int edge = n - it * 2;  // 当前需要旋转的外圈的边长（可知边长最小为2）

            for (int i = 0; i < edge - 1; i++) {    // 注意：这里只能取到edge-2，四个角的数在i=0时已经转完了，如果取到i=edge-1则四个角又会转90°，答案错误
                int l, r, t, b; // 记录一圈中旋转后位置对应的四个数
                t = matrix[it][it+i];
                r = matrix[it+i][n-1-it];
                b = matrix[n-1-it][n-1-it-i];
                l = matrix[n-1-it-i][it];

                // 设置四个对应位置旋转90°后的值
                matrix[it][it+i] = l;
                matrix[it+i][n-1-it] = t;
                matrix[n-1-it][n-1-it-i] = r;
                matrix[n-1-it-i][it] = b;
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {{5,1,9,11},{2,4,8,10},{13,3,6,7},{15,14,12,16}};
        rotate(matrix);
    }
}
