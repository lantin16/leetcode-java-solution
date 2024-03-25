package solveNQueens;

import java.util.*;

/**
 * No.51 N皇后
 *
 * 按照国际象棋的规则，皇后可以攻击与之处在同一行或同一列或同一斜线上的棋子。
 * n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
 * 给你一个整数 n ，返回所有不同的 n 皇后问题 的解决方案。
 * 每一种解法包含一个不同的 n 皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
 */

public class Solution {

    // List<List<String>> res = new ArrayList<>();
    // List<String> tmp = new ArrayList<>();   // 暂时记录每一行的结果
    // public List<List<String>> solveNQueens(int n) {
    //     boolean[][] chessboard = new boolean[n][n]; // nxn的棋盘
    //     backtrack(chessboard, new StringBuilder(), 0);
    //     return res;
    // }
    //
    // /**
    //  *
    //  * @param chessboard
    //  * @param sb
    //  * @param k 正在放置的皇后棋的行坐标
    //  */
    // public void backtrack(boolean[][] chessboard, StringBuilder sb, int k) {
    //     int n = chessboard.length;
    //     if (k == n) {
    //         res.add(new ArrayList<>(tmp));
    //         return;
    //     }
    //
    //     // 看看k行的皇后下在哪才不会与上面的0~(k-1)行冲突
    //     for (int j = 0; j < n; j++) {
    //         if (!conflict(chessboard, k, j)) {
    //             // 和前面没有冲突，则尝试将这一行的皇后下载j列
    //             chessboard[k][j] = true;
    //             sb.append("Q");
    //             for (int r = j + 1; r < n; r++) {   // 这一行确定了皇后位置，将后面补.
    //                 sb.append(".");
    //             }
    //             tmp.add(sb.toString()); // 将这一行的结果存到tmp集合
    //
    //             backtrack(chessboard, new StringBuilder(), k+1);
    //
    //             // 撤销操作
    //             chessboard[k][j] = false;
    //             sb.delete(j, sb.length());    // 从皇后位置往后全部清除（皇后前面的.不清除）
    //             sb.append("."); // 该位置下皇后尝试完，撤销改为空位
    //             tmp.remove(tmp.size()-1);
    //         } else {    // 找到皇后位置前先补.
    //             sb.append(".");
    //         }
    //     }
    // }
    //
    // /**
    //  * 判断若在(x,y)位置下皇后是否会与之前下的皇后冲突
    //  * @param chessboard
    //  * @param x
    //  * @param y
    //  * @return
    //  */
    // public boolean conflict(boolean[][] chessboard, int x, int y) {
    //     int n = chessboard.length;
    //     for (int i = 0; i < x; i++) {  // 由于是按照行从上往下逐行下的，因此检查x行时只有上面0~(x-1)行有皇后
    //         for (int j = 0; j < n; j++) {
    //             if (chessboard[i][j]) { // 找到下的一枚皇后
    //                 if (y == j || Math.abs(y - j) == Math.abs(x - i)) { // 因为x行必定与前面的行不在同一行，因此只需要判断列和对角线规则
    //                     return true;
    //                 } else {
    //                     break;  // 这一行不会再有皇后了，直接搜索下一行的
    //                 }
    //             }
    //         }
    //     }
    //     return false;
    // }




    /**
     * 写法优化
     * 要点：
     * 1. Arrays.fill()可以将数组元素全部初始化位设定值
     * 2. 用一个一维queens数组即可记录下每一行的皇后位置，不需要用二维棋盘数组
     * 3. 使用三个集合 columns、diagonals1 和 diagonals2 分别记录每一列以及两个方向的每条斜线上是否有皇后，可以在 O(1) 的时间内判断该位置所在的列和两条斜线上是否已经有皇后。
     * 4. 判断和标识主对角线的方法：同一条主对角线上的每个位置满足行下标与列下标之差相等，并且不同的主对角线上的这个差值不同，因此可以使用行下标与列下标之差唯一表示一条主对角线。
     * 5. 判断和标识副对角线的方法：同一条副对角线上的每个位置满足行下标与列下标之和相等，并且不同的副对角线上的这个和不同，因此可以使用行下标与列下标之和唯一表示一条副对角线。
     */

    List<List<String>> res = new ArrayList<>();
    // 回溯法
    // 时间复杂度：O(n!)，其中n为皇后数量。分析：由于每个皇后必须位于不同列，因此已经放置的皇后所在的列不能放置别的皇后。第一个皇后有 n 列可以选择，
    // 第二个皇后最多有 n-1 列可以选择，第三个皇后最多有 n-2 列可以选择（如果考虑到不能在同一条斜线上，可能的选择数量更少），因此所有可能的情况不会超过 n！种。
    // 空间复杂度：O(n)。分析：空间复杂度主要取决于递归调用层数、记录每行放置的皇后的列下标的数组以及三个集合，递归调用层数不会超过 n，数组的长度为 n，
    // 每个集合的元素个数都不会超过 n。
    public List<List<String>> solveNQueens(int n) {
        int[] queens = new int[n];  // 记录每一行皇后的列下标
        Arrays.fill(queens, -1);    // 将数组中所有元素都初始设为-1

        // 使用三个集合 columns、diagonals1 和 diagonals2 分别记录每一列以及两个方向的每条斜线上是否有皇后。
        Set<Integer> columns = new HashSet<>();
        Set<Integer> diagonals1 = new HashSet<>();
        Set<Integer> diagonals2 = new HashSet<>();

        backtrack(queens, n, 0, columns, diagonals1, diagonals2);
        return res;
    }


    /**
     * 确定每一行的皇后位置
     * @param queens
     * @param n
     * @param row 当前确定的行
     * @param columns
     * @param diagonals1
     * @param diagonals2
     */
    public void backtrack(int[] queens, int n, int row, Set<Integer> columns, Set<Integer> diagonals1, Set<Integer> diagonals2) {
        if (row == n) { // 最后一行的皇后也成功放置，生成棋盘解
            res.add(generateBoard(queens));
            return;
        }

        // 看看row行的皇后下在哪才不会与上面的之前的行冲突
        for (int j = 0; j < n; j++) {
            // 检查列是否冲突
            if (columns.contains(j)) {
                continue;
            }

            // 检查主对角线是否冲突
            // 原理：同一条主对角线上的每个位置满足行下标与列下标之差相等，并且不同的主对角线上的这个差值不同，因此可以使用行下标与列下标之差唯一表示一条主对角线。
            int diag1 = row - j;
            if (diagonals1.contains(diag1)) {
                continue;
            }

            // 检查副对角线是否冲突
            // 原理：同一条副对角线上的每个位置满足行下标与列下标之和相等，并且不同的副对角线上的这个和不同，因此可以使用行下标与列下标之和唯一表示一条副对角线。
            int diag2 = row + j;
            if (diagonals2.contains(diag2)) {
                continue;
            }

            // 若都不冲突，则在该位置放下皇后
            queens[row] = j;
            // 更新三个集合，将该位置的列、主副对角线都加入对应集合，以便下面的行方便判断位置
            columns.add(j);
            diagonals1.add(diag1);
            diagonals2.add(diag2);
            // 继续确定下面的行的皇后位置
            backtrack(queens, n, row + 1, columns, diagonals1, diagonals2);
            // 撤销操作
            queens[row] = -1;
            columns.remove(j);
            diagonals1.remove(diag1);
            diagonals2.remove(diag2);
        }
    }

    /**
     * 根据queens数组生成一个棋盘解
     * @param queens
     * @return
     */
    public List<String> generateBoard(int[] queens) {
        List<String> board = new ArrayList<>();  // 一种棋盘解
        int n = queens.length;
        System.out.println("queens=" + queens.toString() + ", n="+n);
        for (int i = 0; i < n; i++) {
            char[] row = new char[n];
            Arrays.fill(row, '.');  // 先将一行全设为.
            row[queens[i]] = 'Q';   // 再将该行的皇后位置设为Q
            board.add(new String(row));
        }
        return board;
    }

}
