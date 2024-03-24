package exist;

/**
 * No.79 单词搜索
 *
 * 给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。如果 word 存在于网格中，返回 true ；否则，返回 false 。
 * 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。
 */

public class Solution {

    // 回溯法
    // 时间复杂度：O(mn × 3^l)，其中m,n为网格的长度与宽度，l为字符串word的长度。分析：最坏的情况会对每个格子都开始一次搜索，而每次搜索中，一般每个
    // 格子只会进入三个方向（有一个是来的方向，不会返回去），故每开启一次dfs最差的时间复杂度是O(3^l)。但是实际上由于剪枝的存在，在遇到不匹配或已访问
    // 的字符时会提前退出，终止递归流程。因此，实际的时间复杂度会远远小于 O(mn × 3^l)。
    // 空间复杂度：O(mn)。分析：们额外开辟了 O(mn) 的 visited 数组，另外递归深度最大为O(min(l, mn))
    int[][] direction = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}}; // 描述相邻格子的行列坐标变化量
    public boolean exist(char[][] board, String word) {
        int m = board.length, n = board[0].length;
        boolean[][] visited = new boolean[m][n];    // 记录格子的访问情况

        // 在网格中找到word开始的字母
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 从起始字母开始深度优先搜索
                if (board[i][j] == word.charAt(0) && dfs(board, word, 0, i, j, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 深度优先搜索
     * @param board
     * @param word
     * @param idx 当前正在搜索word[idx]
     * @param r 当前搜索格子的行坐标
     * @param c 当前搜索格子的列坐标
     * @param visited 访问标记数组
     * @return
     */
    public boolean dfs(char[][] board, String word, int idx, int r, int c, boolean[][] visited) {
        if (board[r][c] != word.charAt(idx)) {  // 如果对应位置字母不匹配，直接返回false
            return false;
        } else if (idx == word.length()-1) {    // 如果对应位置匹配且已经匹配到了word最后一个字母，则说明找到word，返回true
            return true;
        }

        // 如果当前字母匹配但长度还没够，则继续向其相邻的未访问过的格子继续搜索
        visited[r][c] = true;   // 将当前格子标记为已访问
        // 按照上右下左的顺序向相邻格子继续进行dfs
        for (int i = 0; i < 4; i++) {
            int nr = r + direction[i][0];
            int nc = c + direction[i][1];
            // 向相邻的在网格内且未访问过的格子搜索
            if ((nr >= 0 && nr < board.length) && (nc >= 0 && nc < board[0].length) && !visited[nr][nc] &&
                    dfs(board, word, idx + 1, nr, nc, visited)) { // 如果朝一个方向搜索到了结果，则直接返回，不用继续搜索（剪枝）
                return true;
            }
        }
        visited[r][c] = false;  // 撤销操作
        return false;   // 从这个格子往周围搜索没有搜索到，回溯到上一格换个方向搜索
    }
}
