package setZeroes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * leetcode No.73 矩阵置零
 * 给定一个 m x n 的矩阵，如果一个元素为 0 ，则将其所在行和列的所有元素都设为 0 。请使用 原地 算法。
 */

public class Solution {

    // 解决算法题如何减小空间复杂度？
    // ——一种思路：利用好传入或返回的结果变量，它们一般是不计入空间复杂度的，看之前需要额外空间记录的数据能够记录在已给或者返回的变量中，
    // 如果会有影响则看是否能用常数个额外空间来消除这种影响，而常数个额外空间的空间复杂度为O(1)

    // 使用matrix的第一行和第一列来代替记录哪些行和哪些列需要置零，那原本第一行和第一列是否需要置零就无法记录，因此还需要额外两个变量来记录第一行和第一列是否需要置零
    // 算下来只多用了两个额外变量，空间复杂度 O(1)
    public void setZeroes(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        // 用两个变量来记录第一行和第一列是否需要置零，比如第一行的第二列为零则代表第二列需要置零
        boolean firstZeroRow = false;
        boolean firstZeroCol = false;

        for (int j = 0; j < n; j++) {
            if (matrix[0][j] == 0) {
                firstZeroRow = true;
            }
        }
        for (int i = 0; i < m; i++) {
            if (matrix[i][0] == 0) {
                firstZeroCol = true;
            }
        }

        // 使用matrix的第一行和第一列来分别代替记录哪些列和哪些行需要置零
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 0){
                    matrix[0][j] = 0;   // matrix的第一行来记录哪些列需要置零（不包括第一列）
                    matrix[i][0] = 0;   // matrix的第一列来记录哪些行需要置零（不包括第一行）
                }
            }
        }

        // 第二次遍历来将这些行和列实际置零
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }
        // 若第一行和第一列需要置零则只能最后置零
        if (firstZeroRow) {
            for (int j = 0; j < n; j++) {
                matrix[0][j] = 0;
            }
        }
        if (firstZeroCol) {
            for (int i = 0; i < m; i++) {
                matrix[i][0] = 0;
            }
        }
    }


    // // 使用两个标记数组分别记录哪些行和列需要置零，第二遍遍历再将它们统一置零
    // // 空间复杂度 O(m+n)
    // public void setZeroes(int[][] matrix) {
    //     int m = matrix.length, n = matrix[0].length;
    //     // 用两个数组分别记录哪些行和列需要置零
    //     boolean zeroRows[] = new boolean[m];
    //     boolean zeroCols[] = new boolean[n];
    //
    //     // 第一次遍历matrix只记录哪些行和列需要置零，不实际置零
    //     for (int i = 0; i < m; i++) {
    //         for (int j =0; j < n; j++) {
    //             if (matrix[i][j] == 0){
    //                 zeroRows[i] = true;
    //                 zeroCols[j] = true;
    //             }
    //         }
    //     }
    //
    //     // 第二次遍历zeroRows和zeroCols来将这些行和列实际置零
    //     for (int i = 0; i < m; i++) {
    //         for (int j = 0; j < n; j++) {
    //             if (zeroRows[i] || zeroCols[j]) {
    //                 matrix[i][j] = 0;
    //             }
    //         }
    //     }
    // }
}
