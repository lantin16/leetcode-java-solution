package spiralOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * No.54 螺旋矩阵
 * 给你一个 m 行 n 列的矩阵 matrix ，请按照 顺时针螺旋顺序 ，返回矩阵中的所有元素。
 */

public class Solution {

    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        int m = matrix.length, n = matrix[0].length;
        // 1. 向哪走？
        int direction = 0;  // 本次遍历的方向（0,1,2,3分别代表向右，下，左，上的方向遍历）

        // 2. 走多远？
        int left = 0, right = n - 1;    // 还能走的左右边界列对应的下标
        int top = 0, bottom = m - 1;    // 还能走的上下边界行对应的下标

        // 3. 何时停？
        while (left <= right && top <= bottom) {
            switch (direction) {
                case 0 :    // 向右走
                    for (int j = left; j <= right; j++) {
                        res.add(matrix[top][j]);
                    }
                    top++;  // 上面一行遍历完毕
                    break;
                case 1 :    // 向下走
                    for (int i = top; i <= bottom; i++) {
                        res.add(matrix[i][right]);
                    }
                    right--;  // 右边一列遍历完毕
                    break;
                case 2 :    // 向左走
                    for (int j = right; j >= left; j--) {
                        res.add(matrix[bottom][j]);
                    }
                    bottom--;  // 下面一行遍历完毕
                    break;
                case 3 :    // 向上走
                    for (int i = bottom; i >= top; i--) {
                        res.add(matrix[i][left]);
                    }
                    left++;  // 左边一列遍历完毕
                    break;
            }
            direction = (direction + 1) % 4;    // 更新方向
        }

        return res;
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}
        };
        List<Integer> list = spiralOrder(matrix);
        System.out.println(list);
    }
}

