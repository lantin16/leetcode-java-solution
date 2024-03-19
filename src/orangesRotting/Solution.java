package orangesRotting;

import java.util.LinkedList;
import java.util.Queue;

/**
 * No.994 腐烂的橘子
 * 在给定的 m x n 网格 grid 中，每个单元格可以有以下三个值之一：
 *
 * 值 0 代表空单元格；
 * 值 1 代表新鲜橘子；
 * 值 2 代表腐烂的橘子。
 * 每分钟，腐烂的橘子 周围 4 个方向上相邻 的新鲜橘子都会腐烂。
 *
 * 返回 直到单元格中没有新鲜橘子为止所必须经过的最小分钟数。如果不可能，返回 -1 。
 *
 */

public class Solution {

    // 多源广度优先搜索
    // 对于初始所有的腐烂橘子，其实它们在广度优先搜索上是等价于同一层的节点的。可以看作在-1秒有一个超级腐烂橘子在0秒时将这些橘子同时传染，然后它们再同时继续传染周围的
    // 既然等价于同一层，那么可以将这些腐烂橘子都放进队列里进行广度优先搜索即可
    // 时间复杂度：O(mn)，其中m，n分别为grid的行数和列数
    // 空间复杂度：O(mn)。广度优先搜索中队列里存放的状态最多不会超过mn个，最多需要 O(nm) 的空间
    public static int orangesRotting(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int freshNum = 0;   // 记录新鲜橘子的数量（用于最后判断是否全部变为烂橘子）
        int minutes = 0;    // 记录经过的分钟数，每经过一轮广度优先搜索，就加一（一轮指BFS同一层的格子全部遍历完）
        int[] dr = {-1, 0, 1, 0};   // 上右下左相邻格子行坐标的改变量
        int[] dc = {0, 1, 0, -1};   // 上右下左相邻格子列坐标的改变量
        Queue<Integer> queue = new LinkedList<>();

        // 先统计出初始的新鲜橘子数以及将腐烂的橘子入队
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    freshNum++;
                } else if (grid[i][j] == 2) {
                    queue.offer(i * n + j);
                }
            }
        }

        // 开始传染
        while (!queue.isEmpty()) {
            // 如果已经没有新鲜橘子，直接提前结束
            if (freshNum == 0) {
                return minutes;
            }

            // 每轮将同一层的节点全部遍历完
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int id = queue.poll();
                // 从格子的序号中解析出行列坐标
                int r = id / n;
                int c = id % n;

                // 将该格子的上右下左相邻的新鲜橘子传染
                for (int k = 0; k < 4; k++) {
                    int nr = r + dr[k];
                    int nc = c + dc[k];

                    if ((nr >= 0 && nr < m) && (nc >= 0 && nc < n) && grid[nr][nc] == 1) {
                        // 先感染再入队，避免某些格子重复入队，这样队列里的都是已经遍历过的烂橘子
                        grid[nr][nc] = 2;
                        queue.offer(nr * n + nc);
                        freshNum--;
                    }
                }
            }
            minutes++;  // 将同一层的腐烂橘子遍历完，分钟数加1
        }

        return freshNum > 0 ? -1 : minutes;
    }


    public static void main(String[] args) {
        int[][] grid = {{2,1,1},{1,1,0},{0,1,1}};
        System.out.println(orangesRotting(grid));
    }
}
