package diameterOfBinaryTree;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * No.543 二叉树的直径
 *
 * 给你一棵二叉树的根节点，返回该树的 直径 。
 * 二叉树的 直径 是指树中任意两个节点之间最长路径的 长度 。这条路径可能经过也可能不经过根节点 root 。
 * 两节点之间路径的 长度 由它们之间边数表示。
 */


public class Solution {
    // // 递归
    // // 直径对应的路径可能有两种情况：
    // // - 经过root，= 左边叶子节点到root的最长路径 + 右边叶子节点到root的最长路径
    // // -不经过root，= max{root左子树的直径, root右子树的直径}
    // public int diameterOfBinaryTree(TreeNode root) {
    //     if (root == null) {
    //         return 0;
    //     }
    //
    //     // root左边叶子节点到root的最长路径 = root左子树的最大深度
    //     int pathWithRoot = maxDepth(root.left) + maxDepth(root.right);  // 经过root的最长路径
    //
    //     int ld = diameterOfBinaryTree(root.left);   // 左子树的直径
    //     int rd = diameterOfBinaryTree(root.right);   // 右子树的直径
    //     int pathWithoutRoot = Math.max(ld, rd); // 不经过root的最长路径
    //
    //     return Math.max(pathWithRoot,pathWithoutRoot);
    //
    // }
    //
    // // 求二叉树的最大深度
    // public int maxDepth(TreeNode root) {
    //     if (root == null) {
    //         return 0;
    //     }
    //
    //     return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    // }



    // 下面这种写法更好！！！！
    // 其实也是递归，只不过在求深度的同时也维护了res的值。
    // 比起上面的写法：求最大深度单独递归，求左右子树的直径又单独递归，而求直径又要递归求最大深度，下面这种大大提高了性能


    int res;    // 用全局变量来记录最长路径

    // 任意一条路径均可以被看作由某个节点为起点，从其左儿子和右儿子向下遍历的路径拼接得到。
    // 求二叉树直径 = 求 以各个节点node为起点的最长路径 的最大值
    // 而 以node为起点由左儿子向下遍历的最长路径 = node左子树的最大深度
    public int diameterOfBinaryTree(TreeNode root) {
        res = 0;
        depth(root);
        return res;
    }

    // 向下遍历的同时维护res
    // 判断以该node为起点的左右路径拼接后能够使res更大
    public int depth(TreeNode node) {
        if (node == null) {
            return 0;
        }

        int l = depth(node.left);   // node左子树的最大深度
        int r = depth(node.right);   // node右子树的最大深度

        res = Math.max(res, l + r);
        return Math.max(l, r) + 1;
    }

}
