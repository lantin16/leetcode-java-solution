package numIslands;

import java.util.LinkedList;
import java.util.Queue;

/**
 * No.200 岛屿数量
 *
 * 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
 * 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
 * 此外，你可以假设该网格的四条边均被水包围。
 *
 * 岛屿问题基本都可以用dfs解决
 */

public class Solution {

    /**
     * 需不需要另外用一个二维数组来存储访问状态呢？——不用，本题直接在grid中修改对应元素值作标记即可
     * 定义如下：
     * 0 —— 海洋格子
     * 1 —— 陆地格子（未遍历过）
     * 2 —— 陆地格子（已遍历过）
     */

    // dfs（推荐）
    // 时间复杂度：O(mn)，其中m和n分别为grid行数和列数
    // 时间复杂度：O(mn)。在最坏情况下，整个网格均为陆地，深度优先搜索的深度达到mn。
    public int numIslands(char[][] grid) {
        int res = 0;    // 记录岛屿数量

        // 对网格中遇到的每一块陆地都进行深度优先搜索
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    dfs(grid, i, j);
                    res ++;
                }
            }
        }

        return res;
    }

    /**
     * 从grid[r][c]开始进行深搜
     * @param grid
     * @param r
     * @param c
     */
    public void dfs(char[][] grid, int r, int c) {
        // 1. 中止条件：超出grid边界 或 搜索到海洋格子或已经遍历过陆地格子
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length) {
            return;
        }
        if (grid[r][c] != '1') {
            return;
        }

        // 2. 本层的逻辑：将当前陆地格子标记为已遍历
        grid[r][c] = '2';

        // 3. 继续递归：按照上右下左的顺序继续对周围的格子进行深搜
        dfs(grid, r-1, c);
        dfs(grid, r, c+1);
        dfs(grid, r+1, c);
        dfs(grid, r, c-1);

    }






    // // bfs + 队列
    // // 时间复杂度：O(mn)，其中m和n分别为grid行数和列数
    // // 空间复杂度：O(min(m,n))。分析：在最坏情况下，整个网格均为陆地，队列的大小达到 min(m, n)，因为bfs在二维矩阵中是斜着扫描的，
    // // 当矩阵全为1时，从左上向右下遍历，最长路径为副对角线上的格子数目。（不理解可以画图模拟全1情况看看队列的元素数）
    // public int numIslands(char[][] grid) {
    //     int res = 0;    // 记录岛屿数量
    //
    //     // 对网格中遇到的每一块陆地都进行广度优先搜索
    //     for (int i = 0; i < grid.length; i++) {
    //         for (int j = 0; j < grid[0].length; j++) {
    //             if (grid[i][j] == '1') {
    //                 bfs(grid, i, j);
    //                 res ++; // 调用bfs的次数就等于岛屿的个数
    //             }
    //         }
    //     }
    //
    //     return res;
    // }
    //
    // // ！！！注意：这里bfs时，只要加入队列就代表走过，就需要标记，而不是从队列拿出来的时候再去标记走过！！！
    // // 如果等到从队列拿出来的时候再去标记，则会导致很多结点重复入队，这会大大拖延时间
    // // 还有，如何将x，y坐标作为整体入队呢？——1.使用长度为2的数组 2.定义一个class 3.将二维坐标转换为一个数字，等到出队要用时再转回二维坐标（推荐）
    // public void bfs(char[][] grid, int r, int c) {
    //     int m = grid.length, n = grid[0].length;    // grid的行数和列数
    //     Queue<Integer> queue = new LinkedList<>();
    //     grid[r][c] = '2';   // 入队的时候就标记访问过
    //     queue.offer(r * n + c); // 将二维坐标(r,c)转换为一个数 r * n + c，可以看作从0开始，(r,c)在grid中从左往右、从上往下数的序号
    //
    //     while (!queue.isEmpty()) {
    //         int id = queue.poll();
    //         // 从序号中解析出行号和列号
    //         int row = id / n;
    //         int col = id % n;
    //
    //         // 在队列中的都是已经访问过的，在入队前就标记了已访问，因此出队时不用标记了
    //
    //         // 判断当前格子的上右下左相邻格子是否需要入队（超出gird边界的不入队，不是未遍历的陆地格子不入队。这也说明入队了的都是边界内的格子）
    //         // 上
    //         if (row - 1 >= 0 && grid[row-1][col] == '1') {   // 已知(row,col)在边界内，因此对于它上边的相邻格子只需要判断行坐标是否越界
    //             grid[row-1][col] = '2'; // 入队的时候就标记访问过，不要等出队的时候再标记
    //             queue.offer((row-1) * n + col);
    //         }
    //
    //         // 右
    //         if (col + 1 < n && grid[row][col+1] == '1') {
    //             grid[row][col+1] = '2';
    //             queue.offer(row * n + col + 1);
    //         }
    //
    //         // 下
    //         if (row + 1 < m && grid[row+1][col] == '1') {
    //             grid[row+1][col] = '2';
    //             queue.offer((row+1) * n + col);
    //         }
    //
    //         // 左
    //         if (col - 1 >= 0 && grid[row][col-1] == '1') {
    //             grid[row][col-1] = '2';
    //             queue.offer(row * n + col - 1);
    //         }
    //
    //     }
    // }





    // // 并查集
    // class UnionFind {
    //     int count;  // 记录连通分量个数
    //     int[] parent;   // parent[i]表示i的父节点
    //     int[] rank;     // 秩，以i为根的树的高度
    //
    //     // 构造方法，初始化并查集
    //     public UnionFind(char[][] grid) {
    //         count = 0;
    //         int m = grid.length;
    //         int n = grid[0].length;
    //
    //         // 二维网格的每个格子都拥有一个parent和rank值，因此这两个数组长度为m*n
    //         parent = new int[m*n];
    //         rank = new int[m*n];
    //
    //         //初始化，遍历每个格子设置它们的初始parent和rank
    //         for (int i = 0; i < m; i++) {
    //             for (int j = 0; j < n; j++) {
    //                 // 如果是陆地格子，它们初始时是孤立的连通分量，parent指向自己，rank为1
    //                 if (grid[i][j] == '1') {
    //                     parent[i * n + j] = i * n + j;  // parent初始指向自己
    //                     count++;
    //                 }
    //                 rank[i * n + j] = 1;    // rank初始为1
    //
    //             }
    //         }
    //     }
    //
    //     // 查找结点i的根节点
    //     public int find(int i) {
    //         if (parent[i] != i) {   // 只有根节点的parent指向自己
    //             parent[i] = find(parent[i]); // 路径压缩，将i到根节点路径上的所有结点都直接指向根节点
    //         }
    //         return parent[i];   // 返回根节点
    //     }
    //
    //     // 按秩合并结点x和结点y所在的连通分量
    //     public void union(int x, int y) {
    //         // 找到x和y的根节点
    //         int rootX = find(x);
    //         int rootY = find(y);
    //
    //         // 如果根节点不同，说明x和y不在同一个连通分量中，需要合并
    //         if (rootX != rootY) {
    //             // 将rank较小的树合并到rank较大的树上（将简单的树往复杂的树上合并，影响的结点数更少，开销更小）
    //             if (rank[rootX] > rank[rootY]) {
    //                 parent[rootY] = rootX;
    //             } else if (rank[rootX] < rank[rootY]) {
    //                 parent[rootX] = rootY;
    //             } else {    // 如果两个树的高度相同，则合并后新树的高度+1
    //                 parent[rootY] = rootX;
    //                 rank[rootX]++;
    //             }
    //             count--;    // 合并后连通分量减1（两棵树变一棵树）
    //         }
    //     }
    //
    //     // 返回当前的连通分量个数
    //     public int getCount() {
    //         return count;
    //     }
    // }
    //
    // // 为了求出岛屿的数量，我们可以扫描整个二维网格。如果一个位置为 1，则将其与相邻四个方向上的 1 在并查集中进行合并。
    // // 最终岛屿的数量就是并查集中连通分量的数目。
    // // 时间复杂度：O(mn)，其中m和n分别为grid行数和列数
    // // 空间复杂度：O(mn)，并查集的空间代价
    // public int numIslands(char[][] grid) {
    //     int m = grid.length;
    //     int n = grid[0].length;
    //
    //     // 初始化并查集
    //     UnionFind uf = new UnionFind(grid);
    //
    //     // 遍历每个格子，如果是陆地格子，就将它与相邻四个方向的陆地格子合并
    //     for (int i = 0; i < m; i++) {
    //         for (int j = 0; j < n; j++) {
    //             if (grid[i][j] == '1') {
    //                 grid[i][j] = '2';   // 将当前格子标记为已访问过
    //
    //                 // 合并当前格子与上右下左的陆地格子
    //                 if (i - 1 >= 0 && grid[i-1][j] == '1') {
    //                     uf.union(i * n + j, (i-1) * n + j);
    //                 }
    //
    //                 if (j + 1 < n && grid[i][j+1] == '1') {
    //                     uf.union(i * n + j, i * n + j + 1);
    //                 }
    //
    //                 if (i + 1 < m && grid[i+1][j] == '1') {
    //                     uf.union(i * n + j, (i+1) * n + j);
    //                 }
    //
    //                 if (j - 1 >= 0 && grid[i][j-1] == '1') {
    //                     uf.union(i * n + j, i * n + j - 1);
    //                 }
    //             }
    //         }
    //     }
    //
    //     return uf.getCount();
    // }
}
