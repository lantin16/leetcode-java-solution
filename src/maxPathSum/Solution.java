package maxPathSum;

import java.util.HashMap;
import java.util.Map;

/**
 * No.124 二叉树中的最大路径和
 * 二叉树中的 路径 被定义为一条节点序列，序列中每对相邻节点之间都存在一条边。同一个节点在一条路径序列中 至多出现一次 。该路径 至少包含一个 节点，且不一定经过根节点。
 *
 * 路径和 是路径中各节点值的总和。
 *
 * 给你一个二叉树的根节点 root ，返回其 最大路径和 。
 */

public class Solution {


    // 递归 + dfs
    // 所有路径都可以看作以某个节点为根，向其左孩子右孩子延伸的两条路径拼接而成
    // 因此通过dfs求出以每个节点为根，向其孩子延伸的最大路径和，然后同时计算左右路径拼接的最大路径和来维护一个全局的最大路径和
    // 时间复杂度：O(n)，其中n为二叉树节点个数，对每个结点的访问不超过2次。
    // 空间复杂度：O(h)，其中h为二叉树高度。空间复杂度主要取决于递归调用层数，最大层数等于二叉树的高度，最坏情况下，二叉树的高度等于二叉树中的节点个数。
    private int maxSum = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        dfs(root);
        return maxSum;
    }

    /**
     * 计算以root为根节点向其孩子延伸的最大路径和
     * 同时对每个结点计算：以root为根节点，向左向右路径拼接成的路径中 的最大路径和，并根据此来维护全局的最大路径和
     * @param root 以root为根节点其孩子延伸的最大路径和
     * @return
     */
    public int dfs(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int lcRootMaxSum = dfs(root.left);  // 以root.left为起点向下延伸的最大路径和
        int rcRootMaxSum = dfs(root.right); // 以root.right为起点向下延伸的最大路径和

        // 以root为起点向下延伸的最大路径和有三种情况：1.仅root 2.root及向左孩子延伸 3.root及向右孩子延伸
        int curRootMaxPathSum = Math.max(root.val, Math.max(root.val + lcRootMaxSum, root.val + rcRootMaxSum));


        // 以root为根节点，向左向右拼接的最大路径和（必须经过root且root为最上面的结点）
        int curMaxSum = root.val;
        if (lcRootMaxSum > 0) {
            curMaxSum += lcRootMaxSum;
        }
        if (rcRootMaxSum > 0) {
            curMaxSum += rcRootMaxSum;
        }
        
        // 维护全局最大路径和
        maxSum = Math.max(maxSum, curMaxSum);
        return curRootMaxPathSum;
    }
}
