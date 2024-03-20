package canFinish;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * No.207 课程表
 * 你这个学期必须选修 numCourses 门课程，记为 0 到 numCourses - 1 。
 * 在选修某些课程之前需要一些先修课程。 先修课程按数组 prerequisites 给出，其中 prerequisites[i] = [ai, bi] ，表示如果要学习课程 ai 则 必须 先学习课程  bi 。
 * 例如，先修课程对 [0, 1] 表示：想要学习课程 0 ，你需要先完成课程 1 。
 * 请你判断是否可能完成所有课程的学习？如果可以，返回 true ；否则，返回 false 。
 */

public class Solution {

    /**
     * 拓扑排序
     * 在图论中，拓扑排序是一个有向无环图的所有顶点的线性序列。且该序列必须满足下面两个条件：
     * 1. 每个顶点出现且只出现一次。
     * 2. 若存在一条从顶点 A 到顶点 B 的路径，那么在序列中顶点 A 出现在顶点 B 的前面。
     * <p>
     * 有向无环图一定存在拓扑排序（可以是一个或多个）
     * 若一个有向图中存在环，则不存在拓扑排序，因此拓扑排序可以用来判断一个有向图是否存在环
     * 拓扑排序通常用来“排序”具有依赖关系的任务。
     */


    // 本题可看作：课程安排图是否是有向无环图（DAG）。课程B依赖于课程A，即课程A是课程B的前置课程，可看作有向边 A -> B




    // 通过 bfs（入度数组、邻接表） + 拓扑排序 判断图中是否有环 （更好理解）
    // 求有向图拓扑排序的算法：不停地删除入度为零的结点以及它们的出边，最后若能删完则能求出拓扑排序，也就证明没有环
    // 时间复杂度：O(n+m)。分析：遍历一个图需要访问所有节点和所有临边，n 和 m 分别为节点数量（课程数）和临边数量（先修课程的要求数）；
    // 空间复杂度：O(n+m)。分析：题目中是以列表形式给出的先修课程关系，为了对图进行广度优先搜索，我们需要存储成邻接表的形式，另外还需要存储入度数组，空间复杂度为 O(n+m)。
    // 在广度优先搜索的过程中，我们需要最多 O(n)的队列空间（迭代）进行广度优先搜索。因此总空间复杂度为 O(n+m)。
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // 1. 根据课程前置条件列表建立课程安排有向图的 邻接表（以便后续访问）以及 入度数组（便于判断某课程是否可修）
        List<List<Integer>> adjacency = new ArrayList<>();
        int[] inDegrees = new int[numCourses];
        // 初始化邻接表
        for (int i = 0; i < numCourses; i++) {
            adjacency.add(new ArrayList<>());
        }
        // 根据课程前置条件列表建立邻接表以及入度数组
        for (int[] cp : prerequisites) {    // cp是prerequisites中的先修课程对。[a, b]表示b->a
            adjacency.get(cp[1]).add(cp[0]);
            inDegrees[cp[0]]++;
        }

        // 2. 对图中入度为0的课程进行BFS（入度为0代表它不需要依赖其他前置课程，可以直接修）
        Queue<Integer> queue = new LinkedList<>();  // 入度为0的课程可以入队，代表该课程可以修了
        for (int i = 0; i < numCourses; i++) {
            if (inDegrees[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int pre = queue.poll(); // 将队首课程取出来修掉
            numCourses--;   // 修一门少一门
            // 将以pre作为前置课程的课程入度减一，代表pre修完它们的前置课程也少了一门
            for (int v : adjacency.get(pre)) {
                if (--inDegrees[v] == 0) {  // 如果有课程经过入度减一后入度为0了，证明它也可以修了，就入队
                    queue.offer(v);
                }
            }
        }
        return numCourses == 0; // 最后剩余课程为0则代表可以修完
    }






    // // 定义全局变量便于递归的函数访问
    // List<List<Integer>> adjacency;  // 邻接表
    // int[] visited;  // 访问标记数组
    // boolean valid = true;   // valid用于发现环之后直接提前中止
    //
    // // 通过 dfs + 拓扑排序 判断图中是否有环
    // // 时间复杂度：O(n+m)，其中 n 为课程数，m 为先修课程的要求数。这其实就是对图进行深度优先搜索的时间复杂度。
    // // 空间复杂度：O(n+m)。题目中是以列表形式给出的先修课程关系，为了对图进行深度优先搜索，我们需要存储成邻接表的形式，空间复杂度为 O(n+m)。
    // // 在深度优先搜索的过程中，我们需要最多 O(n) 的栈空间（递归）进行深度优先搜索，因此总空间复杂度为 O(n+m)。
    // public boolean canFinish(int numCourses, int[][] prerequisites) {
    //     // 1. 根据课程前置条件列表建立课程安排有向图的邻接表，以便后续访问
    //      adjacency = new ArrayList<>();
    //     // 初始化邻接表
    //     for (int i = 0; i < numCourses; i++) {
    //         adjacency.add(new ArrayList<>());
    //     }
    //     // 根据课程前置条件列表建立邻接表
    //     for (int[] cp : prerequisites) {    // cp是prerequisites中的先修课程对。[a, b]表示b->a
    //         adjacency.get(cp[1]).add(cp[0]);
    //     }
    //
    //     // 2. 借助标志数组visited来记录各个课程结点的访问状态：0：未访问， 1：访问中， 2：访问完
    //     visited = new int[numCourses];
    //
    //     // 3. 对图中所有未搜索状态的课程依次进行DFS，判断以它们起步的DFS是否存在环
    //     for (int i = 0; i < numCourses; i++) {
    //         if (visited[i] == 0) {
    //             dfs(i);
    //             if (!valid) {
    //                 return false;
    //             }
    //         }
    //     }
    //     return true;
    // }
    //
    // // 事实上，对于每一轮DFS（由canFinish方法开启的），只有当前深搜路径上访问中的结点才处于“访问中”的状态，其余结点都是未访问或访问完的状态
    // // 因此，只要深搜遇到了访问中的结点，证明是走到了回头路上的结点（出现环）。
    // public void dfs(int i) {
    //     // 1. 将当前结点标记为访问中，只有当其邻接结点都访问完回溯到这里时，才会改为访问完
    //     visited[i] = 1;
    //
    //     // 2. 检查其邻接结点的状态
    //     for (int v : adjacency.get(i)) {
    //         if (visited[v] == 1) {  // 遇到了本轮DFS走过且未完成的结点，即出现环
    //             valid = false;
    //             return;
    //         } else if (visited[v] == 0) {   // 向邻接的未访问的结点继续搜索
    //             dfs(v);
    //             if (!valid) {   // 如果发现环提前返回
    //                 return;
    //             }
    //         }
    //     }
    //
    //     // 3. i的所有邻接结点都访问完则可将i的状态设为访问完，回溯到上一层
    //     visited[i] = 2;
    // }
}
