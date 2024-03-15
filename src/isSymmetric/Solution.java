package isSymmetric;

import java.lang.annotation.Target;
import java.util.LinkedList;
import java.util.Queue;

/**
 * No.101 对称二叉树
 *
 * 给你一个二叉树的根节点 root ， 检查它是否轴对称。
 */

public class Solution {
    // // 递归
    // // 时间复杂度：O(n)，其中n为节点个数
    // // 空间复杂度：O(height)，其中height为树的高度
    // public boolean isSymmetric(TreeNode root) {
    //     // // 如果只有一个节点则一定对称
    //     // if (root.left == null && root.right == null) {
    //     //     return true;
    //     // }
    //     //
    //     // return check(root.left, root.right);
    //
    //     // 这样写：从root就开始递归
    //     return check(root, root);
    // }
    //
    // // 判断左右两棵树是否关于最开始的轴对称
    // public boolean check(TreeNode lTree, TreeNode rTree) {
    //     // 两棵树都不存在则一定对称
    //     if (lTree == null && rTree == null) {
    //         return true;
    //     }
    //
    //     // 一棵存在一棵不存在则一定不对称
    //     if (lTree == null ^ rTree == null) {
    //         return false;
    //     }
    //
    //     // 两棵树都存在则需要进一步比较根节点的val以及它们的子树
    //     // 要判断根节点的val是否相等，以及左树的左子树和右树的右子树、左树的右子树和右树的左子树是否轴对称
    //     boolean valIsSymmetric = lTree.val == rTree.val;
    //     boolean childrenIsSymmetric = check(lTree.left, rTree.right) && check(lTree.right, rTree.left);
    //     return valIsSymmetric && childrenIsSymmetric;
    // }


    // 迭代
    // 需要用一个队列来维护节点
    // 时间复杂度：O(n)，其中n为节点个数
    // 空间复杂度：队列中最多不会超过 n 个点，故渐进空间复杂度为 O(n)
    public boolean isSymmetric(TreeNode root) {
        // if (root.left == null && root.right == null) {
        //     return true;
        // }
        //
        // if (root.left == null || root.right == null) {
        //     return false;
        // }
        //
        Queue<TreeNode> queue = new LinkedList<>();
        // queue.offer(root.left); // 左边的先入队，右边与它对应的要判断的部分后入队
        // queue.offer(root.right);

        // 或者这样写，将root看作左右两边的节点（比起上面单独先处理root的学法 更简洁，但是要花费双倍的时间，因为重复检查了root的左子树和右子树）
        queue.offer(root);
        queue.offer(root);

        while (!queue.isEmpty()) {
            // 每次弹出两个节点检查
            TreeNode l = queue.poll();
            TreeNode r = queue.poll();

            if (l == null && r == null) {
                continue;
            }

            if ((l == null || r == null) || (l.val != r.val)) {
                return false;
            }

            // l，r均不为null且val相等则将它们的左右子结点按相反的顺序插入队列中
            queue.offer(l.left);
            queue.offer(r.right);

            queue.offer(l.right);
            queue.offer(r.left);
        }

        return true;
    }

}
