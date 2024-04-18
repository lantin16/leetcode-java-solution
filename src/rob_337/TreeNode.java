package rob_337;

/**
 * Definition for a binary tree node.
 *
 * 二叉树的顺序存储结构：
 * 按照完全二叉树的节点下标存储，缺失的节点在数组该位置存null
 * 下标关系（节点下标对应数组下标，从0开始）：
 * 1. 下标为i的节点的父节点下标为 (i-1)/2
 * 2. 下标为i的节点的左子节点（如果有的话）下标为 2i+1
 * 3. 下标为i的节点的右子节点（如果有的话）下标为 2i+2
 */

public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

